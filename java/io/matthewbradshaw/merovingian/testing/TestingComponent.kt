package io.matthewbradshaw.merovingian.testing

import io.matthewbradshaw.merovingian.MerovingianComponent
import io.matthewbradshaw.merovingian.host.HostFactory
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
import io.matthewbradshaw.merovingian.engine.RootNode

@TestingScope
@Component(modules = [MaterialsModule::class], dependencies = [MerovingianComponent::class])
interface TestingComponent {

  fun cubeSwarm(): CubeSwarm
  fun hostfactory(): HostFactory

  @Component.Builder
  interface Builder {
    @BindsInstance fun setMerovingianComponent(merovingianComponent: MerovingianComponent): Builder
    fun build(): TestingComponent
  }
}

fun testing(merovingian: MerovingianComponent) = DaggerTestingComponent.builder().setMerovingianComponent(merovingian).build()