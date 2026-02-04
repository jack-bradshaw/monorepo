package com.jackbradshaw.backstab.processor.writer

import dagger.Binds
import dagger.Module

/** Dagger module for the Writer package. */
@Module
interface WriterModule {
  /** Binds the concrete implementation of [Writer]. */
  @Binds fun bindWriter(impl: WriterImpl): Writer
}
