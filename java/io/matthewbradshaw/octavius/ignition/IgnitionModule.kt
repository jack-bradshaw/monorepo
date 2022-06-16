package io.matthewbradshaw.octavius.ignition

import dagger.Binds
import dagger.Module

@Module
interface IgnitionModule {
  @Binds
  fun bindIgnition(impl: IgnitionImpl): Ignition
}