package io.jackbradshaw.otter.demo.items

import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Spatial
import com.jme3.scene.shape.Box
import io.jackbradshaw.otter.demo.materials.Materials
import io.jackbradshaw.otter.engine.core.EngineCore
import io.jackbradshaw.otter.scene.item.SceneItemImpl
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CubeLevelImpl
@Inject
internal constructor(
    private val cubeSwarmProvider: Provider<CubeSwarm>,
    private val engineCore: EngineCore,
    private val materials: Materials,
) : CubeLevel, SceneItemImpl() {

  init {
    println("cube level impl init")
    engineCore.extractPhysics().getPhysicsSpace().setGravity(Vector3f(0f, 0f, 0f))
  }

  private lateinit var swarm: CubeSwarm
  private lateinit var floor: Spatial

  init {
    runBlocking {
      swarm = cubeSwarmProvider.get().also { addDescendant(it) }

      floor =
          Geometry("cube_box", Box(2f, 0.2f, 2f))
              .apply { setMaterial(materials.getRandomly()) }
              .also { addElement(it) }
    }
  }

  init {
    engineCore.extractCoroutineScope().launch {
      engineCore.extractDefaultInGameCamera().setLocation(Vector3f(0f, 0f, 0f))
    }
  }
}
