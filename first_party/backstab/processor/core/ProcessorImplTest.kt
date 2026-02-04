package com.jackbradshaw.backstab.processor.core

import com.jackbradshaw.backstab.processor.generator.AggregateComponentGenerator
import com.jackbradshaw.backstab.processor.parser.Parser
import com.jackbradshaw.backstab.processor.writer.Writer
import dagger.BindsInstance
import dagger.Component
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProcessorImplTest : ProcessorTest() {
  override fun createSubject(
      generator: AggregateComponentGenerator,
      writer: Writer,
      parser: Parser
  ): Processor {
    return DaggerProcessorTestComponent.builder()
        .generator(generator)
        .writer(writer)
        .parser(parser)
        .build()
        .processor()
  }
}

@Component(modules = [ProcessorModule::class])
interface ProcessorTestComponent {
  fun processor(): Processor

  @Component.Builder
  interface Builder {
    @BindsInstance fun generator(generator: AggregateComponentGenerator): Builder

    @BindsInstance fun writer(writer: Writer): Builder

    @BindsInstance fun parser(parser: Parser): Builder

    fun build(): ProcessorTestComponent
  }
}
