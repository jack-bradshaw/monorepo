package com.jackbradshaw.backstab.core.generator

import dagger.Binds
import dagger.Module

/** Dagger module for the Generator package. */
@Module
interface GeneratorImplModule {
  /** Binds the concrete implementation of [Generator]. */
  @Binds fun bindGenerator(impl: GeneratorImpl): Generator
}
