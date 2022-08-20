package io.jackbradshaw.otter.clock

import io.jackbradshaw.otter.otterScope
import io.jackbradshaw.otter.engine.Engine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@otterScope
class RenderingClock @Inject internal constructor(
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