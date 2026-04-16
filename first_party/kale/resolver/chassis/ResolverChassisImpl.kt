package com.jackbradshaw.kale.resolver.chassis

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.resourcemanager.ResourceManager
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.model.Versions
import com.jackbradshaw.kale.provider.ProviderRunner
import com.jackbradshaw.kale.resolver.chassis.ResolverChassis.ResolverHarness
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * [ResolverChassis] that executes on [providerRunner].
 *
 * The implementation works as follows:
 * 1. The various `open` functions begin KSP execution and provide a harness linked to the run.
 * 2. The harness provides functions for feeding into a processing queue.
 * 3. The KSP run occurs on a background coroutine that is cancelled when the harness is closed.
 * 4. The KSP run pulls from the queue in the `process` callback and runs each block in the context
 *    of KSP (i.e. on the KSP thread as part of the KSP run).
 * 5. The KSP run continually polls the queue so it never completes on its own.
 *
 * This effectively turns KSP into a pub/sub processor so that blocks can be defined elsewhere and
 * executed in its context. This approach is necessary because of how KSP handles threading. Any
 * attempt to hoist its resources out of the `process` callback and keep KSP in a suspended state
 * results in the resources becoming useless because KSP resources depend on the KSP thread being
 * unblocked to function correctly.
 */
class ResolverChassisImpl
@Inject
internal constructor(
    private val providerRunner: ProviderRunner,
    private val resourceManagerFactory: ResourceManager.Factory,
    @Io private val coroutineContext: CoroutineContext
) : ResolverChassis {

  /**
   * Tracks all open sessions so they can be closed when this chassis is closed.
   *
   * There are no other states/processes so [hasTerminalState], [hasTerminatedProcesses], and
   * [close] all delegate to this manager.
   */
  private val resourceManager =
      resourceManagerFactory.createResourceManager<StubKey, CompilationSession>()

  override val hasTerminalState = resourceManager.hasTerminalState

  override val hasTerminatedProcesses = resourceManager.hasTerminatedProcesses

  override suspend fun open(
      sources: Set<Source>,
      versions: Versions,
      options: Map<String, String>
  ): ResolverHarness {
    return resourceManager
        .getOrPut(StubKey()) { CompilationSession(sources, versions, options) }
        .harness
  }

  override suspend fun open(
      source: Source,
      versions: Versions,
      options: Map<String, String>
  ): ResolverHarness {
    return open(setOf(source), versions, options)
  }

  override fun close() {
    runBlocking { resourceManager.close() }
  }

  /**
   * Runs KSP, exposes a [harness] for feeding blocks into the compilation, and processes each. When
   * this chassis is closed, the compilation is cancelled and all unprocessed blocks are discarded.
   */
  private inner class CompilationSession(
      private val sources: Set<Source>,
      private val versions: Versions,
      private val options: Map<String, String>
  ) : ObservableClosable {

    /**
     * Whether this compilation session has entered into its terminal state. Will be set in [close]
     * and will never become `false` after becoming `true`.
     */
    private val _hasTerminalState = MutableStateFlow<Boolean>(false)

    /**
     * Whether this compilation session has finished all background processing. Will be set in
     * [close] and will never become `false` after becoming `true`.
     */
    private val _hasTerminatedProcesses = MutableStateFlow<Boolean>(false)

    /** Coroutine Job linked to [coroutineScope]. Exists so the scope can be cancelled. */
    private val coroutineScopeHandle = Job()

    /** A coroutine scope for the KSP run. */
    private val coroutineScope = CoroutineScope(coroutineContext + coroutineScopeHandle)

    /** The queue of blocks yet to be processed. */
    private val blockQueue = Channel<ConsumableBlock>(BLOCK_QUEUE_BUFFER_SIZE)

    init {
      coroutineScope.launch {
        val provider =
            object : SymbolProcessorProvider {
              override fun create(environment: SymbolProcessorEnvironment) = createProcessor()
            }
        providerRunner.runProvider(provider, sources, versions, options)
      }
    }

    /**
     * The harness for accessing this run.
     *
     * Sends blocks into the queue for processing in a KSP context.
     *
     * Uses a try/catch to guard against the race condition where the resource is closed between the
     * closure check at the beginning of the `withResolver` callback and the subsequent `send`.
     * While it's better to avoid catches where possible, the alternative of putting the `send`
     * inside the closure check is unacceptable, because it requires holding the lock until the
     * blockQueue can accept the element, which involves placing a potential suspending point within
     * a lock check, which is an antipattern.
     *
     * Since the harness represents the final exposure for consumers, the closure
     * functions/properties delegate to the session to ensure harness closure causes session closure
     * (and vice versa).
     */
    val harness =
        object : ResolverHarness {
          override suspend fun withResolver(block: (Resolver) -> Unit) {
            check(!hasTerminalState.value) {
              "This harness is closed, withResolver cannot be used."
            }

            val consumableBlock = ConsumableBlock(block)

            try {
              // Send block for processing by KSP.
              blockQueue.send(consumableBlock)
            } catch (e: ClosedSendChannelException) {
              error("This harness is closed, withResolver cannot be used.")
            }

            // Wait until KSP has processed the block.
            consumableBlock.isConsumed.filter { it }.first()
          }

          override val hasTerminalState
            get() = this@CompilationSession.hasTerminalState

          override val hasTerminatedProcesses
            get() = this@CompilationSession.hasTerminatedProcesses

          override fun close() {
            this@CompilationSession.close()
          }
        }

    override val hasTerminalState = _hasTerminalState.asStateFlow()

    override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

    override fun close() {
      _hasTerminalState.value = true
      blockQueue.close()
      runBlocking { coroutineScopeHandle.cancelAndJoin() }
      _hasTerminatedProcesses.value = true
    }

    /**
     * Creates a [SymbolProcessor] that pulls from the [blockQueue] in [process] and evaluates each.
     *
     * To ensure the queue is drained after closure, every value entered into the queue is polled
     * and marked as consumed, but blocks are only executed if the session is open.
     *
     * The process is not started when returned (only created).
     */
    private fun createProcessor(): SymbolProcessor {
      return object : SymbolProcessor {
        override fun process(resolver: Resolver): List<KSAnnotated> {
          // Called on IO dispatcher since KSP was invoked from an IO-bound coroutine.
          runBlocking {
            for (consumableBlock in blockQueue) {
              if (!hasTerminalState.value) consumableBlock.block.invoke(resolver)
              consumableBlock.isConsumed.value = true
            }
          }
          return emptyList()
        }
      }
    }
  }

  companion object {
    /**
     * The size of the block buffer.
     *
     * Chosen as a vast overestimate to ensure queue insertion does not suspend in most cases.
     */
    private const val BLOCK_QUEUE_BUFFER_SIZE = 10000
  }
}

/**
 * A block and an associated observable flag to track whether the block has been consumed.
 *
 * The flag must be externally set because [block] will not automatically update it when it
 * completes. The flag does not necessarily mean the block ran, but rather it has been processed,
 * with the processor acting as the ultimate arbiter of whether to run the block or skip over it.
 */
private data class ConsumableBlock(
    val block: (Resolver) -> Unit,
) {
  val isConsumed = MutableStateFlow<Boolean>(false)
}

/**
 * ResourceManager is a key-value map but association is not needed in ResolverChassisImpl as it
 * effectively functions as a set. This class returns false for equals and has a dummy hash code
 * implementation to ensure each value inserted into the manager has a unique key (effectively
 * turning the map into a set).
 */
class StubKey {
  override fun equals(other: Any?) = false

  override fun hashCode() = 0
}
