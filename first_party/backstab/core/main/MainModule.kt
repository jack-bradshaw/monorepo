package com.jackbradshaw.backstab.core.main

import dagger.Binds
import dagger.Module

/** Dagger [Module] for the Main orchestrator. */
@Module
interface MainModule {
  /** [Binds] the concrete implementation of [Main]. */
  @Binds fun bindMain(impl: MainImpl): Main
}
