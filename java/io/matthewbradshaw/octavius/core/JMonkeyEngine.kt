package io.matthewbradshaw.octavius.core

import com.jme3.app.VRAppState
import com.jme3.app.VREnvironment
import com.jme3.app.SimpleApplication
import com.jme3.system.AppSettings

sealed interface JMonkeyEngine {

  val paradigm: Paradigm

  data class FlatWare(
    val settings: AppSettings,
    val application: SimpleApplication
  ) : JMonkeyEngine {
    override val paradigm = Paradigm.FLATWARE
  }

  class Vr(
    val settings: AppSettings,
    val application: SimpleApplication,
    val appState: VRAppState,
    val environment: VREnvironment
  ) : JMonkeyEngine {
    override val paradigm = Paradigm.VR
  }
}