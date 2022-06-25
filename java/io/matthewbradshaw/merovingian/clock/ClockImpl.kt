package io.matthewbradshaw.merovingian.clock

import io.matthewbradshaw.merovingian.MerovingianScope
import io.matthewbradshaw.merovingian.engine.Engine
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import javax.inject.Inject
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.Dispatchers

@MerovingianScope
class ClockImpl @Inject internal constructor(
  private val engine: Engine
) : Clock {

  init {
    engine.extractCoroutineScope().launch(Dispatchers.Default) {
      while (true) {
        val previousTotalTime = totalFlow.value
        totalFlow.value = engine.extractTotalTime()
        deltaFlow.value = totalFlow.value - previousTotalTime
      }
    }
  }

  private val totalFlow = MutableStateFlow<Double>(0.0)
  private val deltaFlow = MutableStateFlow<Double>(0.0)

  override fun totalSec() = totalFlow
  override fun deltaSec() = deltaFlow
}