package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

/** A [SymbolProcessor] that tracks whether [process] was invoked. */
class BasicProcessor : SymbolProcessor {

  /** Whether [process] was invoked. */
  var didRunProcess = false
    private set

  override fun process(resolver: Resolver): List<KSAnnotated> {
    didRunProcess = true
    return emptyList()
  }
}
