package com.jackbradshaw.backstab.core.ports.modulesink

import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget

/**
 * Provides a way to declare backstab modules to be exported back to the underlying build system.
 */
interface ModuleSink {
  /** Publishes new [modules] associated with the given [target]. */
  suspend fun publishModules(target: BackstabTarget, modules: List<BackstabModule>)
}
