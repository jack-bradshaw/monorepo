package com.jackbradshaw.coroutines.testing.realistic.dispatcher

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

/** Abstract tests that all [IdleableDispatcher] implementations should pass. */
abstract class IdleableDispatcherTest {

  @After
  fun tearDown() = runBlocking {
    // In case a test failed before cancelling work.
    stopLongRunningWork()
  }

  @Test
  fun isIdle_returnsTrueInitially() = runBlocking {
    val dispatcher = subject()
    
    assertThat(dispatcher.isIdle()).isTrue()
  }

  @Test
  fun isIdle_returnsFalseWhileWorking() = runBlocking {
    val dispatcher = subject()

    startLongRunningWork()
    
    assertThat(dispatcher.isIdle()).isFalse()
  }

  @Test
  fun isIdle_returnsTrueAfterWorkCompletes() = runBlocking {
    val dispatcher = subject()

    startLongRunningWork()
    stopLongRunningWork()
    
    assertThat(dispatcher.isIdle()).isTrue()
  }

  /** Returns the subject under test. The same instance must be returned on each call. */
  abstract fun subject(): IdleableDispatcher
  
  /** Starts long running work and blocks until the work has been started (but not completed). */
  abstract fun startLongRunningWork()
  
  /** Stops the long running work started by [startLongRunningWork] and blocks until the work has been stopped. */
  abstract suspend fun stopLongRunningWork()
}
