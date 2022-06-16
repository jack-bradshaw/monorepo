package io.matthewbradshaw.octavius.engine

import io.matthewbradshaw.octavius.OctaviusScope
import dagger.Provides
import dagger.Module
import com.jme3.app.VRAppState
import com.jme3.app.VREnvironment
import com.jme3.system.AppSettings
import com.jme3.app.VRConstants

import com.jme3.app.state.AppState
import io.matthewbradshaw.octavius.core.Paradigm
import io.matthewbradshaw.octavius.ticker.Ticker
import io.matthewbradshaw.octavius.ignition.Ignition

@Module
class EngineModule {

  @Provides
  @OctaviusScope
  fun provideJMonkey(paradigm: Paradigm, ticker: Ticker, ignition: Ignition) = when (paradigm) {
    Paradigm.FLATWARE -> createFlatWareEngine(ticker, ignition)
    Paradigm.VR -> createVrEngine(ticker, ignition)
  }

  private fun createFlatWareEngine(ticker: Ticker, ignition: Ignition): Engine.FlatWare {
    val settings = AppSettings( /* loadDefaults= */ true)
    val driver = EngineImpl(ticker, ignition, listOf<AppState>()).apply {
      extractApplication().setSettings(settings)
      extractApplication().setShowSettings(false)
    }

    return Engine.FlatWare(
      settings,
      driver
    )
  }

  private fun createVrEngine(ticker: Ticker, ignition: Ignition): Engine.Vr {
    println("create vr engine")

    val settings = AppSettings( /* loadDefaults= */ true).apply {
      put(VRConstants.SETTING_VRAPI, VRConstants.SETTING_VRAPI_OPENVR_LWJGL_VALUE)
      put(VRConstants.SETTING_ENABLE_MIRROR_WINDOW, true)
    }

    val environment = VREnvironment(settings).apply {
      initialize()
      if (!isInitialized()) throw IllegalStateException("VR environment did not initialize.")
    }

    val appState = VRAppState(settings, environment).apply {
      setMirrorWindowSize(DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX, DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX)
    }

    val driver = EngineImpl(ticker, ignition, listOf(appState)).apply {
      extractApplication().setSettings(settings)
      extractApplication().setShowSettings(false)
    }

    return Engine.Vr(
      settings,
      driver,
      appState,
      environment
    )
  }

  companion object {
    private const val DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX = 1024
    private const val DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX = 800
  }
}


