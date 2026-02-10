package com.jackbradshaw.backstab.processor

import dagger.Binds
import dagger.Module

/** Dagger module for the Processor package. */
@Module
interface ProcessorImplModule {
  /** Binds the concrete implementation of [Processor]. */
  @Binds
  fun bindProcessor(impl: ProcessorImpl): Processor
}
