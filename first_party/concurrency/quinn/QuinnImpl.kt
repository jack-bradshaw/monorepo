package com.jackbradshaw.concurrency.quinn

import com.jackbradshaw.concurrency.quinn.Quinn.ErrorHandling
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
   * Lock guarding [execute].
   *
   * This lock is used in the [execute] block. It ensures that only one call to [execute] is
   * draining the work queue at any given time.
   */
  private val executeLock = Mutex()

  /**
   * Lock guarding [taskQueue].
   *
   * Used in two places: Inserting into the queue in [tryQueueInternal] and draining the queue in
   * [close]. This prevents general write races when multiple threads try to insert concurrently,
   * and provides thread safety during closure. Since [close] sets [isSealed] `true` before
   * beginning the drain, guarding insertions ensures the drain operation occurs AFTER all pending
   * insertions are complete, which guarantees there are no write-races and ensures all pending work
   * is drained.
   */
  private val queueLock = Mutex()

  /** The queue of unprocessed tasks. */
  private val taskQueue = ArrayDeque<ConsumableTask<T>>()

  /** Whether new tasks should be rejected. */
  private val isSealed = MutableStateFlow(false)

  /** Whether execution has permanently ceased. */
  private val isExecutionStopped = MutableStateFlow(false)

  /** The number of calls to [execute] (includes the active call and all waiting calls). */
  private val executeCallCount = AtomicInteger(0)

  /** Whether there is presently at least one active call to [execute]. */
  private val _isExecuting = MutableStateFlow(false)

  /** Whether [close] has finished. */
  private val isFinishedClosing = MutableStateFlow(false)

  /** Signal to indicate a new entry has been added to the queue. */
  private val onTaskInserted = Channel<Unit>(1, BufferOverflow.DROP_OLDEST)

  override val hasTerminalState = isSealed

  override val hasTerminatedProcesses = isFinishedClosing

  override val isExecuting = _isExecuting.asStateFlow()

  override suspend fun queueAtBack(errorHandling: ErrorHandling, task: (T) -> Unit) {
    check(tryQueueAtBack(errorHandling, task) != Quinn.InsertionResult.REJECTED_CLOSED) {
      "This Quinn instance is closed, queueAtBack cannot be used."
    }
  }

  override suspend fun tryQueueAtBack(errorHandling: ErrorHandling, task: (T) -> Unit) =
      tryQueueInternal(task, errorHandling, atFront = false)

  override suspend fun queueAtFront(errorHandling: ErrorHandling, task: (T) -> Unit) {
    check(tryQueueAtFront(errorHandling, task) != Quinn.InsertionResult.REJECTED_CLOSED) {
      "This Quinn instance is closed, queueAtFront cannot be used."
    }
  }

  override suspend fun tryQueueAtFront(errorHandling: ErrorHandling, task: (T) -> Unit) =
      tryQueueInternal(task, errorHandling, atFront = true)

  private suspend fun tryQueueInternal(
      task: (T) -> Unit,
      errorHandling: ErrorHandling,
      atFront: Boolean
  ): Quinn.InsertionResult {
    // Early exit, not strictly necessary, but it avoids redundant work.
    if (isSealed.value) return Quinn.InsertionResult.REJECTED_CLOSED

    val consumableTask = ConsumableTask(errorHandling, task)

    queueLock.withLock {
      if (!isSealed.value) {
        if (atFront) {
          taskQueue.addFirst(consumableTask)
        } else {
          taskQueue.addLast(consumableTask)
        }
        onTaskInserted.trySend(Unit)
      } else {
        return Quinn.InsertionResult.REJECTED_CLOSED
      }
    }

    val finalOutcome = consumableTask.outcome.await()
    if (finalOutcome is ConsumableTask.Outcome.ExecutedWithError &&
        errorHandling == ErrorHandling.DELIVER_TO_SUBMISSION_SIDE) {
      throw finalOutcome.error
    }

    return if (finalOutcome is ConsumableTask.Outcome.NotExecuted) {
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
        // Early exit, not strictly necessary, but it avoids redundant work.
        if (isExecutionStopped.value) return

        onTaskInserted.receiveAsFlow().collect {
          while (true) {
            val task = queueLock.withLock { taskQueue.removeFirstOrNull() } ?: break

            if (isExecutionStopped.value) {
              task.outcome.complete(ConsumableTask.Outcome.NotExecuted)
              break
            }

            try {
              task.task.invoke(resource)
              task.outcome.complete(ConsumableTask.Outcome.ExecutedSuccessfully)
            } catch (t: Throwable) {
              task.outcome.complete(ConsumableTask.Outcome.ExecutedWithError(t))
              if (task.errorHandling == ErrorHandling.DELIVER_TO_EXECUTION_SIDE) {
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
      awaitExecutionFinished()
      declareClosed()
    }
  }

  /** Prevents new tasks from being inserted into the queue. */
  private fun seal() {
    isSealed.value = true
  }

  /** Prevents further processing of tasks already in the queue. */
  private fun stopProcessing() {
    isExecutionStopped.value = true
    onTaskInserted.close()
  }

  /** Drains the task queue without execution. */
  private suspend fun drainQueue() {
    queueLock.withLock {
      while (true) {
        val task = taskQueue.removeFirstOrNull() ?: break
        task.outcome.complete(ConsumableTask.Outcome.NotExecuted)
      }
    }
  }

  /** Waits until all calls to [execute] have resumed. */
  private suspend fun awaitExecutionFinished() {
    isExecuting.first { !it }
  }

  /** Marks this [Quinn] as definitively closed. */
  private suspend fun declareClosed() {
    isFinishedClosing.value = true
  }

  /** Factory that provides [QuinnImpl] instances. */
  @QuinnScope
  class Factory @Inject internal constructor() : Quinn.Factory {
    override fun <T> createQuinn(): Quinn<T> = QuinnImpl()
  }
}

/** A task, its associated error handling, and a flag to track the processing outcome. */
private data class ConsumableTask<T>(
    val errorHandling: ErrorHandling,
    val task: (T) -> Unit,
) {

  /** The outcome of processing. Completes when processing completes. */
  val outcome = CompletableDeferred<Outcome>()

  /** The outcome of processing [task]. */
  sealed class Outcome {
    /** The task ran and completed without error. */
    object ExecutedSuccessfully : Outcome()

    /** The task ran and threw [error]. */
    class ExecutedWithError(val error: Throwable) : Outcome()

    /** The task was removed from the queue by the executor but was not run. */
    object NotExecuted : Outcome()
  }
}
