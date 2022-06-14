package io.matthewbradshaw.octavius.engine

import com.jme3.app.VRAppState
import com.jme3.app.VREnvironment
import com.jme3.renderer.Camera
import com.jme3.system.AppSettings
import io.matthewbradshaw.octavius.core.Paradigm
import com.jme3.asset.AssetManager
import io.matthewbradshaw.octavius.driver.Driver

sealed interface Engine {

  val paradigm: Paradigm
  val settings: AppSettings
  val driver: Driver
  val camera: Camera
  val assetManager: AssetManager

  data class FlatWare(
    override val settings: AppSettings,
    override val driver: Driver,
    override val camera: Camera,
    override val assetManager: AssetManager
  ) : Engine {
    override val paradigm = Paradigm.FLATWARE
  }

  class Vr(
    override val settings: AppSettings,
    override val driver: Driver,
    override val camera: Camera,
    override val assetManager: AssetManager,
    val appState: VRAppState,
    val environment: VREnvironment
  ) : Engine {
    override val paradigm = Paradigm.VR
  }
}