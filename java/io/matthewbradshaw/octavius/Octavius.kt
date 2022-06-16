package io.matthewbradshaw.octavius

import dagger.Component
import dagger.BindsInstance
import io.matthewbradshaw.octavius.engine.EngineModule
import io.matthewbradshaw.octavius.ignition.IgnitionModule
import io.matthewbradshaw.octavius.ignition.Ignition
import io.matthewbradshaw.octavius.core.Paradigm
import io.matthewbradshaw.octavius.ticker.Ticker
import io.matthewbradshaw.octavius.ticker.TickerModule

@OctaviusScope
@Component(modules = [EngineModule::class, TickerModule::class, IgnitionModule::class])
interface Octavius {

  fun ignition(): Ignition
  fun ticker(): Ticker
  fun engine(): Engine

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun paradigm(paradigm: Paradigm): Builder
    fun build(): Octavius
  }
}