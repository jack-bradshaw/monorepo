package com.jackbradshaw.coroutines.testing.advancer

import dagger.Binds
import dagger.Module

/** Provides bindings for [Advancer]. */
@Module
interface AdvancerModule {
  @Binds fun advancer(impl: AdvancerImpl): Advancer
}
