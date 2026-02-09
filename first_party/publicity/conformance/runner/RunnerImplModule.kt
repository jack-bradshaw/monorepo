package com.jackbradshaw.publicity.conformance.runner

import dagger.Binds
import dagger.Module

@Module
interface RunnerImplModule {
  @Binds fun bindRunner(impl: RunnerImpl): Runner
}
