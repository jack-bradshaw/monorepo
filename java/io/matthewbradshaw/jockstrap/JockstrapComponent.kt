package io.matthewbradshaw.jockstrap

import dagger.BindsInstance
import dagger.Component
import io.matthewbradshaw.jockstrap.clock.Clock
import io.matthewbradshaw.jockstrap.clock.ClockModule
import io.matthewbradshaw.jockstrap.clock.Physics
import io.matthewbradshaw.jockstrap.clock.Rendering
import io.matthewbradshaw.jockstrap.clock.Real
import io.matthewbradshaw.jockstrap.config.Config
import io.matthewbradshaw.jockstrap.engine.Engine
import io.matthewbradshaw.jockstrap.engine.EngineModule

@JockstrapScope
@Component(
  modules = [
    EngineModule::class,
    ClockModule::class,
  ]
)
interface TrinityComponent {

  @Physics
  fun physicsClock(): Clock

  @Rendering
  fun renderingClock(): Clock

  @Real
  fun realClock(): Clock

  fun engine(): Engine

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setConfig(config: Config): Builder
    fun build(): TrinityComponent
  }
}

fun trinity(config: Config): TrinityComponent =
  DaggerTrinityComponent.builder().setConfig(config).build()