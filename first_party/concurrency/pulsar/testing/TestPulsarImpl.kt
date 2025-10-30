package com.jackbradshaw.concurrency.pulsar.testing

import com.jackbradshaw.concurrency.ConcurrencyScope
import javax.inject.Inject
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow

/** Implementation of [TestPulsar] that uses a single `MutableSharedFlow` internally. */
@ConcurrencyScope
class TestPulsarImpl @Inject constructor() : TestPulsar {

  private val sharedFlow =
      MutableSharedFlow<Unit>(
          replay = 0, extraBufferCapacity = 0, onBufferOverflow = BufferOverflow.SUSPEND)

  override suspend fun emit() {
    sharedFlow.emit(Unit)
  }

  override fun pulses() = sharedFlow
}
