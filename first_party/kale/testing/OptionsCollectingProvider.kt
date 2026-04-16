package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/** Provides a [BasicProcessor] and collects the options supplied via the KSP environment. */
class OptionsCollectingProvider : SymbolProcessorProvider {

  /** The processor supplied by this provider. Available after [create]. */
  lateinit var processor: BasicProcessor
    private set

  /** The options supplied via the KSP environment. Available after [create]. */
  lateinit var options: Map<String, String>
    private set

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    processor = BasicProcessor()
    options = environment.options
    return processor
  }
}
