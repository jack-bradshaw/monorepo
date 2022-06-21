package io.matthewbradshaw.merovingian.engine

import com.jme3.app.SimpleApplication
import io.matthewbradshaw.merovingian.MerovingianScope
import dagger.Provides
import dagger.Binds
import com.jme3.app.VRAppState
import com.jme3.scene.Node
import kotlinx.coroutines.CoroutineDispatcher
import dagger.Module
import com.jme3.asset.AssetManager
import com.jme3.renderer.Camera
import io.matthewbradshaw.merovingian.coroutines.dispatcher

object EngineModules {

  @Module
  class Provisioning {
    @Provides
    @MerovingianScope
    fun provideCamera(engine: Engine): Camera = engine.extractCamera()

    @Provides
    @MerovingianScope
    fun provideAssetManager(engine: Engine): AssetManager = engine.extractAssetManager()

    @Provides
    @MerovingianScope
    fun provideApp(engine: Engine): SimpleApplication = engine.extractApp()

    @Provides
    @MerovingianScope
    fun provideVr(engine: Engine): VRAppState =
      engine.extractVr() ?: throw IllegalStateException("Engine is not configured for VR.")

    @Provides
    @MerovingianScope
    @MainDispatcher
    fun provideDispatcher(engine: Engine): CoroutineDispatcher = engine.extractApp().dispatcher()

    @Provides
    @MerovingianScope
    @RootNode
    fun provideRootNode(engine: Engine): Node = engine.extractRootNode()
  }

  @Module
  interface Binding {
    @Binds
    fun bindEngine(impl: EngineImpl): Engine
  }
}