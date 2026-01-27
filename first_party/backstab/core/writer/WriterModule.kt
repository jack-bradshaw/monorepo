package com.jackbradshaw.backstab.core.writer

import com.google.devtools.ksp.processing.CodeGenerator
import dagger.Module
import dagger.Provides

@Module
object WriterModule {
  @Provides
  fun provideWriter(codeGenerator: CodeGenerator): Writer = FileWriterImpl(codeGenerator)
}
