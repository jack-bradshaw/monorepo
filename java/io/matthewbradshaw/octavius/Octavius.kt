package io.matthewbradshaw.octavius

import dagger.Component
import dagger.BindsInstance
import io.matthewbradshaw.octavius.engine.EngineModule
import io.matthewbradshaw.octavius.engine.Engine
import io.matthewbradshaw.octavius.engine.Paradigm
import io.matthewbradshaw.octavius.ticker.Ticker
import io.matthewbradshaw.octavius.ticker.TickerModule

@OctaviusScope
@Component(modules = [EngineModule::class, TickerModule::class])
interface Octavius {

  fun ticker(): Ticker
  fun engine(): Engine

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun paradigm(paradigm: Paradigm): Builder
    fun build(): Octavius
  }
}