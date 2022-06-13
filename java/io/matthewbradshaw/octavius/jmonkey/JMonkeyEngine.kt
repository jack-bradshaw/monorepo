package io.matthewbradshaw.octavius.jmonkey

import com.jme3.app.VRAppState
import com.jme3.app.VREnvironment
import com.jme3.app.SimpleApplication
import com.jme3.renderer.Camera
import com.jme3.system.AppSettings
import io.matthewbradshaw.octavius.heartbeat.Ticker
import io.matthewbradshaw.octavius.core.Paradigm
import com.jme3.asset.AssetManager

sealed interface JMonkeyEngine {

  val paradigm: Paradigm
  val settings: AppSettings
  val application: JMonkeyApp
  val camera: Camera
  val assetManager: AssetManager

  data class FlatWare(
    override val settings: AppSettings,
    override val application: JMonkeyApp,
    override val camera: Camera,
    override val assetManager: AssetManager
  ) : JMonkeyEngine {
    override val paradigm = Paradigm.FLATWARE
  }

  class Vr(
    override val settings: AppSettings,
    override val application: JMonkeyApp,
    override val camera: Camera,
    override val assetManager: AssetManager,
    val appState: VRAppState,
    val environment: VREnvironment
  ) : JMonkeyEngine {
    override val paradigm = Paradigm.VR
  }
}