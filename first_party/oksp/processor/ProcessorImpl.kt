package com.jackbradshaw.oksp.processor

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.oksp.model.SourceFile
import com.jackbradshaw.oksp.processor.Processor as OkspProcessor
import com.jackbradshaw.oksp.service.ProcessingService
import com.jackbradshaw.quinn.Quinn
import com.jackbradshaw.quinn.QuinnImpl
import java.util.LinkedList
import javax.inject.Inject
import javax.inject.Provider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProcessorImpl
@Inject
constructor(
    private val environment: SymbolProcessorEnvironment,
    @Io private val coroutineContext: CoroutineContext,
    private val quinnProvider: Provider<QuinnImpl<Resolver>>
) : OkspProcessor, ProcessingService {

  private val scope = CoroutineScope(coroutineContext)

  /**
   * Emits when KSP notifies this processor that no more rounds will be delivered to [process]. The
   * KSP contract guarantees the signal will occur after the last [process] call has returned.
   * Replay and overflow strategy ensure this flow is historical and blocks the emitter until all
   * downstream subscribers have completed, meaning late subscribers will always receive the signal,
   * and all subscribers have an opportunity to clean up resources. This behaviour is important and
   * rules out using a MutableStateFlow because [finish] returning is usually followed by process
   * termination so must be held open until receives have processed it.
   */
  private val terminationSignal =
      MutableSharedFlow<Unit>(replay = 1, onBufferOverflow = BufferOverflow.SUSPEND)

  /** The [Quinn] isolated execution scope for the present round. */
  private var currentQuinn: Quinn<Resolver>? = null

  /**
   * Emits each time a new round is started by KSP. The configuration ensures this flow is passive,
   * meaning any missed events are discarded and the supplier does not block. Note that [process]
   * explicitly blocks at the start of each round until there is at least one subscriber to ensure
   * every round is processed by at least one downstream subscriber.
   */
  private val roundStartEvents =
      MutableSharedFlow<Unit>(
          replay = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = 1)

  /**
   * Emits each time a round is completed by a downstream consumer. The configuration ensures this
   * flow is passive, meaning any missed events are discarded and the supplier does not block.
   */
  private val roundCompleteEvents =
      MutableSharedFlow<Unit>(
          replay = 0, onBufferOverflow = BufferOverflow.DROP_OLDEST, extraBufferCapacity = 1)

  /**
   * Emits each time a value is deferred by a downstream subscriber. Channel is used instead of
   * SharedFlow to allow suspending behavior without replay buffering, and is adequate since there
   * is exactly one consumer (the collector in the [process] callback).
   */
  private val roundDeferredValues =
      Channel<KSAnnotated>(
          capacity = DEFERRED_BUFFER_SIZE, onBufferOverflow = BufferOverflow.SUSPEND)

  override fun observeAllRoundsCompleteEvent(): SharedFlow<Unit> = terminationSignal

  override fun process(resolver: Resolver): List<KSAnnotated> = runBlocking {
    val allDeferred = LinkedList<KSAnnotated>()
    val collectAllDeferred =
        scope.launch {
          roundDeferredValues.consumeAsFlow().onEach { allDeferred.add(it) }.collect()
        }

    // Processing cannot begin until at least one observer is present.
    roundStartEvents.subscriptionCount.filter { it > 0 }.first()

    val quinn = quinnProvider.get()
    currentQuinn = quinn
    roundStartEvents.emit(Unit)

    val roundMonitor = scope.launch {
      roundCompleteEvents.first()
      quinn.close()
      collectAllDeferred.cancel()
    }

    quinn.drain(resolver)

    roundMonitor.join()
    currentQuinn = null

    return@runBlocking allDeferred
  }

  override fun finish() {
    runBlocking {
      currentQuinn?.close()
      currentQuinn = null

      // Termination cannot complete until at least one observer is present.
      terminationSignal.subscriptionCount.filter { it > 0 }.first()
      terminationSignal.emit(Unit)
    }
  }

  override fun onRoundStart(): Flow<Unit> = roundStartEvents

  override suspend fun withResolver(block: (Resolver) -> Unit) {
    val quinn =
        currentQuinn
            ?: error(
                "Resolver is not available yet. Processing has not started. Call `withResolver` after " +
                    "the first emission from `onRoundStart` and before " +
                    "`observeAllRoundsCompleteState` emits true."
            )
    quinn.submit(block)
  }

  override suspend fun publishSource(source: SourceFile, anchors: List<KSNode>) {
    val dependencyFiles = anchors.mapNotNull { it.getEnclosingFile() }.toTypedArray()
    val dependencies = Dependencies(aggregating = true, *dependencyFiles)

    environment.codeGenerator
        .createNewFile(
            dependencies = dependencies,
            packageName = source.packageName,
            fileName = source.fileName,
            extensionName = source.extension)
        .use { it.write(source.contents.toByteArray()) }
  }

  override suspend fun publishError(error: Throwable, anchor: KSNode?) {
    if (anchor != null) {
      environment.logger.error(error.toString(), anchor)
    } else {
      environment.logger.error(error.toString())
    }
  }

  override suspend fun publishError(error: String, anchor: KSNode?) {
    if (anchor != null) {
      environment.logger.error(error, anchor)
    } else {
      environment.logger.error(error)
    }
  }

  override suspend fun publishDeferred(node: KSAnnotated) {
    roundDeferredValues.send(node)
  }

  override suspend fun completeRound() {
    roundCompleteEvents.emit(Unit)
  }

  private companion object {
    private const val DEFERRED_BUFFER_SIZE = 1000
  }
}

private fun KSNode.getEnclosingFile(): KSFile? =
    when (this) {
      is KSFile -> this
      is KSDeclaration -> containingFile
      else -> parent?.getEnclosingFile()
    }
