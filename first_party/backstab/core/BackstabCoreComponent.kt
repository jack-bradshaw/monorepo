package com.jackbradshaw.backstab.core

import com.google.devtools.ksp.processing.CodeGenerator
import com.jackbradshaw.backstab.core.generator.MetaComponentGeneratorModule
import com.jackbradshaw.backstab.core.parser.ParserModule
import com.jackbradshaw.backstab.core.processor.Processor
import com.jackbradshaw.backstab.core.processor.ProcessorModule
import com.jackbradshaw.backstab.core.writer.WriterModule
import dagger.BindsInstance
import dagger.Component

interface BackstabCoreComponent {
  fun processor(): Processor
}

@BackstabCoreScope
@Component(modules = [
  ProcessorModule::class,
  MetaComponentGeneratorModule::class,
  ParserModule::class,
  WriterModule::class
])
interface ProdBackstabCoreComponent : BackstabCoreComponent {
  override fun processor(): Processor

  @Component.Builder
  interface Builder {
    @BindsInstance fun binding(codeGenerator: CodeGenerator): Builder
    fun build(): ProdBackstabCoreComponent
  }
}

fun backstabCoreComponent(codeGenerator: CodeGenerator): BackstabCoreComponent =
    DaggerProdBackstabCoreComponent.builder()
        .binding(codeGenerator)
        .build()
