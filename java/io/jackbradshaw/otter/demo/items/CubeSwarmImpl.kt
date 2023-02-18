package io.jackbradshaw.otter.demo.items
<<<<<<< HEAD
import kotlinx.coroutines.flow.first
import io.jackbradshaw.otter.physics.model.placement
import io.jackbradshaw.otter.math.model.point
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
=======

import com.jme3.material.Material
import com.jme3.math.ColorRGBA
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
import io.jackbradshaw.otter.coroutines.renderingDispatcher
import io.jackbradshaw.otter.demo.config.Constants
import io.jackbradshaw.otter.demo.materials.Materials
import io.jackbradshaw.otter.engine.core.EngineCore
<<<<<<< HEAD
import io.jackbradshaw.otter.physics.model.Placement
import io.jackbradshaw.otter.timing.Clock
import io.jackbradshaw.otter.qualifiers.Rendering
=======
import io.jackbradshaw.otter.math.model.point
import io.jackbradshaw.otter.physics.model.Placement
import io.jackbradshaw.otter.physics.model.placement
import io.jackbradshaw.otter.qualifiers.Rendering
import io.jackbradshaw.otter.scene.item.SceneItemImpl
import io.jackbradshaw.otter.timing.Clock
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b
import javax.inject.Inject
import javax.inject.Provider
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
<<<<<<< HEAD
import io.jackbradshaw.otter.scene.item.SceneItemImpl
=======
>>>>>>> 780513c7d14aae85c67b233f1c2667ee1e78f25b

class CubeSwarmImpl
@Inject
internal constructor(
    private val cubeProvider: Provider<Cube>,
    private val materials: Materials,
    @Rendering private val clock: Clock,
    private val random: Random,
    private val engineCore: EngineCore
) : CubeSwarm, SceneItemImpl() {

  private var cubeMaterials: List<Material>
  private var timeOffsets: List<Int>

  init {
    runBlocking {
      println("cube swarm impl init")
      cubeMaterials = List(Constants.ITEM_CHANNELS) { materials.getRandomly() }
      timeOffsets = List(Constants.ITEM_CHANNELS) { (random.nextFloat() * MAX_TIME_OFFSET).toInt() }
      for (i in 0 until Constants.SWARM_SIZE) {
        addDescendant(cubeProvider.get(), generateRandomPositionOnSphere())
      }
    }
  }

  init {
    engineCore.extractCoroutineScope().launch(engineCore.renderingDispatcher()) {
      clock
          .totalSec()
          .onEach {
            for (i in 0 until Constants.ITEM_CHANNELS) {
              val time = it + timeOffsets[i]
              val green =
                  (GREEN_CHANNEL_CONSTANT_OFFSET + (GREEN_CHANNEL_AMPLITUDE_MODIFIER * sin(time)))
                      .toFloat()
              cubeMaterials[i].setColor("Color", ColorRGBA(0f, green, 0f, 1f))
            }
          }
          .collect()
    }
  }

  private fun generateRandomPositionOnSphere(): Placement {
    val radius = random.nextInt(MAX_RADIUS) + MIN_RADIUS
    val u = random.nextFloat()
    val v = random.nextFloat()
    val theta = _2PI * u
    val phi = acos((2 * v) - 1)
    val x = (radius * cos(theta) * sin(phi)).toFloat()
    val y = (radius * sin(theta) * sin(phi)).toFloat()
    val z = (radius * cos(phi)).toFloat()
    return placement(position = point(x, y, z))
  }

  companion object {
    private const val MAX_TIME_OFFSET = 10
    private const val MAX_RADIUS = 1000
    private const val MIN_RADIUS = 30
    private const val GREEN_CHANNEL_CONSTANT_OFFSET = 0.6F
    private const val GREEN_CHANNEL_AMPLITUDE_MODIFIER = 0.4F
    private const val _2PI = 2 * 3.14f
  }
}
