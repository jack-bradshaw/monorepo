package io.matthewbradshaw.gmonkey.octavius

import dagger.Component
import dagger.BindsInstance
import io.matthewbradshaw.gmonkey.octavius.engine.EngineModule
import io.matthewbradshaw.gmonkey.octavius.engine.Engine
import io.matthewbradshaw.gmonkey.octavius.engine.Paradigm
import io.matthewbradshaw.gmonkey.octavius.ticker.Ticker
import io.matthewbradshaw.gmonkey.octavius.ticker.TickerModule

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