package com.jackbradshaw.backstab.processor.parser

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.processor.model.BackstabComponent

/** Parses Dagger [KSClassDeclaration] symbols into the Backstab domain model. */
interface Parser {
  /**
   * Parses a Dagger [KSClassDeclaration] symbol into a [BackstabComponent] model.
   *
   * @throws IllegalArgumentException if [component] is not annotated with `@dagger.Component`.
   */
  fun parseModel(component: KSClassDeclaration): BackstabComponent
}
