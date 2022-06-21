package io.matthewbradshaw.merovingian

import com.jme3.app.SimpleApplication
import com.jme3.app.VRAppState
import com.jme3.asset.AssetManager
import dagger.Component
import dagger.BindsInstance
import io.matthewbradshaw.merovingian.engine.EngineModules
import io.matthewbradshaw.merovingian.config.Paradigm
import io.matthewbradshaw.merovingian.ticker.Ticker
import io.matthewbradshaw.merovingian.ticker.TickerModule
import com.jme3.renderer.Camera
import kotlinx.coroutines.CoroutineDispatcher
import com.jme3.scene.Node
import io.matthewbradshaw.merovingian.engine.RootNode
import io.matthewbradshaw.merovingian.engine.MainDispatcher
import io.matthewbradshaw.merovingian.host.HostFactory

@MerovingianScope
@Component(
  modules = [
    EngineModules.Binding::class,
    EngineModules.Provisioning::class,
    TickerModule::class
  ]
)
interface MerovingianComponent {

  fun ticker(): Ticker
  fun camera(): Camera
  fun assetManager(): AssetManager
  fun app(): SimpleApplication
  fun vr(): VRAppState

  @MainDispatcher
  fun mainDispatcher(): CoroutineDispatcher

  @RootNode
  fun rootNode(): Node

  fun hostFactory(): HostFactory

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setParadigm(paradigm: Paradigm): Builder
    fun build(): MerovingianComponent
  }
}

fun merovingian(paradigm: Paradigm): MerovingianComponent = DaggerMerovingianComponent.builder().setParadigm(paradigm).build()