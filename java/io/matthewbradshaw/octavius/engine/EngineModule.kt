package io.matthewbradshaw.octavius.engine

import io.matthewbradshaw.octavius.OctaviusScope
import dagger.Provides
import dagger.Module
import com.jme3.app.VRAppState
import com.jme3.app.VREnvironment
import com.jme3.system.AppSettings
import com.jme3.app.VRConstants
import io.matthewbradshaw.octavius.core.Paradigm
import io.matthewbradshaw.octavius.driver.Driver

@Module
class EngineModule {

  @Provides
  @OctaviusScope
  fun provideJMonkey(paradigm: Paradigm, driver: Driver) = when (paradigm) {
    Paradigm.FLATWARE -> createFlatWareEngine(driver)
    Paradigm.VR -> createVrEngine(driver)
  }

  private fun createFlatWareEngine(driver: Driver): Engine.FlatWare {
    val settings = AppSettings( /* loadDefaults= */ true).also {
      driver.extractApplication().setSettings(it)
      driver.extractApplication().setShowSettings(false)
    }

    return Engine.FlatWare(
      settings,
      driver,
      driver.extractCamera(),
      driver.extractAssetManager()
    )
  }

  private fun createVrEngine(driver: Driver): Engine.Vr {
    val settings = AppSettings( /* loadDefaults= */ true).apply {
      put(VRConstants.SETTING_VRAPI, VRConstants.SETTING_VRAPI_OPENVR_LWJGL_VALUE)
      put(VRConstants.SETTING_ENABLE_MIRROR_WINDOW, true)
    }.also {
      driver.extractApplication().setSettings(it)
      driver.extractApplication().setShowSettings(false)
    }

    val environment = VREnvironment(settings).apply {
      initialize()
      if (!isInitialized()) throw IllegalStateException("VR environment did not initialize.")
    }

    val appState = VRAppState(settings, environment).apply {
      setMirrorWindowSize(DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX, DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX)
    }

    return Engine.Vr(
      settings,
      driver,
      driver.extractCamera(),
      driver.extractAssetManager(),
      appState,
      environment
    )
  }

  companion object {
    private const val DEFAULT_VR_MIRROR_WINDOW_WIDTH_PX = 1024
    private const val DEFAULT_VR_MIRROR_WINDOW_HEIGHT_PX = 800
  }
}


