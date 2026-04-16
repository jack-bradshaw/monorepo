package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/** Provides a [LoggingProcessor]. */
class LoggingProvider : SymbolProcessorProvider {

  /** The processor supplied by this provider. Available after [create]. */
  lateinit var processor: LoggingProcessor
    private set

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    processor = LoggingProcessor(environment.logger)
    return processor
  }
}
