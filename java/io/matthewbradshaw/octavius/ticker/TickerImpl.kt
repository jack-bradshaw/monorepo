package io.matthewbradshaw.octavius.ticker

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.channels.BufferOverflow
import javax.inject.Inject

class TickerImpl @Inject internal constructor() : Ticker {

  private val pulse = MutableSharedFlow<TimeSec>(onBufferOverflow = BufferOverflow.SUSPEND)
  private var netTimeSec = 0f

  override fun netTimeSec() = netTimeSec
  override fun pulse() = pulse
  override suspend fun tick(timeSinceLastTickSec: TimeSec) {
    netTimeSec += timeSinceLastTickSec
    pulse.emit(timeSinceLastTickSec)
  }
}