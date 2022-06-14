package java.io.matthewbradshaw.octavius.test

import io.matthewbradshaw.octavius.core.Game
import io.matthewbradshaw.octavius.core.Paradigm
import io.matthewbradshaw.octavius.ticker.Ticker
import io.matthewbradshaw.octavius.Octavius
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.MainScope
import kotlin.math.acos
import kotlin.math.cos
import com.jme3.scene.Node
import com.jme3.scene.Spatial
import kotlin.math.sin
import kotlin.random.Random
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.math.Vector3f

class CubeGame(private val octavius: Octavius) : Game {

  private val coroutineScope = MainScope()

  init {
    coroutineScope.launch {
      val ticker = octavius.ticker()
      ticker.pulse().collect {
        for (i in 0 until INDEPENDENCE_FACTOR) {
          val green = 0.6f + (0.4f * sin((boxMaterialRateMultiplier[i] * ticker.netTimeSec()) + boxMaterialOffsets[i]))
          boxMaterials[i].setColor("Color", ColorRGBA(0f, green, 0f, 1f))
        }
      }
    }
  }

  private val random = Random(0L)

  private val boxMaterialOffsets = List<Int>(INDEPENDENCE_FACTOR) { random.nextInt(10) }
  private val boxMaterialRateMultiplier = List<Float>(INDEPENDENCE_FACTOR) { random.nextFloat() }
  private val boxMaterials by lazy {
    List<Material>(INDEPENDENCE_FACTOR) {
      Material(
        octavius.engine().assetManager,
        "Common/MatDefs/Misc/Unshaded.j3md"
      ).apply {
        setColor("Color", ColorRGBA.Blue)
      }
    }
  }

  override fun ui(): Flow<Spatial> = flowOf(createUi())

  private fun createUi(): Spatial {
    val root = Node("game_root")

    Geometry("Box 1", Box(5f, 5f, 5f)).apply {
      setMaterial(boxMaterials[0])
      setLocalTranslation(0f, 0f, 0f)
    }.let { root.attachChild(it) }

    for (i in 1..BOX_COUNT) {
      Geometry("Box I", Box(0.5f, 0.5f, 0.5f)).apply {
        setMaterial(boxMaterials[random.nextInt(9)])
      }.let {
        root.attachChild(it)
        it.setLocalTranslation(randomPositionOnSphere())
      }
    }
    octavius.engine().camera.setLocation(Vector3f(2.5f, 2.5f, 2.5f))

    return root
  }

  private fun randomPositionOnSphere(): Vector3f {
    val radius = random.nextInt(MAX_RADIUS) + MIN_RADIUS
    val u = random.nextFloat()
    val v = random.nextFloat()
    val theta = _2PI * u
    val phi = acos((2 * v) - 1)
    val x = radius * cos(theta) * sin(phi)
    val y = radius * sin(theta) * sin(phi)
    val z = radius * cos(phi)
    return Vector3f(x, y, z)
  }

  companion object {
    private const val INDEPENDENCE_FACTOR = 10
    private const val BOX_COUNT = 10000
    private const val MAX_RADIUS = 1000
    private const val MIN_RADIUS = 10
    private const val _2PI = 2 * 3.14f
  }
}