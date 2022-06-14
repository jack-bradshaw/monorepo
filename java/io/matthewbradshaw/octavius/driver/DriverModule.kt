package io.matthewbradshaw.octavius.driver

import dagger.Binds
import dagger.Module

@Module
interface DriverModule {
  @Binds
  fun bindDriver(impl: DriverImpl): Driver
}