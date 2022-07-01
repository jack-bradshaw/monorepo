package io.matthewbradshaw.merovingian

import dagger.BindsInstance
import dagger.Component
import io.matthewbradshaw.merovingian.clock.Clock
import io.matthewbradshaw.merovingian.clock.ClockModule
import io.matthewbradshaw.merovingian.config.Paradigm
import io.matthewbradshaw.merovingian.engine.Engine
import io.matthewbradshaw.merovingian.engine.EngineModule
import io.matthewbradshaw.merovingian.host.Host
import io.matthewbradshaw.merovingian.host.HostModule

@MerovingianScope
@Component(
  modules = [
    EngineModule::class,
    ClockModule::class,
    HostModule::class,
  ]
)
interface MerovingianComponent {

  fun clock(): Clock
  fun engine(): Engine
  fun host(): Host

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setParadigm(paradigm: Paradigm): Builder
    fun build(): MerovingianComponent
  }
}

fun merovingian(paradigm: Paradigm): MerovingianComponent =
  DaggerMerovingianComponent.builder().setParadigm(paradigm).build()