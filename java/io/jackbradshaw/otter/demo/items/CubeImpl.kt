package io.jackbradshaw.otter.demo.items

import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial
import com.jme3.scene.shape.Box
import io.jackbradshaw.otter.coroutines.physicsDispatcher
import io.jackbradshaw.otter.demo.materials.Materials
import io.jackbradshaw.otter.engine.core.EngineCore
import io.jackbradshaw.otter.scene.item.SceneItemImpl
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CubeImpl
@Inject
internal constructor(
    private val materials: Materials,
    private val engineCore: EngineCore,
    private val random: Random,
) : Cube, SceneItemImpl() {

  private val size = random.nextFloat()
  private lateinit var shape: Mesh
  private lateinit var geometry: Spatial

  // private lateinit var collider: RigidBodyControl

  init {
    runBlocking {
      println("jackbradshaw cube init")
      shape = Box(size, size, size)
      // collider = RigidBodyControl(HullCollisionShape(shape), mass()).also { add(it) }
      geometry =
          Geometry("cube", shape)
              .apply { setMaterial(materials.getRandomly()) }
              .also { addElement(it) }
    }
  }

  // Cube mass is defined as density * L^3. Let density be = 1 kg/m^3.
  private fun mass() = size * size * size

  init {
    engineCore.extractCoroutineScope().launch(engineCore.physicsDispatcher()) {
      while (true) {
        delay(1000)
        val magnitude =
            Vector3f(
                (100 * (random.nextFloat() - 0.5)).toFloat(),
                (100 * (random.nextFloat() - 0.5)).toFloat(),
                (100 * (random.nextFloat() - 0.5)).toFloat())
        val location = Vector3f(size / 2, size / 2, size / 2)
        // collider.applyImpulse(magnitude, location)
      }
    }
  }
}
