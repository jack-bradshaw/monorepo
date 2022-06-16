package io.matthewbradshaw.octavius.ticker

import dagger.Binds
import dagger.Module

@Module
interface TickerModule {
  @Binds
  fun bindTicker(impl: TickerImpl): Ticker
}