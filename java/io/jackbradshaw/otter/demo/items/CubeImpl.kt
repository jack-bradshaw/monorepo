package io.jackbradshaw.otter.demo.items

import com.jme3.bullet.collision.shapes.HullCollisionShape
import com.jme3.bullet.control.RigidBodyControl
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Spatial
import com.jme3.scene.shape.Box
import io.jackbradshaw.otter.coroutines.physicsDispatcher
import io.jackbradshaw.otter.demo.materials.Materials
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.ottermodel.Delta
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import kotlin.random.Random

class CubeImpl
@Inject
internal constructor(
    private val materials: Materials,
    private val engine: Engine,
    private val random: Random,
) : Cube {

  private val size = random.nextFloat()
  private lateinit var shape: Mesh
  private lateinit var geometry: Spatial
  private lateinit var collider: RigidBodyControl

  init {
    runBlocking {
      shape = Box(size, size, size)
      collider = RigidBodyControl(HullCollisionShape(shape), mass())
      geometry =
          Geometry("cube", shape).apply {
            setMaterial(materials.getRandomly())
            addControl(collider)
          }
    }
  }

  override val spatial = geometry
  override fun colliders() = flowOf(collider to Delta.INCLUDE)

  override suspend fun setRelativePosition(position: Vector3f) {
    val current = collider.getPhysicsLocation()
    collider.setPhysicsLocation(current.add(position))
  }

  // Cube mass is defined as density * L^3. Let density be = 1 kg/m^3.
  private fun mass() = size * size * size

  init {
    engine.extractCoroutineScope().launch(engine.physicsDispatcher()) {
      while (true) {
        delay(1000)
        val magnitude =
            Vector3f(
                (100 * (random.nextFloat() - 0.5)).toFloat(),
                (100 * (random.nextFloat() - 0.5)).toFloat(),
                (100 * (random.nextFloat() - 0.5)).toFloat())
        val location = Vector3f(size / 2, size / 2, size / 2)
        collider.applyImpulse(magnitude, location)
      }
    }
  }
}
