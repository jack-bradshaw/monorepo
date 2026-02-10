package com.jackbradshaw.backstab.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration

/** Orchestrates the Backstab generation process. */
interface Processor {
  /**
   * Creates an aggregate component for each dagger component in [components].
   */
  suspend fun createAggregateComponents(components: List<KSClassDeclaration>)
}
