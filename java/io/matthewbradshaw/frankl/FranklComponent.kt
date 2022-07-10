package io.matthewbradshaw.frankl

import dagger.BindsInstance
import dagger.Component
import io.matthewbradshaw.frankl.clock.Clock
import io.matthewbradshaw.frankl.clock.Physics
import io.matthewbradshaw.frankl.clock.Rendering
import io.matthewbradshaw.frankl.clock.ClockModule
import io.matthewbradshaw.frankl.config.Config
import io.matthewbradshaw.frankl.engine.Engine
import io.matthewbradshaw.frankl.engine.EngineModule
import io.matthewbradshaw.frankl.host.Host
import io.matthewbradshaw.frankl.host.HostModule

@FranklScope
@Component(
  modules = [
    EngineModule::class,
    ClockModule::class,
    HostModule::class,
  ]
)
interface MerovingianComponent {

  @Physics fun physicsClock(): Clock
  @Rendering fun renderingClock(): Clock
  fun engine(): Engine
  fun host(): Host

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setConfig(config: Config): Builder
    fun build(): MerovingianComponent
  }
}

fun merovingian(config: Config): MerovingianComponent =
  DaggerMerovingianComponent.builder().setConfig(config).build()