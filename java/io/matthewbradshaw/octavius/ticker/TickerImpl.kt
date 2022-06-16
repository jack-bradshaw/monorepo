package io.matthewbradshaw.octavius.ticker

import io.matthewbradshaw.octavius.OctaviusScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.channels.BufferOverflow
import javax.inject.Inject

@OctaviusScope
class TickerImpl @Inject internal constructor() : Ticker {

  init {
    println("ticker init")
  }

  private val pulse = MutableSharedFlow<TimeSec>(onBufferOverflow = BufferOverflow.SUSPEND)
  private var netTimeSec = 0f

  override fun netTimeSec() = netTimeSec
  override fun pulse() = pulse
  override suspend fun tick(timeSinceLastTickSec: TimeSec) {
    netTimeSec += timeSinceLastTickSec
    pulse.emit(timeSinceLastTickSec)
  }
}