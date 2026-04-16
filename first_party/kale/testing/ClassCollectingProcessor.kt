package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

/**
 * A [SymbolProcessor] that tracks whether [process] was invoked and collects the name of every
 * class it receives.
 */
class ClassCollectingProcessor : SymbolProcessor {

  /** Class names progressively collected over multiple processing rounds. */
  private val progressiveClassNameCollection = mutableSetOf<String>()

  /** Whether [process] was invoked. */
  var didRunProcess = false
    private set

  /** The classes observed during processing. Available after [finish]. */
  var collectedClassNames = emptySet<String>()
    private set

  override fun process(resolver: Resolver): List<KSAnnotated> {
    didRunProcess = true

    progressiveClassNameCollection +=
        resolver
            .getAllFiles()
            .flatMap { it.declarations }
            .filterIsInstance<KSClassDeclaration>()
            .map { it.simpleName.asString() }

    return emptyList()
  }

  override fun finish() {
    collectedClassNames = progressiveClassNameCollection.toSet()
  }
}
