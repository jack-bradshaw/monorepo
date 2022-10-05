package io.jackbradshaw.otter.engine.core

import dagger.Binds
import dagger.Module

@Module
interface EngineModule {
  @Binds fun bindEngine(impl: EngineCoreImpl): EngineCore
}
