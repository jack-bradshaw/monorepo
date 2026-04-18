package com.jackbradshaw.quinn.core

import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Default implementation of [Quinn].
 *
 * Implemented using a coroutine channel for the work queue. Handles various edge cases and race
 * conditions with synchronous locks.
 */
class QuinnImpl<T> @Inject constructor() : Quinn<T> {

  /**
   * Guards closure.
   *
   * This lock is used in two places:
   * 1. Guarding setting [_hasTerminalState] to `true` when entering [close].
   * 2. Guarding dequeueing and evaluating a `block` in [tryExecute].
   *
   * This guards against a race condition:
   * 1. On thread 1: The [tryExecute] function checks the state, receives `open`, and dequeues the
   *    next block to run.
   * 2. On thread 2: The [close] function is called and sets the closed state to `closed`.
   * 3. On thread 1: The dequeued block is evaluated.
   *
   * This is erroneous because it means processing is continuing after closure has returned and
   * reported all processing terminated. By guarding both in a mutex, the race condition is
   * eliminated. Normally evaluating a `block` in the context of a mutex would be erroneous because
   * it can suspend, thus holding the lock indefinitely; however, in this case that is exactly the
   * desired behaviour. When [close] is called, it should block the calling thread until the
   * presently evaluated `block` is finished, even if processing has only dequeued it so far.
   */
  private val closureLock = Mutex()

  /**
   * Guards [execute] and [tryExecute].
   *
   * This lock is used in the [execute] block. It ensures that only one call to [execute] is
   * draining the work queue at any given time.
   */
  private val executeLock = Mutex()

  /** Whether this has entered a terminal state. */
  private val _hasTerminalState = MutableStateFlow(false)

  /** Whether this has finished all ongoing processing. */
  private val _hasTerminatedProcesses = MutableStateFlow(false)

  /** The queue of unprocessed blocks. */
  private val blockQueue = Channel<ConsumableBlock<T>>(BLOCK_QUEUE_BUFFER_SIZE)

  override val hasTerminalState = _hasTerminalState.asStateFlow()

  override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

  override suspend fun run(block: suspend (T) -> Unit) {
    check(tryRun(block)) { "This Quinn instance is closed, run cannot be used." }
  }

  override suspend fun tryRun(block: suspend (T) -> Unit): Boolean {
    if (hasTerminalState.value) return false

    val consumableBlock = ConsumableBlock(block)

    /*
     * This section handles the complex interaction between closure and channel submission in a way
     * that avoids race conditions while minimising the need for try/catch blocks.
     *
     * Calling `trySend` will either succeed, or it will fail in one of two ways:
     *
     * 1. Fail because the queue is full, with a 'failed not closed' result.
     * 2. Fail because the queue is closed, with a 'failed with closed' result.
     *
     * Failure 1 can happen at any time if the channel is overloaded, and failure 2 will occur if
     * closure happens after the check at the start of `tryRun` but before `trySend` (i.e. a race
     * condition).
     *
     * This section handles both cases by attempting a `trySend` and checks the result before
     * falling back to a suspending `send` in a try/catch block when the queue is full. This avoids
     * the performance overhead of using try/catch when the queue is open and not full, while
     * accounting for all edge cases and race conditions.
     *
     * The alternative to this entire section involves wrapping the queue submission in the close
     * lock, but that only addresses failure mode 2, and is undesirable since submission could block
     * indefinitely in the case of a full queue. This would block closure until enough blocks have
     * been processed to unlock, which violates the Quinn contract (guarantees closure only allows
     * the presently processing block to complete).
     */
    val result = blockQueue.trySend(consumableBlock)
    if (result.isFailure) {
      if (result.isClosed) {
        // Indicates race condition between initial check at start of tryRun and submission
        return false
      } else {
        // Indicates queue is full, so fall back to send in try/catch to suspend until submitted
        try {
          blockQueue.send(consumableBlock)
        } catch (e: ClosedSendChannelException) {
          return false
        }
      }
    }

    consumableBlock.isProcessed.filter { it }.first()
    return true
  }

  override suspend fun execute(resource: T) {
    if (hasTerminalState.value) return

    executeLock.withLock {
      if (hasTerminalState.value) return

      for (consumableBlock in blockQueue) {
        var shouldBreak = false

        closureLock.withLock {
          if (!hasTerminalState.value) {
            consumableBlock.block.invoke(resource)
            consumableBlock.isProcessed.value = true
          } else {
            consumableBlock.isProcessed.value = true
            shouldBreak = true
          }
        }

        if (shouldBreak) break
      }
    }
  }

  override fun close() {
    runBlocking {
      closureLock.withLock { _hasTerminalState.value = true }
      blockQueue.close()

      // Drains the queue without running any.
      for (pendingBlock in blockQueue) {
        pendingBlock.isProcessed.value = true
      }

      _hasTerminatedProcesses.value = true
    }
  }

  private companion object {
    /**
     * The size of the work queue buffer.
     *
     * Chosen as a vast overestimate to ensure queue insertion does not suspend in most cases.
     */
    private const val BLOCK_QUEUE_BUFFER_SIZE = 10000
  }

  /** Factory that provides [QuinnImpl] instances. */
  class FactoryImpl @Inject internal constructor() : Quinn.Factory {
    override fun <T> createQuinn(): Quinn<T> = QuinnImpl()
  }
}

/**
 * A block and an associated observable flag to track whether the block has been processed.
 *
 * The flag must be externally set, because [block] will not set it when it completed. The flag does
 * not necessarily mean the block ran, only that it has been evaluated by the processor. The
 * processor may decide to run the block or skip over it, and both count as being processed.
 */
private data class ConsumableBlock<T>(
    val block: suspend (T) -> Unit,
) {
  val isProcessed = MutableStateFlow(false)
}
