package io.jackbradshaw.otter.timing

import com.jme3.bullet.PhysicsSpace
import com.jme3.bullet.PhysicsTickListener
import com.jme3.bullet.control.GhostControl
import com.jme3.scene.Node
import io.jackbradshaw.otter.OtterScope
import io.jackbradshaw.otter.coroutines.renderingDispatcher
import io.jackbradshaw.otter.engine.core.EngineCore
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@OtterScope
class PhysicsClock @Inject internal constructor(private val engineCore: EngineCore) : Clock {

  private var totalRuntime = 0.0
  private val totalFlow = MutableStateFlow<Double>(0.0)
  private val deltaFlow = MutableStateFlow<Double>(0.0)

  init {
    runBlocking {
      val ghostControl =
          object : GhostControl(), PhysicsTickListener {
            override fun physicsTick(space: PhysicsSpace, tpf: Float) = runBlocking {
              totalRuntime += tpf
            }
            override fun prePhysicsTick(space: PhysicsSpace, tpf: Float) {}
          }
      val ghostNode = Node("physics_clock_ghost").apply { addControl(ghostControl) }

      withContext(engineCore.renderingDispatcher()) {
        engineCore.extractFrameworkNode().attachChild(ghostNode)
      }

      engineCore.extractPhysics().getPhysicsSpace().add(ghostControl)
      engineCore.extractCoroutineScope().launch(Dispatchers.Default) {
        while (true) {
          val previousTotalRuntime = totalFlow.value
          totalFlow.value = totalRuntime
          deltaFlow.value = totalRuntime - previousTotalRuntime
        }
      }
    }
  }

  override fun totalSec() = totalFlow
  override fun deltaSec() = deltaFlow
}
