package com.jackbradshaw.coroutines.testing.artificial.dispatcher

import com.jackbradshaw.coroutines.CoroutinesDaggerScope
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Delay
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler

/** A [AdvancableDispatcher] that delegates to a [StandardTestDispatcher]. */
@OptIn(ExperimentalCoroutinesApi::class, InternalCoroutinesApi::class)
@CoroutinesDaggerScope
class AdvancableDispatcherImpl @Inject internal constructor() :
    AdvancableDispatcher() {

  private val scheduler = TestCoroutineScheduler()
  
  private val dispatcher = StandardTestDispatcher(scheduler, name = "SinglethreadTestDispatcher")

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    dispatcher.dispatch(context, block)
  }

  override fun advanceBy(millis: Int) {
    scheduler.advanceTimeBy(millis.toLong())

    // Ensures that any work scheduled for the present time is executed.
    scheduler.runCurrent()
  }

  override fun scheduleResumeAfterDelay(timeMillis: Long, continuation: CancellableContinuation<Unit>) {
    (dispatcher as Delay).scheduleResumeAfterDelay(timeMillis, continuation)
  }

  override fun invokeOnTimeout(timeMillis: Long, block: Runnable, context: CoroutineContext): DisposableHandle {
    return (dispatcher as Delay).invokeOnTimeout(timeMillis, block, context)
  }
}
