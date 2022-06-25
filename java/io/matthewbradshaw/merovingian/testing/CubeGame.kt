package io.matthewbradshaw.merovingian.testing

import io.matthewbradshaw.merovingian.model.GameItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.flow.flowOf
import com.jme3.math.Vector3f
import com.jme3.renderer.Camera
import com.google.auto.factory.Provided
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.google.auto.factory.AutoFactory
import com.jme3.scene.Spatial
import io.matthewbradshaw.merovingian.engine.EngineBound
import kotlinx.coroutines.CoroutineScope
import io.matthewbradshaw.kotlinhelpers.once

@TestingScope
@AutoFactory
class CubeGame(
  @Provided private val camera: Camera,
  @Provided private val cubeSwarmFactory: CubeSwarmFactory,
  @Provided @EngineBound private val engineScope: CoroutineScope,
  @Provided private val materials: Materials,
) : GameItem {

  //private lateinit var root: Node
  private lateinit var swarm: CubeSwarm
  //private lateinit var floor: Spatial

  private val preparations = once {
   // root = Node("root")

    swarm = cubeSwarmFactory.create(CUBE_COUNT)

    /*if (!this::floor.isInitialized) {
      floor = Geometry("cube_box", Box(5f, 0.2f, 5f)).apply {
        setMaterial(materials.createUnshadedGreen())
      }
    }*/
  }

  override suspend fun representation() : Spatial {
    preparations.runOnce()
    return swarm.representation()
  }

  override suspend fun logic() {
    engineScope.launch {
      camera.setLocation(Vector3f(0f, 0f, 0f))
      swarm.logic()
    }
  }

  companion object {
    private const val CUBE_COUNT = 10_000
  }
}