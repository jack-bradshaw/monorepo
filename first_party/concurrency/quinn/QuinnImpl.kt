
package com.jackbradshaw.concurrency.quinn


import com.jackbradshaw.concurrency.quinn.Quinn.ErrorBehaviour
import com.jackbradshaw.concurrency.quinn.Quinn.InsertionResult
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
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

   * Guards [execute].

   *
   * This lock is used in the [execute] block. It ensures that only one call to [execute] is
   * draining the work queue at any given time.
   */
  private val executeLock = Mutex()


  /**

   * Mutex guarding [blockQueue].
   *
   * Used in two places: Inserting into the queue in [tryQueueInternal] and draining the queue in
   * [close]. This prevents general write races when multiple threads try to insert concurrently,
   * and since [close] sets [shouldAcceptNewBlocks] `false` before beginning the drain, it ensures
   * the drain operation waits until all pending insertions have released the lock and the queue is
   * guaranteed to have no more insertions.
   */
  private val queueLock = Mutex()

  /** Whether new blocks are presently being accepted. */
  private val shouldAcceptNewBlocks = MutableStateFlow(true)

  /** Whether the queue of blocks is presently being consumed. */
  private val shouldProcessExistingBlocks = MutableStateFlow(true)

  /** Signal to indicate a new entry has been added to the queue and execution should resume. */
  private val executionSignal = Channel<Unit>(1, BufferOverflow.DROP_OLDEST)

  /** The queue of unprocessed blocks. */
  private val blockQueue = ArrayDeque<ConsumableBlock<T>>()

  private val isSealed = MutableStateFlow(false)

  private val isFinishedClosing = MutableStateFlow(false)


  override val hasTerminalState = isSealed

  override val hasTerminatedProcesses = isFinishedClosing


  private val _isExecuting = MutableStateFlow(false)

  override val isExecuting = _isExecuting.asStateFlow()

  private val executeCallCount = AtomicInteger(0)

  override suspend fun queueAtBack(errorBehaviour: ErrorBehaviour, block: (T) -> Unit) {

    check(tryQueueAtBack(errorBehaviour, block) != Quinn.InsertionResult.REJECTED_CLOSED) {

      "This Quinn instance is closed, queueAtBack cannot be used."
    }
  }


  override suspend fun tryQueueAtBack(
      errorBehaviour: ErrorBehaviour,
      block: (T) -> Unit
  ): Quinn.InsertionResult = tryQueueInternal(block, errorBehaviour, atFront = false)

  override suspend fun queueAtFront(errorBehaviour: ErrorBehaviour, block: (T) -> Unit) {

    check(tryQueueAtFront(errorBehaviour, block) != Quinn.InsertionResult.REJECTED_CLOSED) {

      "This Quinn instance is closed, queueAtFront cannot be used."
    }
  }


  override suspend fun tryQueueAtFront(
      errorBehaviour: ErrorBehaviour,
      block: (T) -> Unit
  ): Quinn.InsertionResult = tryQueueInternal(block, errorBehaviour, atFront = true)

  private suspend fun tryQueueInternal(
      block: (T) -> Unit,
      errorBehaviour: ErrorBehaviour,
      atFront: Boolean
  ): Quinn.InsertionResult {
    // Early exit. Not strictly necessary, but it avoids redundant work.
    if (!shouldAcceptNewBlocks.value) return Quinn.InsertionResult.REJECTED_CLOSED

    val consumableBlock = ConsumableBlock(errorBehaviour, block)

    queueLock.withLock {
      if (shouldAcceptNewBlocks.value) {
        if (atFront) {
          blockQueue.addFirst(consumableBlock)
        } else {
          blockQueue.addLast(consumableBlock)
        }
        executionSignal.trySend(Unit)
      } else {
        // Notify caller block was not executed.
        return Quinn.InsertionResult.REJECTED_CLOSED
      }
    }

    val finalOutcome = consumableBlock.outcome.await()
    if (finalOutcome is ConsumableBlock.Outcome.ExecutedWithError &&
        errorBehaviour == ErrorBehaviour.DELIVER_TO_SUBMISSION_SIDE) {
      throw finalOutcome.error
    }

    return if (finalOutcome is ConsumableBlock.Outcome.NotExecuted) {
      Quinn.InsertionResult.INSERTED_NOT_RUN
    } else {

      Quinn.InsertionResult.INSERTED_AND_RUN

    }
  }

  override suspend fun execute(resource: T) {
    if (executeCallCount.incrementAndGet() > 0) {
      _isExecuting.value = true
    }
    try {
      executeLock.withLock {

        // Early exit. Not strictly necessary, but it avoids redundant work.
        if (!shouldProcessExistingBlocks.value) return

        executionSignal.receiveAsFlow().collect {
          while (true) {
            val block = queueLock.withLock { blockQueue.removeFirstOrNull() } ?: break

            if (!shouldProcessExistingBlocks.value) {
              block.outcome.complete(ConsumableBlock.Outcome.NotExecuted)

              break
            }

            try {

              block.block.invoke(resource)
              block.outcome.complete(ConsumableBlock.Outcome.ExecutedSuccessfully)
            } catch (t: Throwable) {
              block.outcome.complete(ConsumableBlock.Outcome.ExecutedWithError(t))

              if (block.errorBehaviour == ErrorBehaviour.DELIVER_TO_EXECUTION_SIDE) {

                throw t
              }
            }
          }
        }
      }
    } finally {
      if (executeCallCount.decrementAndGet() == 0) {
        _isExecuting.value = false
      }
    }
  }



  override fun close() {
    runBlocking {
      seal()
      stopProcessing()
      drainQueue()

      awaitHalt()
      isFinishedClosing.value = true
    }
  }

  /** Prevents new blocks from being inserted into the queue. */
  private fun seal() {
    shouldAcceptNewBlocks.value = false
    isSealed.value = true
  }

  /** Prevents further processing of blocks already in the queue. */
  private fun stopProcessing() {
    shouldProcessExistingBlocks.value = false
    executionSignal.close()
  }

  /** Drains existing blocks in the queue without executing them */
  private suspend fun drainQueue() {
    queueLock.withLock {
      while (true) {
        val block = blockQueue.removeFirstOrNull() ?: break
        block.outcome.complete(ConsumableBlock.Outcome.NotExecuted)

      }
    }
  }


  /** Waits until queue execution has completely halted. */
  private suspend fun awaitHalt() {
    isExecuting.first { !it }
  }

  /** Factory that provides [QuinnImpl] instances. */
  class FactoryImpl @Inject internal constructor() : Quinn.Factory {

    override fun <T> createQuinn(): Quinn<T> = QuinnImpl()
  }
}


/** A block and an associated observable flag to track processing. */
private data class ConsumableBlock<T>(
    val errorBehaviour: ErrorBehaviour,
    val block: (T) -> Unit,
) {

  /** The outcome of processing, holds null if not processed yet. */
  val outcome = CompletableDeferred<Outcome>()

  /** The outcome of processing [block]. */
  sealed class Outcome {
    /** Block was processed without error. */
    object ExecutedSuccessfully : Outcome()

    /** Block threw [error] while processing. */
    class ExecutedWithError(val error: Throwable) : Outcome()

    /** Block was evaluated for execution but not executed. */

    object NotExecuted : Outcome()
  }
}
