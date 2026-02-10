package com.jackbradshaw.backstab.processor.generator

import dagger.Binds
import dagger.Module

/** Dagger module for the Generator package. */
@Module
interface GeneratorModule {
  /** Binds the concrete implementation of [Generator]. */
  @Binds fun bindGenerator(impl: GeneratorImpl): Generator
}
