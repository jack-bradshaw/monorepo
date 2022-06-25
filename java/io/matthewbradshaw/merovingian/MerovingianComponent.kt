package io.matthewbradshaw.merovingian

import com.jme3.app.SimpleApplication
import com.jme3.app.VRAppState
import com.jme3.asset.AssetManager
import dagger.Component
import dagger.BindsInstance
import io.matthewbradshaw.merovingian.engine.EngineModules
import io.matthewbradshaw.merovingian.config.Paradigm
import io.matthewbradshaw.merovingian.clock.Clock
import io.matthewbradshaw.merovingian.clock.ClockModule
import kotlinx.coroutines.CoroutineScope
import com.jme3.renderer.Camera
import kotlinx.coroutines.CoroutineDispatcher
import com.jme3.scene.Node
import io.matthewbradshaw.merovingian.coroutines.DispatcherModule
import io.matthewbradshaw.merovingian.host.HostFactory
import io.matthewbradshaw.merovingian.engine.EngineBound

@MerovingianScope
@Component(
  modules = [
    EngineModules.Binding::class,
    EngineModules.Provisioning::class,
    ClockModule::class,
    DispatcherModule::class,
  ]
)
interface MerovingianComponent {

  fun clock(): Clock
  fun camera(): Camera
  fun assetManager(): AssetManager
  fun app(): SimpleApplication
  fun vr(): VRAppState

  @EngineBound
  fun coroutineDispatcher(): CoroutineDispatcher

  @EngineBound
  fun coroutineScope(): CoroutineScope

  fun hostFactory(): HostFactory

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setParadigm(paradigm: Paradigm): Builder
    fun build(): MerovingianComponent
  }
}

fun merovingian(paradigm: Paradigm): MerovingianComponent = DaggerMerovingianComponent.builder().setParadigm(paradigm).build()