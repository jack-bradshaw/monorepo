package io.matthewbradshaw.merovingian.clock

import dagger.Binds
import dagger.Module

@Module
interface ClockModule {
  @Binds
  fun bindClock(impl: ClockImpl): Clock
}