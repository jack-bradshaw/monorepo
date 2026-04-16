package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

/**
 * A [SymbolProcessor] that tracks whether [process] was invoked and throws an exception during
 * processing.
 *
 * The thrown exception is a [RuntimeException] with message "foo".
 */
class ExceptionThrowingProcessor : SymbolProcessor {

  /** Whether [process] was invoked. */
  var didRunProcess = false
    private set

  override fun process(resolver: Resolver): List<KSAnnotated> {
    didRunProcess = true
    throw RuntimeException("foo")
  }
}
