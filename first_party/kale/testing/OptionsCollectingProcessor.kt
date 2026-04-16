package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

/**
 * A processor that tracks whether [process] was run in the [didRunProcess] variable and exposes
 * [receivedOptions].
 */
class OptionsCollectingProcessor(val receivedOptions: Map<String, String>) : SymbolProcessor {

  /** Whether [process] was invoked. */
  var didRunProcess = false
    private set

  override fun process(resolver: Resolver): List<KSAnnotated> {
    didRunProcess = true
    return emptyList()
  }
}
