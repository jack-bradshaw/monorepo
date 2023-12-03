package io.jackbradshaw.otter.timing

import io.jackbradshaw.otter.OtterScope
import io.jackbradshaw.otter.engine.core.EngineCore
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OtterScope
class RenderingClock @Inject internal constructor(private val engineCore: EngineCore) : Clock {

  init {
    engineCore.extractCoroutineScope().launch {
      while (true) {
        val previousTotalTime = totalFlow.value
        totalFlow.value = engineCore.extractTotalEngineRuntime()
        deltaFlow.value = totalFlow.value - previousTotalTime
      }
    }
  }

  private val totalFlow = MutableStateFlow<Double>(0.0)
  private val deltaFlow = MutableStateFlow<Double>(0.0)

  override fun totalSec() = totalFlow

  override fun deltaSec() = deltaFlow
}
