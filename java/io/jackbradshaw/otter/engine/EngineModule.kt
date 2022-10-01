package io.jackbradshaw.otter.engine

import dagger.Binds

@Module
interface EngineModule {
  @Binds fun bindEngine(impl: EngineImpl): Engine
}
