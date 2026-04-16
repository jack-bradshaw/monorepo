package com.jackbradshaw.kale.processor

import dagger.Binds
import dagger.Module

@Module
interface ProcessorRunnerModule {
  @Binds fun bindProcessorRunner(impl: ProcessorRunnerImpl): ProcessorRunner
}
