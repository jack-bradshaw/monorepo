package io.matthewbradshaw.gmonkey.engine

import com.jme3.app.SimpleApplication
import io.matthewbradshaw.gmonkey.GMonkeyScope
import dagger.Provides
import dagger.Binds
import dagger.Module
import com.jme3.app.VRAppState
import com.jme3.scene.Node
import kotlinx.coroutines.CoroutineDispatcher

import com.jme3.asset.AssetManager
import com.jme3.renderer.Camera
import io.matthewbradshaw.gmonkey.coroutines.dispatcher

object EngineModules {

  @Module
  class Provisioning {
    @Provides
    @GMonkeyScope
    fun provideCamera(engine: Engine): Camera = engine.extractCamera()

    @Provides
    @GMonkeyScope
    fun provideAssetManager(engine: Engine): AssetManager = engine.extractAssetManager()

    @Provides
    @GMonkeyScope
    fun provideApp(engine: Engine): SimpleApplication = engine.extractApp()

    @Provides
    @GMonkeyScope
    fun provideVr(engine: Engine): VRAppState =
      engine.extractVr() ?: throw IllegalStateException("Engine is not configured for VR.")

    @Provides
    @GMonkeyScope
    @MainDispatcher
    fun provideDispatcher(engine: Engine): CoroutineDispatcher = engine.extractApp().dispatcher()

    @Provides
    @GMonkeyScope
    @RootNode
    fun provideRootNode(engine: Engine): Node = engine.extractRootNode()
  }

  @Module
  interface Binding {
    @Binds
    fun bindEngine(impl: EngineImpl): Engine
  }
}