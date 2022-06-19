package io.matthewbradshaw.gmonkey.core.engine

import com.jme3.app.SimpleApplication
import io.matthewbradshaw.gmonkey.core.CoreScope
import dagger.Provides
import dagger.Module
import dagger.Binds
import com.jme3.app.VRAppState

import com.jme3.asset.AssetManager
import com.jme3.renderer.Camera

object EngineModules {
  @Module
  class Provisioning {
    @Provides
    @CoreScope
    fun provideCamera(engine: Engine): Camera = engine.extractCamera()

    @Provides
    @CoreScope
    fun provideAssetManager(engine: Engine): AssetManager = engine.extractAssetManager()

    @Provides
    @CoreScope
    fun provideApp(engine: Engine): SimpleApplication = engine.extractApp()

    @Provides
    @CoreScope
    fun provideVr(engine: Engine): VRAppState =
      engine.extractVr() ?: throw IllegalStateException("Engine is not configured for VR.")
  }

  @Module
  interface Binding {
    @Binds
    fun bindEngine(impl: EngineImpl): Engine
  }
}