package com.jackbradshaw.coroutines.testing.launcher

import com.jackbradshaw.coroutines.CoroutinesScope
import com.jackbradshaw.coroutines.testing.dispatchers.Deferred as DeferredQualifier
import com.jackbradshaw.coroutines.testing.dispatchers.Eager
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope

@CoroutinesScope
class LauncherImpl
@Inject
constructor(
    private val testScope: TestScope,
    @Eager private val eagerDispatcher: TestDispatcher,
    @DeferredQualifier private val deferredDispatcher: TestDispatcher,
) : Launcher {
  override fun launchEagerly(block: suspend CoroutineScope.() -> Unit): Job =
      testScope.launch(eagerDispatcher) { block() }

  override fun launchDeferred(block: suspend CoroutineScope.() -> Unit): Job =
      testScope.launch(deferredDispatcher) { block() }

  override fun <T> asyncEagerly(block: suspend CoroutineScope.() -> T): Deferred<T> =
      testScope.async(eagerDispatcher) { block() }

  override fun <T> asyncDeferred(block: suspend CoroutineScope.() -> T): Deferred<T> =
      testScope.async(deferredDispatcher) { block() }
}
