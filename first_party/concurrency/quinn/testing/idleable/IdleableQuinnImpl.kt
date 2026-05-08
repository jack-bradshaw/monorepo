package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.Quinn.ErrorHandling
import com.jackbradshaw.concurrency.quinn.testing.prod.Prod
import java.util.concurrent.atomic.AtomicInteger

/**
 * Default implementation of [IdleableQuinn].
 *
 * The interceptor pattern is used to count the number of pending tasks and completed tasks. Idle
 * state is reached when either no executor is active, or the submitted count equals the completed
 * count. All functions delegate to the [delegate] to minimise the complexity of this class and
 * ensure it functions as a simple wrapper, including closure, so when [delegate] is closed, this is
 * closed.
 */
class IdleableQuinnImpl<T>(@Prod private val delegate: Quinn<T>) : IdleableQuinn<T> {

  /** The number of tasks that have been submitted (but not necessarily executed). */
  private val submittedTasks = AtomicInteger(0)

  /** The number of tasks that have been executed (including tasks dropped due to closure). */
  private val completedTasks = AtomicInteger(0)

  override val isExecuting = delegate.isExecuting

  override val hasTerminalState = delegate.hasTerminalState

  override val hasTerminatedProcesses = delegate.hasTerminatedProcesses

  override fun isIdle() = !isExecuting.value || submittedTasks.get() == completedTasks.get()

  override suspend fun queueAtBack(errorHandling: ErrorHandling, task: (T) -> Unit) {
    submittedTasks.incrementAndGet()
    try {
      delegate.queueAtBack(errorHandling, task)
    } finally {
      completedTasks.incrementAndGet()
    }
  }

  override suspend fun tryQueueAtBack(
      errorHandling: ErrorHandling,
      task: (T) -> Unit
  ): Quinn.InsertionResult {
    submittedTasks.incrementAndGet()
    try {
      return delegate.tryQueueAtBack(errorHandling, task)
    } finally {
      completedTasks.incrementAndGet()
    }
  }

  override suspend fun queueAtFront(errorHandling: ErrorHandling, task: (T) -> Unit) {
    submittedTasks.incrementAndGet()
    try {
      delegate.queueAtFront(errorHandling, task)
    } finally {
      completedTasks.incrementAndGet()
    }
  }

  override suspend fun tryQueueAtFront(
      errorHandling: ErrorHandling,
      task: (T) -> Unit
  ): Quinn.InsertionResult {
    submittedTasks.incrementAndGet()
    try {
      return delegate.tryQueueAtFront(errorHandling, task)
    } finally {
      completedTasks.incrementAndGet()
    }
  }

  override suspend fun execute(resource: T) = delegate.execute(resource)

  override fun close() = delegate.close()
}
