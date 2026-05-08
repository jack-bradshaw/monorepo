package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.jackbradshaw.concurrency.quinn.Production

import com.jackbradshaw.concurrency.quinn.Quinn
import com.jackbradshaw.concurrency.quinn.Quinn.ErrorHandling
import java.util.concurrent.atomic.AtomicInteger

class IdleableQuinnImpl<T>(@Production private val delegate: Quinn<T>) : IdleableQuinn<T> {

  private val submittedTasks = AtomicInteger(0)

  private val completedTasks = AtomicInteger(0)

  override fun isIdle(): Boolean {
    // We are idle if all submitted tasks have completed
    return submittedTasks.get() == completedTasks.get()
  }

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

  /* There is no need to increment the submitted/completed counts in execute becuase it is
  guaranteed to be idle when there are no submitted tasks running (i.e. when submitted task count
  equals completed task count). This is guaranteed by the Quinn interface contract. */
  override suspend fun execute(resource: T) = delegate.execute(resource)

  override val isExecuting = delegate.isExecuting

  override val hasTerminalState = delegate.hasTerminalState

  override val hasTerminatedProcesses = delegate.hasTerminatedProcesses

  override fun close() = delegate.close()
}
