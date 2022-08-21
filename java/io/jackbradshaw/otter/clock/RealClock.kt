package io.jackbradshaw.otter.clock

import io.jackbradshaw.otter.OtterScope
import io.jackbradshaw.otter.engine.Engine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@OtterScope
class RealClock @Inject internal constructor(
  private val engine: Engine
) : Clock {

  private val totalFlow = MutableStateFlow<Double>(0.0)
  private val deltaFlow = MutableStateFlow<Double>(0.0)

  init {
    runBlocking {
      engine.extractCoroutineScope().launch(Dispatchers.Default) {
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