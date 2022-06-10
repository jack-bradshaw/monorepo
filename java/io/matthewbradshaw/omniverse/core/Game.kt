package io.matthewbradshaw.omniverse.core

import com.jme3.app.SimpleApplication
import com.jme3.material.Material
import com.jme3.math.ColorRGBA
import com.jme3.scene.Geometry
import com.jme3.scene.shape.Box
import com.jme3.system.AppSettings
import com.jme3.app.state.AppState
import com.jme3.app.VRAppState
import com.jme3.app.VRConstants
import com.jme3.app.VREnvironment
import com.jme3.app.LostFocusBehavior
import com.jme3.math.Vector3f

class Game(
  private val appState: VRAppState,
  private val environment: VREnvironment,
) : SimpleApplication(appState) {
  override fun simpleInitApp() {
    val box = Box(1f, 1f, 1f)
    val geometry = Geometry("Box", box)
    val material = Material(
      assetManager,
      "Common/MatDefs/Misc/Unshaded.j3md"
    )

    material.setColor("Color", ColorRGBA.Blue)
    geometry.setMaterial(material)

    rootNode.attachChild(geometry)

    cam.setLocation(Vector3f(1f, 1f, 1f))
  }

  override fun simpleUpdate(tpf: Float) {
    // TODO
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
}
