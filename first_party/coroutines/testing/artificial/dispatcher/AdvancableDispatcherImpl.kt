package com.jackbradshaw.coroutines.testing.artificial.dispatcher

import com.jackbradshaw.coroutines.CoroutinesDaggerScope
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

/** A [AdvancableDispatcher] that delegates to a [StandardTestDispatcher]. */
@OptIn(ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)
@CoroutinesDaggerScope
class AdvancableDispatcherImpl @Inject internal constructor() : AdvancableDispatcher() {

  /** The test scheduler that controls virtual time. */
  private val scheduler = TestCoroutineScheduler()

  /** A single-threaded dispatcher backed by [scheduler]. */
  private val delegate = StandardTestDispatcher(scheduler, name = "SinglethreadTestDispatcher")

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    delegate.dispatch(context, block)
  }

  override fun advanceBy(millis: Int) {
    scheduler.advanceTimeBy(millis.toLong())
    scheduler.runCurrent()
  }

  override fun scheduleResumeAfterDelay(
      timeMillis: Long,
      continuation: CancellableContinuation<Unit>
  ) {
    (delegate as Delay).scheduleResumeAfterDelay(timeMillis, continuation)
  }

  override fun invokeOnTimeout(
      timeMillis: Long,
      block: Runnable,
      context: CoroutineContext
  ): DisposableHandle {
    return (delegate as Delay).invokeOnTimeout(timeMillis, block, context)
  }
}
