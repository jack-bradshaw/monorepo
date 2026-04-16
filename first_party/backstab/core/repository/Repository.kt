package com.jackbradshaw.backstab.core.repository

import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import kotlinx.coroutines.flow.Flow

/**
 * Exposes backstab targets from the underlying build system and provides a way to report back
 * modules and errors.
 */
interface Repository {
  /** Returns a flow of [BackstabTarget]s that require processing. */
  fun observeTargets(): Flow<BackstabTarget>

  /**
   * Associates [module] with [target] and persists it via the underlying build-system/compiler/etc.
   */
  suspend fun publishModule(target: BackstabTarget, module: BackstabModule)

  /**
   * Associates [error] with [target] and reports it to the underlying build-system/compiler/etc.
   */
  suspend fun publishError(target: BackstabTarget, error: Throwable)
}
