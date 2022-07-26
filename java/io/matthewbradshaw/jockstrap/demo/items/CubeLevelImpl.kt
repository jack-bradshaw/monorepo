package io.matthewbradshaw.jockstrap.demo.items

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import com.jme3.scene.shape.Box
import io.matthewbradshaw.jockstrap.demo.materials.Materials
import io.matthewbradshaw.jockstrap.engine.Engine
import io.matthewbradshaw.jockstrapmodel.DeltaFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Provider

class CubeLevelImpl @Inject internal constructor(
  private val cubeSwarmProvider: Provider<CubeSwarm>,
  private val engine: Engine,
  private val materials: Materials,
) : CubeLevel {

  init {
    engine.extractPhysics().getPhysicsSpace().setGravity(Vector3f(0f, 0f, 0f))
  }

  private lateinit var coordinator: Node
  private lateinit var swarm: CubeSwarm
  private lateinit var floor: Spatial
  private var physicsFlow: DeltaFlow<PhysicsCollisionObject> = flowOf()

  init {
    runBlocking {
      swarm = cubeSwarmProvider.get()

      floor = Geometry("cube_box", Box(2f, 0.2f, 2f)).apply {
        setMaterial(materials.getRandomly())
        setLocalTranslation(0f, 0f, 0f)
      }

      coordinator = Node("coordinator").apply {
        attachChild(swarm.spatial)
        attachChild(floor)
      }

      physicsFlow = merge(physicsFlow, swarm.colliders())
    }
  }

  override val spatial = coordinator
  override fun colliders() = physicsFlow

  init {
    engine.extractCoroutineScope().launch {
      engine.extractCamera().setLocation(Vector3f(0f, 0f, 0f))
    }
  }
}