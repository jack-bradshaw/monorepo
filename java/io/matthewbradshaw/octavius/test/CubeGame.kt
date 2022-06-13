package java.io.matthewbradshaw.octavius.test

import io.matthewbradshaw.octavius.core.Game
import io.matthewbradshaw.octavius.core.Paradigm
import io.matthewbradshaw.octavius.ui.Frameable
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

class CubeGame(ticker: Ticker) : Game {

  private val random = Random(0L)


  private val boxMaterialOffsets = List<Int>(independenceFactor) { random.nextInt(10) }
  private val boxMaterialRateMultiplier = List<Float>(independenceFactor) { random.nextFloat() }
  private val boxMaterials by lazy {
    List<Material>(independenceFactor) {
      Material(
        assetManager,
        "Common/MatDefs/Misc/Unshaded.j3md"
      ).apply {
        setColor("Color", ColorRGBA.Blue)
      }
    }
  }

  override fun ui(): Flow<Frameable> {
    TODO()
  }

  override fun paradigm(): Paradigm {
    TODO("Not yet implemented")
  }

  companion object {
    private const val INDEPENDENCE_FACTOR = 10
    private const val BOX_COUNT = 10000
  }
}







override fun simpleInitApp() {
  Geometry("Box 1", Box(5f, 5f, 5f)).apply {
    setMaterial(boxMaterials[0])
    setLocalTranslation(0f, 0f, 0f)
  }.let { rootNode.attachChild(it) }

  for (i in 1..boxCount) {
    Geometry("Box I", Box(0.5f, 0.5f, 0.5f)).apply {
      setMaterial(boxMaterials[random.nextInt(9)])
    }.let {
      rootNode.attachChild(it)
      it.setLocalTranslation(randomPosition())
    }
  }
  cam.setLocation(Vector3f(2.5f, 2.5f, 2.5f))
}

private val maximumRadius = 1000
private val _2PI: Float = 2 * 3.14f

private fun randomPosition(): Vector3f {
  val radius = random.nextInt(maximumRadius) + 10
  val u = random.nextFloat()
  val v = random.nextFloat()
  val theta = _2PI * u
  val phi = acos((2 * v) - 1)
  val x = radius * cos(theta) * sin(phi)
  val y = radius * sin(theta) * sin(phi)
  val z = radius * cos(phi)
  return Vector3f(x, y, z)
}

private var time = 0f
override fun simpleUpdate(tpf: Float) {
  time += tpf
  for (i in 0 until independenceFactor) {
    val green = 0.6f + (0.4f * sin((boxMaterialRateMultiplier[i] * time) + boxMaterialOffsets[i]))
    boxMaterials[i].setColor("Color", ColorRGBA(0f, green, 0f, 1f))
  }
}

companion object {
  @JvmStatic
  fun main() {
    val settings = AppSettings( /* loadDefaults= */ true)
    settings.put(VRConstants.SETTING_VRAPI, VRConstants.SETTING_VRAPI_OPENVR_LWJGL_VALUE)
    settings.put(VRConstants.SETTING_ENABLE_MIRROR_WINDOW, true)

    val environment = VREnvironment(settings)
    environment.initialize()

    if (environment.isInitialized()) {
      val appState = VRAppState(settings, environment)
      appState.setMirrorWindowSize(1024, 800)
      val game = Game(appState, environment)
      game.setLostFocusBehavior(LostFocusBehavior.Disabled)
      game.setSettings(settings)
      game.setShowSettings(false)
      game.start()
    } else {
      throw IllegalStateException("Game initialization failed.")
    }
  }
}