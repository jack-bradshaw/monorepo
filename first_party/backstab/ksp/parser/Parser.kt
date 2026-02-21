package com.jackbradshaw.backstab.ksp.parser

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.core.model.BackstabTarget
import dagger.Component

/** Parses Dagger [KSClassDeclaration] symbols into the Backstab domain model. */
interface Parser {
  /**
   * Parses a Dagger [KSClassDeclaration] symbol into a [BackstabTarget] model.
   *
   * @throws IllegalArgumentException if [declaration] is not annotated with [Component].
   */
  fun toBackstabTarget(declaration: KSClassDeclaration): BackstabTarget
}
