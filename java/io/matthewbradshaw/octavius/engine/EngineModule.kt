package io.matthewbradshaw.octavius.engine

import io.matthewbradshaw.octavius.OctaviusScope
import dagger.Provides
import dagger.Module
import com.jme3.app.VRAppState
import com.jme3.app.VREnvironment
import com.jme3.system.AppSettings
import com.jme3.app.VRConstants

import com.jme3.app.state.AppState
import io.matthewbradshaw.octavius.engine.Paradigm
import io.matthewbradshaw.octavius.ticker.Ticker

@Module
class EngineModule {

  @Provides
  @OctaviusScope
  fun provideJMonkey(paradigm: Paradigm, ticker: Ticker) = when (paradigm) {
    Paradigm.FLATWARE -> createFlatWareEngine(ticker)
    Paradigm.VR -> createVrEngine(ticker)
  }

  private fun createFlatWareEngine(ticker: Ticker): Engine {
    val settings = AppSettings( /* loadDefaults= */ true)
    return EngineImpl(ticker, listOf()).apply {
      root().setSettings(settings)
      root().setShowSettings(false)
    }
  }

  private fun createVrEngine(ticker: Ticker): Engine {
    val settings = AppSettings( /* loadDefaults= */ true).apply {
      put(VRConstants.SETTING_VRAPI, VRConstants.SETTING_VRAPI_OPENVR_LWJGL_VALUE)
      put(VRConstants.SETTING_ENABLE_MIRROR_WINDOW, true)
    }

    val environment = VREnvironment(settings).apply {
      initialize()
      if (!isInitialized()) throw IllegalStateException("VR environment did not initialize.")
    }

    val vrAppState = VRAppState(settings, environment).apply {
      setMirrorWindowSize(DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX, DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX)
    }

    return EngineImpl(ticker, listOf(vrAppState)).apply {
      root().setSettings(settings)
      root().setShowSettings(false)
    }
  }

  companion object {
    private const val DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX = 1024
    private const val DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX = 800
  }
}


