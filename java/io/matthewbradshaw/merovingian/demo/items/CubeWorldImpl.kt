package io.matthewbradshaw.merovingian.demo.items

import com.jme3.math.Vector3f
import com.jme3.scene.Spatial
import io.matthewbradshaw.klu.concurrency.once
import io.matthewbradshaw.merovingian.demo.DemoScope
import io.matthewbradshaw.merovingian.demo.materials.Materials
import io.matthewbradshaw.merovingian.engine.Engine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@DemoScope
class CubeWorldImpl @Inject internal constructor(
  private val cubeSwarmProvider: Provider<CubeSwarm>,
  private val engine: Engine,
  private val materials: Materials,
) : CubeWorld {

  //private lateinit var root: Node
  private lateinit var swarm: CubeSwarm
  //private lateinit var floor: Spatial

  private val preparations = once {
    // root = Node("root")

    swarm = cubeSwarmProvider.get()

    /*if (!this::floor.isInitialized) {
      floor = Geometry("cube_box", Box(5f, 0.2f, 5f)).apply {
        setMaterial(materials.createUnshadedGreen())
      }
    }*/
  }

  override suspend fun representation(): Spatial {
    preparations.runIfNeverRun()
    return swarm.representation()
  }

  override suspend fun logic() {
    engine.extractCoroutineScope().launch {
      engine.extractCamera().setLocation(Vector3f(0f, 0f, 0f))
      swarm.logic()
    }
  }

  companion object {
    private const val CUBE_COUNT = 10_000
  }
}