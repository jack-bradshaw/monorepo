package io.matthewbradshaw.gmonkey.core.ignition

import dagger.Binds
import dagger.Module

@Module
interface IgnitionModule {
  @Binds
  fun bindIgnition(impl: IgnitionImpl): Ignition
}