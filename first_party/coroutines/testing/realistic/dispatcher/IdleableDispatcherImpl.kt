package com.jackbradshaw.coroutines.testing.realistic.dispatcher

import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.LongAdder
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher

/**
 * A [IdleableDispatcher] that uses thread pools internally.
 *
 * The implementation uses two thread pools, one for active execution of work, and one for
 * timing-related tasks (e.g. delays, timeouts, etc). Both must be inactive for idle state to be
 * reached; furthermore, the active work executor must have no presently executing work and no
 * scheduled work.
 *
 * [LongAdder]s are used to track work state because they offer thread-safe simultaneous access by
 * multiple threads without the bottleneck imposed by atomic constructs such as [AtomicInteger].
 *
 * Class is not
 */
@OptIn(InternalCoroutinesApi::class)
class IdleableDispatcherImpl @Inject constructor() : IdleableDispatcher() {

  /**
   * The number of tasks that have been submitted to the main executor (excludes pending work
   * queue).
   */
  private val mainExecutorSubmittedTaskCount = LongAdder()

  /** The number of tasks that have completed work on the main executor. */
  private val mainExecutorCompletedTaskCount = LongAdder()

  /** The number of tasks that have been submitted to the timing executor. */
  private val timingExecutorSubmittedTaskCount = LongAdder()

  /** The number of tasks that have completed work on the timing executor. */
  private val timingExecutorCompletedTaskCount = LongAdder()

  /** Executor to use for main work execution. Uses four threads to replicate common */
  private val mainExecutor =
      object :
          ThreadPoolExecutor(
              /* corePoolSize= */ MAIN_THREAD_COUNT,
              /* maximumPoolSize= */ MAIN_THREAD_COUNT,
              /* keepAliveTime= */ 0L,
              /* unit= */ TimeUnit.MILLISECONDS,
              /* workQueue= */ LinkedBlockingQueue<Runnable>()) {

        override fun execute(command: Runnable) {
          this@IdleableDispatcherImpl.mainExecutorSubmittedTaskCount.increment()
          super.execute(command)
        }

        override fun afterExecute(r: Runnable?, t: Throwable?) {
          super.afterExecute(r, t)
          this@IdleableDispatcherImpl.mainExecutorCompletedTaskCount.increment()
        }
      }

  /** Executor to use for timing-related tasks (e.g. delay, timeout, etc). */
  private val timingExecutor =
      ScheduledThreadPoolExecutor(/* corePoolSize= */ 1).apply { removeOnCancelPolicy = true }

  /** The [mainExecutor] as a coroutine dispatcher. */
  private val mainExecutorAsDispatcher = mainExecutor.asCoroutineDispatcher()

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    mainExecutorAsDispatcher.dispatch(context, block)
  }

  override fun scheduleResumeAfterDelay(
      timeMillis: Long,
      continuation: CancellableContinuation<Unit>
  ) {

    timingExecutorSubmittedTaskCount.increment()

    val waitForDelay =
        timingExecutor.schedule(
            {
              timingExecutorCompletedTaskCount.increment()
              continuation.resumeWith(Result.success(Unit))
            },
            timeMillis,
            TimeUnit.MILLISECONDS)

    continuation.invokeOnCancellation {
      val cancelledSuccessfully = waitForDelay.cancel(/* mayInterruptIfRunning= */ false)
      if (cancelledSuccessfully) timingExecutorCompletedTaskCount.increment()
    }
  }

  override fun invokeOnTimeout(
      timeMillis: Long,
      block: Runnable,
      context: CoroutineContext
  ): DisposableHandle {

    timingExecutorSubmittedTaskCount.increment()

    val waitForTimeout =
        timingExecutor.schedule(
            {
              timingExecutorCompletedTaskCount.increment()
              block.run()
            },
            timeMillis,
            TimeUnit.MILLISECONDS)

    return object : DisposableHandle {
      override fun dispose() {
        val cancelledSuccessfully = waitForTimeout.cancel(/* mayInterruptIfRunning= */ false)
        if (cancelledSuccessfully) timingExecutorCompletedTaskCount.increment()
      }
    }
  }

  override fun isIdle(): Boolean {
    return mainExecutorSubmittedTaskCount.sum() == mainExecutorCompletedTaskCount.sum() &&
        mainExecutor.queue.isEmpty() &&
        timingExecutorSubmittedTaskCount.sum() == timingExecutorCompletedTaskCount.sum()
  }

  companion object {
    /** Closely mirrors common production machines. */
    private const val MAIN_THREAD_COUNT = 4

    /** Closely mirrors common production machines. */
    private const val SCHEDULED_THREAD_COUNT = 4
  }
}
