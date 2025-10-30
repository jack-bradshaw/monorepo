package com.jackbradshaw.concurrency.pulsar

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

/** Abstract tests for [Pulsar] that all implementation should pass. */
abstract class PulsarTest {

  @Test
  fun pulse_emits() = runBlocking {
    setupSubject()

    val emissions = subject().pulses().take(100).toList()

    assertThat(emissions).isEqualTo(List(100) { Unit })
  }

  abstract fun setupSubject()

  abstract fun subject(): Pulsar
}
