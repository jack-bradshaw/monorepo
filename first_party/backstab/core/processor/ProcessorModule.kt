package com.jackbradshaw.backstab.core.processor

import com.jackbradshaw.backstab.core.generator.MetaComponentGenerator
import com.jackbradshaw.backstab.core.parser.Parser
import com.jackbradshaw.backstab.core.writer.Writer
import dagger.Module
import dagger.Provides

@Module
object ProcessorModule {
  @Provides
  fun provideProcessor(
    generator: MetaComponentGenerator,
    writer: Writer,
    parser: Parser
  ): Processor = ProcessorImpl(generator, writer, parser)
}
