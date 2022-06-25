package io.matthewbradshaw.merovingian.testing

import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.math.Vector3f
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import io.matthewbradshaw.kotlinhelpers.once
import io.matthewbradshaw.merovingian.clock.Clock
import io.matthewbradshaw.merovingian.engine.EngineBound
import io.matthewbradshaw.merovingian.model.GameItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@TestingScope
@AutoFactory
class CubeSwarm(
  private val cubeCount: Int,
  @Provided private val cubeFactory: CubeFactory,
  @Provided private val materials: Materials,
  @Provided private val clock: Clock,
  @Provided private val random: Random,
  @Provided @EngineBound private val engineDispatcher: CoroutineDispatcher,
  @Provided @EngineBound private val engineScope: CoroutineScope
) : GameItem {

  private val logicScope = CoroutineScope(engineScope.coroutineContext)

  private lateinit var cubeMaterials: List<Material>
  private lateinit var timeOffsets: List<Int>
  private lateinit var swarm: Node

  private val preparations = once {
    cubeMaterials = List(INDEPENDENCE_COUNT) { materials.createUnshadedGreen() }

    timeOffsets = List(INDEPENDENCE_COUNT) { (random.nextFloat() * MAX_TIME_OFFSET).toInt() }

    swarm = Node("origin").apply {
      for (i in 0 until cubeCount) {
        val material = cubeMaterials[random.nextInt(INDEPENDENCE_COUNT - 1)]
        val cube = cubeFactory.create(material)
        attachChild(cube.representation())
        cube.representation().setLocalTranslation(generateRandomPositionOnSphere())
      }
    }
  }

  override suspend fun representation(): Spatial {
    preparations.runOnce()
    return swarm
  }

  override suspend fun logic() {
    coroutineScope {
      launch(engineDispatcher) {
        clock
          .totalSec()
          .onEach {
            for (i in 0 until INDEPENDENCE_COUNT) {
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
    private const val INDEPENDENCE_COUNT = 10
    private const val MAX_RADIUS = 1000
    private const val MIN_RADIUS = 10
    private const val GREEN_CHANNEL_CONSTANT_OFFSET = 0.6F
    private const val GREEN_CHANNEL_AMPLITUDE_MODIFIER = 0.4F
    private const val _2PI = 2 * 3.14f
  }
}