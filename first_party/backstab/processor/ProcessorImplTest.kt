package com.jackbradshaw.backstab.processor

import com.jackbradshaw.backstab.processor.generator.Generator
import com.jackbradshaw.backstab.processor.parser.Parser
import com.jackbradshaw.backstab.processor.writer.Writer
import dagger.BindsInstance
import dagger.Component
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProcessorImplTest : ProcessorTest() {
  override fun createSubject(
      generator: Generator,
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

@ProcessorScope
@Component(modules = [ProcessorImplModule::class])
interface ProcessorTestComponent {
  fun processor(): Processor

  @Component.Builder
  interface Builder {
    @BindsInstance fun generator(generator: Generator): Builder

    @BindsInstance fun writer(writer: Writer): Builder

    @BindsInstance fun parser(parser: Parser): Builder

    fun build(): ProcessorTestComponent
  }
}
