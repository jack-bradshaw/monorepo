package io.jackbradshaw.otter

import dagger.BindsInstance
import dagger.Component
import io.jackbradshaw.otter.clock.Clock
import io.jackbradshaw.otter.clock.ClockModule
import io.jackbradshaw.otter.clock.Physics
import io.jackbradshaw.otter.clock.Rendering
import io.jackbradshaw.otter.clock.Real
import io.jackbradshaw.otter.engine.config.Config
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.engine.EngineModule

@OtterScope
@Component(
  modules = [
    EngineModule::class,
    ClockModule::class,
  ]
)
interface OtterComponent {

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
    fun build(): OtterComponent
  }
}

fun otter(config: Config): OtterComponent =
  DaggerOtterComponent.builder().setConfig(config).build()