package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/** Provides a [ClassCollectingProcessor]. */
class ClassCollectingProvider : SymbolProcessorProvider {

  /** The processor supplied by this provider. Available after [create]. */
  val processor = ClassCollectingProcessor()

  override fun create(environment: SymbolProcessorEnvironment) = processor
}
