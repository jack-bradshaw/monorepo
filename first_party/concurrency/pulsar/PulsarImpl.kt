package com.jackbradshaw.concurrency.pulsar

import com.jackbradshaw.concurrency.ConcurrencyScope
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** Pulsar that uses an endless while loop internally to generate the pulses. */
@ConcurrencyScope
class PulsarImpl @Inject constructor() : Pulsar {
  override fun pulses(): Flow<Unit> = flow {
    while (true) {
      emit(Unit)
    }
  }
}
