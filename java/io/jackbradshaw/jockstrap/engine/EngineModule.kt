package io.jackbradshaw.jockstrap.engine

import dagger.Binds

@Module
interface EngineModule {
  @Binds
  fun bindEngine(impl: EngineImpl): Engine
}