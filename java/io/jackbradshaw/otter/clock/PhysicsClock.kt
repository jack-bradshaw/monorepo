package io.jackbradshaw.otter.clock

import com.jme3.bullet.PhysicsSpace
import com.jme3.bullet.PhysicsTickListener
import com.jme3.bullet.control.GhostControl
import com.jme3.scene.Node
import io.jackbradshaw.otter.otterScope
import io.jackbradshaw.otter.coroutines.renderingDispatcher
import io.jackbradshaw.otter.engine.Engine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@otterScope
class PhysicsClock @Inject internal constructor(
  private val engine: Engine
) : Clock {

  private var totalRuntime = 0.0
  private val totalFlow = MutableStateFlow<Double>(0.0)
  private val deltaFlow = MutableStateFlow<Double>(0.0)

  init {
    runBlocking {
      val ghostControl = object : GhostControl(), PhysicsTickListener {
        override fun physicsTick(space: PhysicsSpace, tpf: Float) = runBlocking { totalRuntime += tpf }
        override fun prePhysicsTick(space: PhysicsSpace, tpf: Float) {}
      }
      val ghostNode = Node("physics_clock_ghost").apply { addControl(ghostControl) }

      withContext(engine.renderingDispatcher()) {
        engine.extractFrameworkNode().attachChild(ghostNode)
      }

      engine.extractPhysics().getPhysicsSpace().add(ghostControl)
      engine.extractCoroutineScope().launch(Dispatchers.Default) {
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