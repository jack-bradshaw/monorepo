package com.jackbradshaw.backstab.processor.generator

import dagger.Binds
import dagger.Module

/** Dagger module for the Generator package. */
@Module
interface AggregateComponentGeneratorModule {
  /** Binds the concrete implementation of [AggregateComponentGenerator]. */
  @Binds fun bindGenerator(impl: AggregateComponentGeneratorImpl): AggregateComponentGenerator
}
