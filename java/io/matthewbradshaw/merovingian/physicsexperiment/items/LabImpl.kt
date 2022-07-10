package io.matthewbradshaw.merovingian.physicsexperiment.items

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import com.jme3.util.SkyFactory
import io.matthewbradshaw.merovingian.coroutines.physicsDispatcher
import io.matthewbradshaw.merovingian.coroutines.renderingDispatcher
import io.matthewbradshaw.merovingian.engine.Engine
import io.matthewbradshaw.klu.flow.BinaryDeltaFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.flow.merge

class LabImpl @Inject internal constructor(
  private val engine: Engine,
  private val cubeProvider: Provider<Cube>,
  private val originProvider: Provider<Origin>,
) : Lab {

  init {
    engine.extractPhysics().getPhysicsSpace().setGravity(Vector3f(0f, 0f, 0f))
  }

  private lateinit var coordinator: Node
  private lateinit var cubeA: Cube
  private lateinit var cubeB: Cube

  init {
    runBlocking {
      withContext(engine.renderingDispatcher()) {
        cubeA = cubeProvider.get().apply { setA() }
        cubeB = cubeProvider.get().apply { setB() }
        cubeA.setCubeB(cubeB)
        coordinator = Node("coordinator").apply {
          attachChild(originProvider.get().spatial)
          attachChild(cubeA.spatial)
        }
        cubeA.doTestThing()
        cubeB.doTestThing()
      }
    }
  }

  override val spatial = coordinator
  override fun exportedColliders() = merge(cubeA.exportedColliders(), cubeB.exportedColliders())

  init {
    engine.extractCoroutineScope().launch {
      engine.extractCamera().setLocation(Vector3f(0f, 0f, 0f))
      engine.extractPhysics().setDebugEnabled(true)
      cubeA.doTestThing()
      cubeB.doTestThing()
    }
  }
}