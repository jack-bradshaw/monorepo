package com.jackbradshaw.backstab.processor.generator

import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.squareup.kotlinpoet.FileSpec

/** Generates an aggregate Dagger module for a [BackstabComponent]. */
interface Generator {
  /**
   * Generates a FileSpec containing a dagger module that provides [component].
   */
  suspend fun generate(component: BackstabComponent): FileSpec
}
