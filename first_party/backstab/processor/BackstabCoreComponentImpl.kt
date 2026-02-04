package com.jackbradshaw.backstab.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.jackbradshaw.backstab.processor.core.Processor
import com.jackbradshaw.backstab.processor.core.ProcessorModule
import com.jackbradshaw.backstab.processor.generator.AggregateComponentGeneratorModule
import com.jackbradshaw.backstab.processor.parser.ParserModule
import com.jackbradshaw.backstab.processor.writer.WriterModule
import dagger.BindsInstance
import dagger.Component

/** The concrete Dagger component for the Backstab core. */
@BackstabCoreScope
@Component(
    modules =
        [
            ProcessorModule::class,
            AggregateComponentGeneratorModule::class,
            ParserModule::class,
            WriterModule::class])
interface BackstabCoreComponentImpl : BackstabCoreComponent {
  override fun processor(): Processor

  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(codeGenerator: CodeGenerator): Builder

    fun build(): BackstabCoreComponentImpl
  }
}

/** Returns a [BackstabCoreComponent] which uses the provided [codeGenerator] to write files. */
fun backstabCoreComponent(codeGenerator: CodeGenerator): BackstabCoreComponent =
    DaggerBackstabCoreComponentImpl.builder().binding(codeGenerator).build()
