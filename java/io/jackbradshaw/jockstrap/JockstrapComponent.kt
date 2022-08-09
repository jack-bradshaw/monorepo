package io.jackbradshaw.jockstrap

import dagger.BindsInstance
import dagger.Component
import io.jackbradshaw.jockstrap.clock.Clock
import io.jackbradshaw.jockstrap.clock.ClockModule
import io.jackbradshaw.jockstrap.clock.Physics
import io.jackbradshaw.jockstrap.clock.Rendering
import io.jackbradshaw.jockstrap.clock.Real
import io.jackbradshaw.jockstrap.config.Config
import io.jackbradshaw.jockstrap.engine.Engine
import io.jackbradshaw.jockstrap.engine.EngineModule

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