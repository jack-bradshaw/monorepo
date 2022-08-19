package io.matthewbradshaw.merovingian.engine

import dagger.Binds
import dagger.Module

@Module
interface EngineModule {
  @Binds
  fun bindEngine(impl: EngineImpl): Engine
}