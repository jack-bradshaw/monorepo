package io.matthewbradshaw.merovingian.demo.items

import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import io.matthewbradshaw.klu.concurrency.once
import io.matthewbradshaw.merovingian.clock.Clock
import io.matthewbradshaw.merovingian.demo.DemoScope
import io.matthewbradshaw.merovingian.demo.config.Config
import io.matthewbradshaw.merovingian.demo.materials.Materials
import io.matthewbradshaw.merovingian.engine.Engine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Provider
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@DemoScope
class CubeSwarmImpl @Inject internal constructor(
  private val cubeProvider: Provider<Cube>,
  private val materials: Materials,
  private val clock: Clock,
  private val random: Random,
  private val engine: Engine
) : CubeSwarm {

  private val logicScope = CoroutineScope(engine.extractCoroutineScope().coroutineContext)

  private lateinit var cubeMaterials: List<Material>
  private lateinit var timeOffsets: List<Int>
  private lateinit var origin: Node

  private val preparations = once {
    cubeMaterials = List(Config.ITEM_CHANNELS) { materials.getRandomly() }
    timeOffsets = List(Config.ITEM_CHANNELS) { (random.nextFloat() * MAX_TIME_OFFSET).toInt() }
    origin = Node("origin")
  }

  override suspend fun representation(): Spatial {
    preparations.runIfNeverRun()
    return origin
  }

  override suspend fun logic() {
    coroutineScope {
      launch(engine.extractCoroutineDispatcher()) {
        clock
          .totalSec()
          .onEach {
            for (i in 0 until Config.ITEM_CHANNELS) {
              val time = it + timeOffsets[i]
              val green =
                (GREEN_CHANNEL_CONSTANT_OFFSET + (GREEN_CHANNEL_AMPLITUDE_MODIFIER * sin(time))).toFloat()
              cubeMaterials[i].setColor("Color", ColorRGBA(0f, green, 0f, 1f))
            }
          }
          .collect()
      }
    }
  }

  override suspend fun setCubeCount(count: Int) {
    origin.detachAllChildren()
    withContext(engine.extractCoroutineDispatcher()) {
      for (i in 0 until count) {
        val material = cubeMaterials[random.nextInt(Config.ITEM_CHANNELS - 1)]
        val cube = cubeProvider.get()
        origin.attachChild(cube.representation())
        cube.representation().setLocalTranslation(generateRandomPositionOnSphere())
      }
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