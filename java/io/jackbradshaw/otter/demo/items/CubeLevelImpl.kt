package io.jackbradshaw.otter.demo.items

import com.jme3.math.Vector3f
import com.jme3.scene.Geometry
import com.jme3.scene.Spatial
import com.jme3.scene.shape.Box
import io.jackbradshaw.otter.demo.materials.Materials
import io.jackbradshaw.otter.engine.core.EngineCore
<<<<<<< HEAD
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Provider
import io.jackbradshaw.otter.scene.item.SceneItemImpl
=======
import io.jackbradshaw.otter.scene.item.SceneItemImpl
import javax.inject.Inject
import javax.inject.Provider
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

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
<<<<<<< HEAD
          Geometry("cube_box", Box(2f, 0.2f, 2f)).apply {
            setMaterial(materials.getRandomly())
          }.also { addElement(it) }
=======
          Geometry("cube_box", Box(2f, 0.2f, 2f))
              .apply { setMaterial(materials.getRandomly()) }
              .also { addElement(it) }
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
    }
  }

  init {
    engineCore.extractCoroutineScope().launch {
      engineCore.extractDefaultInGameCamera().setLocation(Vector3f(0f, 0f, 0f))
    }
  }
}
