package io.jackbradshaw.otter.demo.items

import com.jme3.bullet.collision.PhysicsCollisionObject
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import io.jackbradshaw.otter.clock.Clock
import io.jackbradshaw.otter.clock.Rendering
import io.jackbradshaw.otter.coroutines.renderingDispatcher
import io.jackbradshaw.otter.demo.config.Constants
import io.jackbradshaw.otter.demo.materials.Materials
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.ottermodel.DeltaFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class CubeSwarmImpl
@Inject
internal constructor(
    private val cubeProvider: Provider<Cube>,
    private val materials: Materials,
    @Rendering private val clock: Clock,
    private val random: Random,
    private val engine: Engine
) : CubeSwarm {

  private lateinit var cubeMaterials: List<Material>
  private lateinit var timeOffsets: List<Int>
  private lateinit var root: Node
  private var physicsFlow: DeltaFlow<PhysicsCollisionObject> = flowOf()

  init {
    runBlocking {
      cubeMaterials = List(Constants.ITEM_CHANNELS) { materials.getRandomly() }
      timeOffsets = List(Constants.ITEM_CHANNELS) { (random.nextFloat() * MAX_TIME_OFFSET).toInt() }
      root = Node("root")

      withContext(engine.renderingDispatcher()) {
        for (i in 0 until Constants.SWARM_SIZE) {
          val cube = cubeProvider.get()
          root.attachChild(cube.spatial)
          cube.setRelativePosition(generateRandomPositionOnSphere())
          physicsFlow = merge(physicsFlow, cube.colliders())
        }
      }
    }
  }

  override val spatial = root
  override fun colliders() = physicsFlow

  init {
    engine.extractCoroutineScope().launch(engine.renderingDispatcher()) {
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

  private fun generateRandomPositionOnSphere(): Vector3f {
    val radius = random.nextInt(MAX_RADIUS) + MIN_RADIUS
    val u = random.nextFloat()
    val v = random.nextFloat()
    val theta = _2PI * u
    val phi = acos((2 * v) - 1)
    val x = (radius * cos(theta) * sin(phi)).toFloat()
    val y = (radius * sin(theta) * sin(phi)).toFloat()
    val z = (radius * cos(phi)).toFloat()
    return Vector3f(x, y, z)
  }

  companion object {
    private const val MAX_TIME_OFFSET = 10
    private const val MAX_RADIUS = 1000
    private const val MIN_RADIUS = 10
    private const val GREEN_CHANNEL_CONSTANT_OFFSET = 0.6F
    private const val GREEN_CHANNEL_AMPLITUDE_MODIFIER = 0.4F
    private const val _2PI = 2 * 3.14f
  }
}
