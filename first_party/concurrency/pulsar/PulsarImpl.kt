package com.jackbradshaw.concurrency.pulsar

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/** Pulsar that uses an endless while loop internally to generate the pulses. */
@PulsarScope
class PulsarImpl @Inject constructor() : Pulsar {
  override fun pulses(): Flow<Unit> = flow {
    while (true) {
      emit(Unit)
    }
  }
}
