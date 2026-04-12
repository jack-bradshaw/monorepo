package com.jackbradshaw.coroutines.testing.artificial.dispatcher

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.junit.Test

/** Abstract tests that all [AdvancableDispatcher] implementations should pass. */
abstract class AdvancableDispatcherTest {

  @Test
  fun advanceBy_nothingExecuting_doesNotFail() {
    val dispatcher = subject()

    dispatcher.advanceBy(100)
  }

  @Test
  fun advanceBy_advanceIntoTask_taskStillRunning() {
    val dispatcher = subject()
    var completed = false

    CoroutineScope(dispatcher).launch {
      delay(100)
      completed = true
    }

    dispatcher.advanceBy(99)

    assertThat(completed).isFalse()
  }

  @Test
  fun advanceBy_advanceThroughTask_taskComplete() {
    val dispatcher = subject()
    var completed = false

    CoroutineScope(dispatcher).launch {
      delay(100)
      completed = true
    }

    dispatcher.advanceBy(100)

    assertThat(completed).isTrue()
  }

  abstract fun subject(): AdvancableDispatcher
}
