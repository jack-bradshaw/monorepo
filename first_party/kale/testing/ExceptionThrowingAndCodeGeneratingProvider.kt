package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/** Provides an [ExceptionThrowingAndCodeGeneratingProcessor]. */
class ExceptionThrowingAndCodeGeneratingProvider : SymbolProcessorProvider {

  /** The processor supplied by this provider. Available after [create]. */
  lateinit var processor: ExceptionThrowingAndCodeGeneratingProcessor
    private set

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    processor = ExceptionThrowingAndCodeGeneratingProcessor(environment)
    return processor
  }
}
