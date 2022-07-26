package io.matthewbradshaw.jockstrap.clock

import io.matthewbradshaw.jockstrap.JockstrapScope
import io.matthewbradshaw.jockstrap.engine.Engine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@JockstrapScope
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