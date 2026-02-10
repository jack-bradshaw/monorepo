package com.jackbradshaw.backstab.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.jackbradshaw.backstab.processor.Processor
import com.jackbradshaw.backstab.processor.ProcessorImplModule
import com.jackbradshaw.backstab.processor.generator.GeneratorModule
import com.jackbradshaw.backstab.processor.parser.ParserModule
import com.jackbradshaw.backstab.processor.writer.WriterModule
import dagger.BindsInstance
import dagger.Component

/** The concrete Dagger component for the Backstab core. */
@ProcessorScope
@Component(
    modules =
        [
            ProcessorImplModule::class,
            GeneratorModule::class,
            ParserModule::class,
            WriterModule::class])
interface ProcessorComponentImpl : ProcessorComponent {
  override fun processor(): Processor

  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(codeGenerator: CodeGenerator): Builder

    fun build(): ProcessorComponentImpl
  }
}

/** Returns a [ProcessorComponent] which uses the provided [codeGenerator] to write files. */
fun processorComponent(codeGenerator: CodeGenerator): ProcessorComponent =
    DaggerProcessorComponentImpl.builder().binding(codeGenerator).build()
