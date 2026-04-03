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

/** A [IdleableDispatcher] that uses a [ThreadPoolExecutor] internally.
 * 
 * Idle state is tracked through a combination of:
 * 
 * 1. Keeping track of what is presently executing by comparing stated/finished counts.
 * 2. Checking the length of the present processing queue.
 * 3. Checking for pending scheduled delays.
 * 
 * For point 1, `LongAdder`s are used instead of `AtomicInteger`s to improve cross-thread
 * performance.
 */
@OptIn(InternalCoroutinesApi::class)
class IdleableDispatcherImpl private constructor(threadCount: Int) : IdleableDispatcher() {

  private val completedTaskCount = LongAdder()

  private val scheduledTaskCount = LongAdder()
  
  private val delayedTaskCount = LongAdder()

  private val scheduledExecutor = ScheduledThreadPoolExecutor(1).apply {
    removeOnCancelPolicy = true
  }

  private val executor =
      object :
          ThreadPoolExecutor(
              /* corePoolSize= */ threadCount,
              /* maximumPoolSize= */ threadCount,
              /* keepAliveTime= */ 0L,
              /* unit= */ TimeUnit.MILLISECONDS,
              /* workQueue= */ LinkedBlockingQueue<Runnable>()) {

        override fun execute(command: Runnable) {
          this@IdleableDispatcherImpl.scheduledTaskCount.increment()
          super.execute(command)
        }

        override fun afterExecute(r: Runnable?, t: Throwable?) {
          super.afterExecute(r, t)
          this@IdleableDispatcherImpl.completedTaskCount.increment()
        }
      }

  private val executorAsDispatcher = executor.asCoroutineDispatcher()

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    executorAsDispatcher.dispatch(context, block)
  }
  
  override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
    delayedTaskCount.increment()
    val future =
        scheduledExecutor.schedule(
            {
              delayedTaskCount.decrement()
              continuation.resumeWith(Result.success(Unit))
            },
            timeMillis,
            TimeUnit.MILLISECONDS)

    continuation.invokeOnCancellation {
      if (future.cancel(false)) {
        delayedTaskCount.decrement()
      }
    }
  }

  override fun invokeOnTimeout(
      timeMillis: Long,
      block: Runnable,
      context: CoroutineContext
  ): DisposableHandle {
    delayedTaskCount.increment()
    val future =
        scheduledExecutor.schedule(
            {
              delayedTaskCount.decrement()
              block.run()
            },
            timeMillis,
            TimeUnit.MILLISECONDS)

    return object : DisposableHandle {
      override fun dispose() {
        if (future.cancel(false)) {
          delayedTaskCount.decrement()
        }
      }
    }
  }

  override fun isIdle(): Boolean {
    return scheduledTaskCount.sum() == completedTaskCount.sum() && 
           executor.queue.isEmpty() &&
           delayedTaskCount.sum() == 0L
  }

  class Factory @Inject internal constructor() : IdleableDispatcher.Factory {
    override fun create(threadCount: Int): IdleableDispatcher = IdleableDispatcherImpl(threadCount)
  }
}
