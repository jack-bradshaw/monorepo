package com.jackbradshaw.backstab.processor.generator

import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.squareup.kotlinpoet.FileSpec

/** Generates an aggregate Dagger module for a [BackstabComponent]. */
interface AggregateComponentGenerator {
  /**
   * Generates a FileSpec containing the Aggregate Component module.
   *
   * @param component The model representing the Dagger component to generate an aggregate for.
   */
  suspend fun generate(component: BackstabComponent): FileSpec
}
