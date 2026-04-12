package com.jackbradshaw.concurrency.pulsar.testing

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.coroutines.Cpu
import com.jackbradshaw.coroutines.testing.Coroutines
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/** Abstract tests for [TestPulsar] that all implementations should pass. */
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
abstract class TestPulsarTest {

  @Inject @Cpu lateinit var cpuContext: CoroutineContext

  @Inject @Coroutines lateinit var taskBarrier: TestingTaskBarrier

  @Test
  fun emitCalledOnce_emitsOnePulse() = runBlocking {
    setup()

    var collected = mutableListOf<Unit>()

    CoroutineScope(cpuContext).launch { subject().pulses().toList(collected) }
    taskBarrier.awaitAllIdle()

    subject().emit()
    taskBarrier.awaitAllIdle()

    assertThat(collected).isEqualTo(listOf(Unit))
  }

  @Test
  fun emitCalledRepeatedly_emitsRepeatedPulses() = runBlocking {
    setup()

    var collected = mutableListOf<Unit>()

    CoroutineScope(cpuContext).launch { subject().pulses().toList(collected) }
    taskBarrier.awaitAllIdle()

    repeat(3) { subject().emit() }
    taskBarrier.awaitAllIdle()

    assertThat(collected).isEqualTo(List(3) { Unit })
  }

  @Test
  fun emitNotCalled_doesNotEmitPulse() = runBlocking {
    setup()

    var collected = mutableListOf<Unit>()

    CoroutineScope(cpuContext).launch { subject().pulses().toList(collected) }
    taskBarrier.awaitAllIdle()

    assertThat(collected).isEqualTo(emptyList<Unit>())
  }

  abstract fun setup()

  abstract fun subject(): TestPulsar
}
