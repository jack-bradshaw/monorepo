package com.jackbradshaw.backstab.core.processor

import com.jackbradshaw.backstab.core.generator.MetaComponentGenerator
import com.jackbradshaw.backstab.core.parser.Parser
import com.jackbradshaw.backstab.core.writer.Writer
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import dagger.BindsInstance
import dagger.Component

@RunWith(JUnit4::class)
class ProcessorImplTest : ProcessorTest() {
    override fun createSubject(
        generator: MetaComponentGenerator,
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
        @BindsInstance fun generator(generator: MetaComponentGenerator): Builder
        @BindsInstance fun writer(writer: Writer): Builder
        @BindsInstance fun parser(parser: Parser): Builder
        fun build(): ProcessorTestComponent
    }
}
