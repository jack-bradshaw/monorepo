package com.jackbradshaw.backstab.processor.core

import com.google.devtools.ksp.symbol.KSClassDeclaration

/** Orchestrates the Backstab generation process. */
interface Processor {
  /**
   * Creates aggregate components for the given list of Dagger components.
   *
   * @param components The list of [KSClassDeclaration] symbols representing the Dagger components.
   */
  suspend fun createAggregateComponents(components: List<KSClassDeclaration>)
}
