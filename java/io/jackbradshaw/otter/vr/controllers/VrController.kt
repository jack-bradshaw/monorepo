package io.jackbradshaw.otter.vr.controllers

import io.jackbradshaw.otter.physics.Placement
import kotlinx.coroutines.flow.Flow
import io.jackbradshaw.otter.math.Vector

interface VrController {
  fun placement(): Flow<Placement>
  suspend fun setHapticFeedbackOn(on: Boolean)
  suspend fun isHapticFeedbackOn(): Flow<Boolean>
  fun buttonPressed(): Flow<Boolean>
  fun gripped(): Flow<Boolean>
  fun triggered(): Flow<Boolean>
  fun thumbstickPressed(): Flow<Boolean>
  fun thumbstickPosition(): Flow<Vector>
}

