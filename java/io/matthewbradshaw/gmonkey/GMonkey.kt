package io.matthewbradshaw.gmonkey

import com.jme3.app.SimpleApplication
import com.jme3.app.VRAppState
import com.jme3.asset.AssetManager
import dagger.Component
import dagger.BindsInstance
import io.matthewbradshaw.gmonkey.engine.EngineModules
import io.matthewbradshaw.gmonkey.ticker.Ticker
import io.matthewbradshaw.gmonkey.ticker.TickerModule
import com.jme3.renderer.Camera

@GMonkeyScope
@Component(
  modules = [
    EngineModules.Binding::class,
    EngineModules.Provisioning::class,
    TickerModule::class
  ]
)
interface GMonkey {

  fun ticker(): Ticker
  fun camera(): Camera
  fun assetManager(): AssetManager
  fun app(): SimpleApplication
  fun vr(): VRAppState

  @MainDispatcher
  fun mainDispatcher(): Dispatcher

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setParadigm(paradigm: Paradigm): Builder
    fun build(): GMonkey
  }
}

fun gMonkey(paradigm: Paradigm): GMonkey = DaggerGMonkey.builder().setParadigm(paradigm).build()