package io.matthewbradshaw.gmonkey.core

import com.jme3.app.SimpleApplication
import com.jme3.app.VRAppState
import com.jme3.asset.AssetManager
import dagger.Component
import dagger.BindsInstance
import io.matthewbradshaw.gmonkey.core.engine.EngineModules
import io.matthewbradshaw.gmonkey.core.model.Paradigm
import io.matthewbradshaw.gmonkey.core.model.Game
import io.matthewbradshaw.gmonkey.core.ticker.Ticker
import io.matthewbradshaw.gmonkey.core.ticker.TickerModule
import io.matthewbradshaw.gmonkey.core.ignition.Ignition
import io.matthewbradshaw.gmonkey.core.ignition.IgnitionModule
import com.jme3.renderer.Camera

@CoreScope
@Component(
  modules = [
    EngineModules.Binding::class,
    EngineModules.Provisioning::class,
    TickerModule::class,
    IgnitionModule::class
  ]
)
interface Core {

  fun ticker(): Ticker
  fun ignition(): Ignition
  fun camera(): Camera
  fun assetManager(): AssetManager
  fun app(): SimpleApplication
  fun vr(): VRAppState

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setGame(game: Game): Builder
    fun build(): Core
  }
}

fun core(game: Game): Core = DaggerCore.builder().setGame(game).build()