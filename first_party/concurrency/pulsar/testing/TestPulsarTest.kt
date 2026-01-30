package com.jackbradshaw.concurrency.pulsar.testing

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test

/** Abstract tests for [TestPulsar] that all implementations should pass. */
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
abstract class TestPulsarTest {

  @Test
  fun emitCalledOnce_emitsOnePulse() = runBlocking {
    setup()

    var collected = mutableListOf<Unit>()

    testScope().launch(UnconfinedTestDispatcher(testScope().testScheduler)) {
      subject().pulses().toList(collected)
    }

    subject().emit()
    testScope().testScheduler.advanceUntilIdle()

    assertThat(collected).isEqualTo(listOf(Unit))
  }

  @Test
  fun emitCalledRepeatedly_emitsRepeatedPulses() = runBlocking {
    setup()

    var collected = mutableListOf<Unit>()

    testScope().launch(UnconfinedTestDispatcher(testScope().testScheduler)) {
      subject().pulses().toList(collected)
    }
    testScope().testScheduler.advanceUntilIdle()

    repeat(3) { subject().emit() }
    testScope().testScheduler.advanceUntilIdle()

    assertThat(collected).isEqualTo(List(3) { Unit })
  }

  @Test
  fun emitNotCalled_doesNotEmitPulse() = runBlocking {
    setup()

    var collected = mutableListOf<Unit>()

    testScope().launch(UnconfinedTestDispatcher(testScope().testScheduler)) {
      subject().pulses().toList(collected)
    }
    testScope().testScheduler.advanceUntilIdle()

    assertThat(collected).isEqualTo(emptyList<Unit>())
  }

  abstract fun setup()

  abstract fun subject(): TestPulsar

  abstract fun testScope(): TestScope
}
