package io.jackbradshaw.otter.timing

import io.jackbradshaw.otter.OtterScope
import io.jackbradshaw.otter.engine.core.EngineCore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@OtterScope
class HostClock @Inject internal constructor(private val engineCore: EngineCore) : Clock {

  private val totalFlow = MutableStateFlow<Double>(0.0)
  private val deltaFlow = MutableStateFlow<Double>(0.0)

  init {
    runBlocking {
      engineCore.extractCoroutineScope().launch(Dispatchers.Default) {
        while (true) {
          val previousTotalRuntime = totalFlow.value
          totalFlow.value = System.currentTimeMillis().toDouble()
          deltaFlow.value = totalFlow.value - previousTotalRuntime
        }
      }
    }
  }

  override fun totalSec() = totalFlow
  override fun deltaSec() = deltaFlow
}
