package io.jackbradshaw.otter.physics.experiment.items

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.scene.Geometry
import com.jme3.scene.Node
import com.jme3.scene.shape.Box
import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.otter.physics.experiment.materials.Materials
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class OriginImpl
@Inject
constructor(
    private val materials: Materials,
) : Origin {

  lateinit var coordinator: Node

  init {
    runBlocking {
      val x =
          Geometry("cube", Box(AXIS_LONG_DIMENSION, AXIS_SHORT_DIMENSION, AXIS_SHORT_DIMENSION))
              .apply { setMaterial(materials.getRed()) }
      val y =
          Geometry("cube", Box(AXIS_SHORT_DIMENSION, AXIS_LONG_DIMENSION, AXIS_SHORT_DIMENSION))
              .apply { setMaterial(materials.getBlue()) }
      val z =
          Geometry("cube", Box(AXIS_SHORT_DIMENSION, AXIS_SHORT_DIMENSION, AXIS_LONG_DIMENSION))
              .apply { setMaterial(materials.getGreen()) }
      coordinator =
          Node("coordinator").apply {
            attachChild(x)
            attachChild(y)
            attachChild(z)
          }
    }
  }

  override val spatial = coordinator
  override fun exportedColliders(): BinaryDeltaFlow<PhysicsCollisionObject> = flowOf()

  companion object {
    const val AXIS_LONG_DIMENSION = 10f
    const val AXIS_SHORT_DIMENSION = 0.005f
  }
}
