package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/** Provides a [CodeGeneratingProcessor]. */
class CodeGeneratingProvider : SymbolProcessorProvider {

  /** The processor supplied by this provider. Available after [create]. */
  lateinit var processor: CodeGeneratingProcessor
    private set

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    processor = CodeGeneratingProcessor(environment.codeGenerator)
    return processor
  }
}
