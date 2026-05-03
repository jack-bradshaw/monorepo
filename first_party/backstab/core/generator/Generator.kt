package com.jackbradshaw.backstab.core.generator

import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import dagger.Module

/** Generates an aggregate Dagger [Module] for a [BackstabTarget]. */
interface Generator {
  /** Generates a [Module] that provides [target]. */
  suspend fun generateModuleFor(target: BackstabTarget): BackstabModule
}
