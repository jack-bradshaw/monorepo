package com.jackbradshaw.backstab.core.ports.targetsource

import com.jackbradshaw.backstab.core.model.BackstabTarget
import kotlinx.coroutines.flow.Flow

/** Exposes backstab targets from the underlying build system. */
interface TargetSource {
  /** Returns a flow of [BackstabTarget]s that require processing. */
  fun observeTargets(): Flow<BackstabTarget>
}
