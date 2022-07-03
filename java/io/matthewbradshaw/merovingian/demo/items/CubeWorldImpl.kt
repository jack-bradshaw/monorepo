package io.matthewbradshaw.merovingian.demo.items

import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import com.jme3.scene.shape.Box
import io.matthewbradshaw.klu.concurrency.once
import io.matthewbradshaw.merovingian.demo.materials.Materials
import io.matthewbradshaw.merovingian.engine.Engine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

class CubeWorldImpl @Inject internal constructor(
  private val cubeSwarmProvider: Provider<CubeSwarm>,
  private val engine: Engine,
  private val materials: Materials,
) : CubeWorld {

  private lateinit var coordinator: Node
  private lateinit var swarm: CubeSwarm
  private lateinit var floor: Spatial

  private val preparations = once {
    swarm = cubeSwarmProvider.get()

    floor = Geometry("cube_box", Box(2f, 0.2f, 2f)).apply {
      setMaterial(materials.getRandomly())
      setLocalTranslation(0f, 0f, 0f)
    }

    coordinator = Node("coordinator").apply {
      attachChild(swarm.representation())
      attachChild(floor)
    }
  }

  override suspend fun representation(): Spatial {
    preparations.runIfNeverRun()
    return coordinator
  }

  override suspend fun logic() {
    engine.extractCoroutineScope().launch {
      engine.extractCamera().setLocation(Vector3f(0f, 0f, 0f))
      swarm.logic()
    }
  }
}