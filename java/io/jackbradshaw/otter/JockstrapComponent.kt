package io.jackbradshaw.otter

import dagger.BindsInstance
import dagger.Component
import io.jackbradshaw.otter.clock.Clock
import io.jackbradshaw.otter.clock.ClockModule
import io.jackbradshaw.otter.clock.Physics
import io.jackbradshaw.otter.clock.Rendering
import io.jackbradshaw.otter.clock.Real
import io.jackbradshaw.otter.config.Config
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.engine.EngineModule

@otterScope
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