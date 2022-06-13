package io.matthewbradshaw.octavius.jmonkey

import io.matthewbradshaw.octavius.app.App

import io.matthewbradshaw.octavius.OctaviusScope
import io.matthewbradshaw.octavius.heartbeat.Ticker
import dagger.Provides
import dagger.Module
import com.jme3.app.VRAppState
import com.jme3.app.VREnvironment
import com.jme3.system.AppSettings
import com.jme3.app.VRConstants
import com.jme3.app.LostFocusBehavior
import io.matthewbradshaw.octavius.core.JMonkeyEngine
import io.matthewbradshaw.octavius.core.Paradigm
import io.matthewbradshaw.octavius.core.Game

@Module
class JMonkeyModule {

  @Provides
  @OctaviusScope
  fun provideJMonkey(game: Game, app: App) = when (game.paradigm()) {
    Paradigm.FLATWARE -> createJMonkeyFlatWare(game, app)
    Paradigm.VR -> createJMonkeyVr(game, app)
  }

  private fun createJMonkeyFlatWare(game: Game, app: App): JMonkeyEngine.FlatWare {
    val settings = AppSettings( /* loadDefaults= */ true).also {
      app.setSettings(it)
      app.setShowSettings(false)
    }

    return JMonkeyEngine.FlatWare(
      settings,
      app,
    )
  }

  private fun createJMonkeyVr(game: Game, app: App): JMonkeyEngine.Vr {
    val settings = AppSettings( /* loadDefaults= */ true).apply {
      put(VRConstants.SETTING_VRAPI, VRConstants.SETTING_VRAPI_OPENVR_LWJGL_VALUE)
      put(VRConstants.SETTING_ENABLE_MIRROR_WINDOW, true)
    }.also {
      app.setSettings(it)
      app.setShowSettings(false)
    }

    val environment = VREnvironment(settings).apply {
      initialize()
      if (!isInitialized()) throw IllegalStateException("VR environment did not initialize.")
    }

    val appState = VRAppState(settings, environment).apply {
      setMirrorWindowSize(DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX, DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX)
    }


    return JMonkeyEngine.Vr(
      settings,
      app,
      appState,
      environment,
    )
  }

  companion object {
    private const val DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX = 1024
    private const val DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX = 800
  }
}


