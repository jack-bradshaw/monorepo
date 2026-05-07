package com.jackbradshaw.oksp.host

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import com.jackbradshaw.concurrency.quinn.Quinn.ErrorBehaviour
import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.QuinnComponent
import com.jackbradshaw.concurrency.quinn.quinnComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.ApplicationComponent
import com.jackbradshaw.oksp.application.loaded.loadedApplicationComponent
import com.jackbradshaw.oksp.model.KspContext
import com.jackbradshaw.oksp.model.LogLevel
import com.jackbradshaw.oksp.model.Resource
import com.jackbradshaw.oksp.model.Source
import com.jackbradshaw.oksp.service.KspService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Inject
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.withContext

/**
 * Standard KSP entry point for an OKSP application.
 *
 * This provider intercepts the system's KSP initialization and directs the user to construct their
 * Dagger dependency graph. It creates the [Application] environment and maintains its asynchronous
 * lifecycle.
 */
class HostImpl
@Inject
@JvmOverloads
constructor(
    private val applicationComponent: ApplicationComponent = loadedApplicationComponent(),
    private val quinn: QuinnComponent = quinnComponent(),
    private val coroutines: CoroutinesComponent = com.jackbradshaw.coroutines.coroutinesComponent(),
) : Host {

  private lateinit var app: Application

  private lateinit var processor: Processor

  private lateinit var environment: SymbolProcessorEnvironment

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor = runBlocking {
    this@HostImpl.environment = environment
    this@HostImpl.processor = Processor()

    try {
      withContext(coroutines.ioDispatcher()) { startApplication() }
    } catch (e: Throwable) {
      try {
        withContext(coroutines.ioDispatcher()) { shutDownApplicationImmediately() }
      } catch (destroyError: Throwable) {
        e.addSuppressed(destroyError)
      }
      // Rethrowing ensures KSP never begins processing
      throw e
    }

    processor
  }

  private suspend fun startApplication() {
    app = applicationComponent.application()
    app.onCreate(DaggerKspComponentImpl.factory().create(environment, processor.service))
  }

  private suspend fun shutDownApplicationImmediately() {
    try {
      if (::app.isInitialized) app.onDestroy()
    } catch (e: Throwable) {
      if (::environment.isInitialized) {
        environment.logger.error("Error occurred during application onDestroy: ${e.message}")
      }
      throw e
    }
  }

  private suspend fun shutDownApplicationAfterGate(gate: CompletableDeferred<Unit>) {
    gate?.await()
    shutDownApplicationImmediately()
  }

  private inner class Processor : SymbolProcessor {

    /** Whether [service] has declared processing may begin. */
    private val isServiceReadyToStart = CompletableDeferred<Unit>()

    /** Whether [service] has decalred processing may finish. */
    private val isServiceReadyToEnd = CompletableDeferred<Unit>()

    /**
     * Whether round processing has finished without error. Indicates whether the core KSP aspect of
     * processing is finished, and can indicate finished before [isServiceReadyToEnd].
     */
    private val isFinishedNormally = CompletableDeferred<Unit>()

    /**
     * Whether round processing has finished due to an error. Indicates whether the core KSP aspect
     * of processing is finished, and can indicate finished before [isServiceReadyToEnd].
     */
    private val isFinishedErroneously = CompletableDeferred<Unit>()

    /**
     * Tracks when a round starts. Emission is triggered manually when the round start callback is
     * received (i.e. [process]).
     */
    private val roundStartEvents =
        MutableSharedFlow<Unit>(
            replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)

    /** List of symbols deferred in this round. */
    private var currentRoundDeferred: MutableList<KSAnnotated>? = null

    /** The quinn instance linking [service] to the underining KSP thread in this round. */
    private var currentRoundQuinn: Quinn<KspContext>? = null

    /** A [KspService] linked to this processor . */
    val service: KspService =
        object : KspService {

          override suspend fun allowProcessing() {
            check(!isServiceReadyToStart.isCompleted) {
              "Cannot start processing because it was started already."
            }
            isServiceReadyToStart.complete(Unit)
          }

          override suspend fun allowTermination() {
            isServiceReadyToEnd.complete(Unit)
          }

          override suspend fun abortProcessing() {
            if (isFinished()) return

            val abortException = kotlinx.coroutines.CancellationException("KSP Aborted")
            isFinishedErroneously.complete(Unit)

            isServiceReadyToStart.completeExceptionally(abortException)
            isServiceReadyToEnd.completeExceptionally(abortException)

            val activeQuinn = currentRoundQuinn ?: return

            activeQuinn.tryQueueAtFront(ErrorBehaviour.DELIVER_TO_EXECUTION_SIDE) { throw abortException }
            activeQuinn.close()
          }

          override suspend fun onEachRoundStart(): Flow<Unit> = channelFlow {
            val job = launch { roundStartEvents.collect { send(Unit) } }
            select<Unit> {
              isFinishedNormally.onAwait {}
              isFinishedErroneously.onAwait {}
            }
            job.cancelAndJoin()
          }

          override suspend fun onFinalRoundComplete(): Flow<Unit> = flow {
            isFinishedNormally.await()
            emit(Unit)
          }

          override suspend fun completeRound() {
            check(isServiceReadyToStart.isCompleted) {
              "Cannot complete round before processing has started."
            }
            check(!isFinished()) {
              "Cannot complete round because there is no active round. The final round has already " +
                  "completed."
            }
            check(currentRoundQuinn != null) {
              "Cannot complete round outside of an active KSP round."
            }

            currentRoundQuinn?.close()
          }

          override suspend fun withContext(block: (KspContext) -> Unit) {
            check(isServiceReadyToStart.isCompleted) {
              "Cannot invoke withContext before processing has started."
            }
            check(!isFinished()) { "Cannot invoke withContext after the final round." }

            val activeQuinn =
                checkNotNull(currentRoundQuinn) { "Cannot invoke withContext between KSP rounds." }
            activeQuinn.queueAtBack { context -> block(context) }
          }

          override suspend fun publish(source: Source, anchors: List<KSNode>) {
            check(isServiceReadyToStart.isCompleted) {
              "Cannot invoke publish before processing has started."
            }
            check(!isFinished()) { "Cannot invoke publish after the final round." }
            val activeQuinn = currentRoundQuinn
            check(activeQuinn != null) { "Cannot invoke publish between KSP rounds." }

            activeQuinn.queueAtBack { context ->
              val dependencyFiles = anchors.mapNotNull { it.getEnclosingFile() }.toTypedArray()
              val dependencies = Dependencies(aggregating = true, *dependencyFiles)

              context.environment.codeGenerator
                  .createNewFile(
                      dependencies = dependencies,
                      packageName = source.packageName,
                      fileName = source.fileName,
                      extensionName = source.extension)
                  .use { it.write(source.contents.toByteArray()) }
            }
          }

          override suspend fun publish(resource: Resource, anchors: List<KSNode>) {
            check(isServiceReadyToStart.isCompleted) {
              "Cannot invoke publish before processing has started."
            }
            check(!isFinished()) { "Cannot invoke publish after the final round." }
            val activeQuinn = currentRoundQuinn
            check(activeQuinn != null) { "Cannot invoke publish between KSP rounds." }

            activeQuinn.queueAtBack { context ->
              val dependencyFiles = anchors.mapNotNull { it.getEnclosingFile() }.toTypedArray()
              val dependencies = Dependencies(aggregating = true, *dependencyFiles)

              context.environment.codeGenerator
                  .createNewFileByPath(
                      dependencies = dependencies,
                      path =
                          if (resource.directoryPath.isEmpty()) resource.fileName
                          else "${resource.directoryPath}/${resource.fileName}",
                      extensionName = resource.extension)
                  .use { it.write(resource.contents.toByteArray()) }
            }
          }

          override suspend fun log(message: String, level: LogLevel?, anchor: KSNode?) {
            check(isServiceReadyToStart.isCompleted) {
              "Cannot invoke log before processing has started."
            }
            check(!isFinished()) { "Cannot invoke log after the final round." }
            val activeQuinn = currentRoundQuinn
            check(activeQuinn != null) { "Cannot invoke log between KSP rounds." }

            activeQuinn.queueAtBack { context ->
              when (level) {
                null -> context.environment.logger.logging(message, anchor)
                LogLevel.INFO -> context.environment.logger.info(message, anchor)
                LogLevel.WARNING -> context.environment.logger.warn(message, anchor)
                LogLevel.ERROR -> context.environment.logger.error(message, anchor)
              }
            }
          }

          override suspend fun fail(error: Throwable, anchor: KSNode?) {
            check(isServiceReadyToStart.isCompleted) {
              "Cannot invoke fail before processing has started."
            }
            check(!isFinished()) { "Cannot invoke fail after the final round." }
            val activeQuinn = currentRoundQuinn
            check(activeQuinn != null) { "Cannot invoke fail between KSP rounds." }

            try {
              activeQuinn.queueAtFront(ErrorBehaviour.DELIVER_TO_EXECUTION_SIDE) { context ->
                if (anchor != null) {
                  context.environment.logger.error(error.message ?: error.toString(), anchor)
                }
                throw error
              }
            } finally {
              activeQuinn.close()
            }
          }

          override suspend fun fail(error: String, anchor: KSNode?) {
            check(isServiceReadyToStart.isCompleted) {
              "Cannot invoke fail before processing has started."
            }
            check(!isFinished()) { "Cannot invoke fail after the final round." }
            val activeQuinn = currentRoundQuinn
            check(activeQuinn != null) { "Cannot invoke fail between KSP rounds." }

            try {
              activeQuinn.queueAtFront(ErrorBehaviour.DELIVER_TO_EXECUTION_SIDE) { context ->
                if (anchor != null) {
                  context.environment.logger.error(error, anchor)
                } else {
                  context.environment.logger.error(error)
                }
              }
            } finally {
              activeQuinn.close()
            }
          }

          override suspend fun defer(node: KSAnnotated) {
            check(isServiceReadyToStart.isCompleted) {
              "Cannot invoke defer before processing has started."
            }
            check(!isFinished()) { "Cannot invoke defer after the final round." }
            val activeQuinn = currentRoundQuinn
            check(activeQuinn != null) { "Cannot invoke defer between KSP rounds." }

            activeQuinn.queueAtBack { currentRoundDeferred!!.add(node) }
          }
        }

    override fun process(resolver: Resolver): List<KSAnnotated> = runBlocking {
      val proxyBreaker = CompletableDeferred<Unit>()
      val proxyScope = CoroutineScope(coroutines.cpuDispatcher())
      var proxyJob: Job? = null

      try {
        withContext(coroutines.ioDispatcher()) {
          isServiceReadyToStart.await()

          val activeQuinn = quinn.quinnFactory().createQuinn<KspContext>()
          currentRoundQuinn = activeQuinn
          currentRoundDeferred = mutableListOf()

          // Launch the single proxy job that will manage both bridging gaps.
          proxyJob =
              proxyScope.launch {
                // PHASE 1: Bridge the gap TO the execute call.
                // We use runBlocking to ensure the JVM thread is held hostage, preventing
                // IdleableDispatcher from registering as idle during the handoff.
                runBlocking {
                  activeQuinn.isExecuting.first { it }

                  // Now that execute() is definitely running, it's safe to start the round!
                  // We emit here so that the KspService cannot possibly complete the round
                  // before execute() has actually started.
                  roundStartEvents.emit(Unit)
                }

                // PHASE 2: The "Suspended" Idle Zone.
                // execute() has started. We use a standard suspend. Because we are suspended,
                // IdleableDispatcher drops our task. The dispatcher is now idle.
                // Tests can safely awaitAllIdle() during KSP rounds.
                activeQuinn.isExecuting.first { !it }

                // PHASE 3: Bridge the gap FROM the execute call.
                // isExecuting just became false via close() on another thread.
                // Resuming from the suspension above synchronously incremented the dispatcher's
                // submittedTaskCount. We instantly enter runBlocking again to hold the
                // dispatcher hostage during native teardown.
                runBlocking { proxyBreaker.await() }
              }
        }

        // Runs until activeQuinn is closed in `completeRound` of the service
        val quinn = currentRoundQuinn!!
        try {
          quinn.execute(KspContext(environment, resolver))
        } finally {
          quinn.close()
        }

        val allDeferred = currentRoundDeferred!!.toList()
        currentRoundDeferred = null
        currentRoundQuinn = null

        withContext(coroutines.ioDispatcher()) {
          // Release the proxy block *after* claiming the ioDispatcher
          proxyBreaker.complete(Unit)
          proxyJob?.join()
        }

        return@runBlocking allDeferred
      } catch (e: Throwable) {
        // Not gated by `isServiceReadyToEnd` since errors should exit immediately.
        try {
          shutDownApplicationImmediately()
        } catch (destroyError: Throwable) {
          e.addSuppressed(destroyError)
        }
        throw e
      } finally {
        proxyBreaker.complete(Unit)
      }
    }

    override fun finish() {
      runBlocking {
        withContext(coroutines.ioDispatcher()) {
          isFinishedNormally.complete(Unit)
          shutDownApplicationAfterGate(isServiceReadyToEnd)
        }
      }
    }

    override fun onError() {
      runBlocking {
        withContext(coroutines.ioDispatcher()) {
          isFinishedErroneously.complete(Unit)
          // Not gated by `isServiceReadyToEnd` since errors should exit immediately.
          shutDownApplicationImmediately()
        }
      }
    }

    /** Whether round processing is finished for any reason. */
    private fun isFinished(): Boolean =
        isFinishedNormally.isCompleted || isFinishedErroneously.isCompleted
  }
}

private fun KSNode.getEnclosingFile(): KSFile? =
    when (this) {
      is KSFile -> this
      is KSDeclaration -> containingFile
      else -> parent?.getEnclosingFile()
    }

/** The Dagger component passed into the application to supply KSP-related dependencies. */
@Component
internal interface KspComponentImpl : Application.KspComponent {
  @Component.Factory
  interface Factory {
    fun create(
        @BindsInstance environment: SymbolProcessorEnvironment,
        @BindsInstance service: KspService
    ): KspComponentImpl
  }
}
