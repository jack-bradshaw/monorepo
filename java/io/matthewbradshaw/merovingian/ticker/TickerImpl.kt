package io.matthewbradshaw.merovingian.ticker

import io.matthewbradshaw.merovingian.MerovingianScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@MerovingianScope
class TickerImpl @Inject internal constructor() : Ticker {

  init {
    println("ticker init")
  }

  private val pulseDeltaS = MutableSharedFlow<TimeSec>(onBufferOverflow = BufferOverflow.SUSPEND)
  private var totalTimeS = 0f

  override fun totalTimeS() = totalTimeS
  override fun pulseDeltaS() = pulseDeltaS
  override fun pulseTotalS() = pulseDeltaS.map { totalTimeS }

  override suspend fun tick(timeSinceLastTickS: TimeSec) {
    totalTimeS += timeSinceLastTickS
    pulseDeltaS.emit(timeSinceLastTickS)
  }
}