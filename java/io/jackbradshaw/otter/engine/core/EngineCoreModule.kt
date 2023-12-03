package io.jackbradshaw.otter.engine.core

import dagger.Binds
import dagger.Module

@Module
interface EngineCoreModule {
  @Binds fun bindEngine(impl: EngineCoreImpl): EngineCore
}
