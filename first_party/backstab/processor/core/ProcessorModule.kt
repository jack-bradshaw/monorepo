package com.jackbradshaw.backstab.processor.core

import com.jackbradshaw.backstab.processor.generator.AggregateComponentGenerator
import com.jackbradshaw.backstab.processor.parser.Parser
import com.jackbradshaw.backstab.processor.writer.Writer
import dagger.Module
import dagger.Provides

/** Dagger module for the Processor package. */
@Module
object ProcessorModule {
  /** Provides the concrete implementation of [Processor]. */
  @Provides
  fun provideProcessor(
      generator: AggregateComponentGenerator,
      writer: Writer,
      parser: Parser
  ): Processor = ProcessorImpl(parser, generator, writer)
}
