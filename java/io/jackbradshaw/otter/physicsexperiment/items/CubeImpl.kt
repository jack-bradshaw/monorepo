package io.jackbradshaw.otter.physics.experiment.items

import com.jme3.bullet.collision.shapes.HullCollisionShape
import com.jme3.bullet.control.RigidBodyControl
import com.jme3.scene.Geometry
import com.jme3.scene.Mesh
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import com.jme3.scene.shape.Box
import io.jackbradshaw.otter.coroutines.renderingDispatcher
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.klu.flow.BinaryDelta
import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.otter.physics.experiment.materials.Materials
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import com.jme3.bullet.collision.PhysicsCollisionObject


class CubeImpl @Inject internal constructor(
  private val materials: Materials,
  private val engine: Engine,
) : Cube {

  private val size = 0.1f
  private lateinit var shape: Mesh
  private lateinit var coordinator: Node
  private lateinit var geometry: Spatial
  private lateinit var collider: RigidBodyControl

  private val colliderFlow = MutableSharedFlow<Pair<PhysicsCollisionObject, BinaryDelta>>(1)

  init {
    runBlocking {
      shape = Box(size, size, size)
      coordinator = Node("coordinator")
      collider = RigidBodyControl(HullCollisionShape(shape), 10f)
      geometry = Geometry("cube", shape).apply {
        coordinator.attachChild(this)
      }
    }
  }

  override val spatial = coordinator
  override fun exportedColliders() = colliderFlow as BinaryDeltaFlow<PhysicsCollisionObject>

  // Cube mass is defined as density * L^3. Let density be = 1 kg/m^3.
  private fun mass() = size * size * size

  private var isA = true

  override suspend fun setA() {
    withContext(engine.renderingDispatcher()) {
      isA = true
      geometry.setMaterial(materials.getRed())
    }
  }

  override suspend fun setB() {
    withContext(engine.renderingDispatcher()) {
      isA = false
      geometry.setMaterial(materials.getBlue())
      geometry.addControl(collider)
      colliderFlow.tryEmit(collider to BinaryDelta.INCLUDE)
    }
  }

  override suspend fun setCubeB(cube: Cube) {
    withContext(engine.renderingDispatcher()) {
      coordinator.attachChild(cube.spatial)
    }
  }

  override suspend fun doTestThing() {
    if (isA) setupTestForA() else setupTestForB()
  }

  private suspend fun setupTestForA() {
    // Experiment 1: Translation without collision
    /*var x = 0f
    engine.extractCoroutineScope().launch(engine.renderingDispatcher()) {
      while(true) {
        delay(10L)
        x += FastMath.HALF_PI / 60
        val rollX = Quaternion().apply { fromAngleAxis(x, unitY().toJme3()) }
        coordinator.setLocalRotation(rollX)
      }
    }*/

    // Experiment 2: Rotation without collision
    /*
    var x = 0f
    engine.extractCoroutineScope().launch(engine.renderingDispatcher()) {
      while(true) {
        delay(10L)
        x += FastMath.HALF_PI / 120
        val rollX = Quaternion().apply { fromAngleAxis(x, unitY().toJme3()) }
        coordinator.setLocalRotation(rollX)
      }
    }*/

    // Experiment 3: Scale without collision
    /*
    engine.extractCoroutineScope().launch(engine.renderingDispatcher()) {
        val scaleX = vector3(2, 1, 1).toJme3()
        coordinator.setLocalScale(scaleX)
    }*/
  }

  private suspend fun setupTestForB() {
    // Experiment 1: Translation without collision
    /*var x = 1f
    engine.extractCoroutineScope().launch(engine.renderingDispatcher()) {
      while(true) {
        delay(10L)
        x += 1f / 60
        val posX = vector3x(sin(x))
        println("" + posX)
        geometry.setLocalTranslation(posX.toJme3())
      }
    }*/

    // Experiment 2: Rotation without collision
    /*
    var x = 0f
    engine.extractCoroutineScope().launch(engine.renderingDispatcher()) {
      coordinator.setLocalTranslation(vector3(0.5f, 0, 0).toJme3())
      while(true) {
        delay(10L)
        x += FastMath.HALF_PI / 120
        val rollX = Quaternion().apply { fromAngleAxis(x, unitY().toJme3()) }
        coordinator.setLocalRotation(rollX)
      }
    }*/

    // Experiment 3: Scale without collision
    /*var x = 0f
    engine.extractCoroutineScope().launch(engine.renderingDispatcher()) {
      coordinator.setLocalTranslation(vector3(1f, 0, 0).toJme3())
      val scaleX = vector3(2, 1, 1).toJme3()
      coordinator.setLocalScale(scaleX)
    }*/
  }
}
