package io.jackbradshaw.otter.vr.controllers

import io.jackbradshaw.otter.physics.Placement
import kotlinx.coroutines.flow.Flow

interface VrControllers {
  suspend fun getController(index: Int): VrController
}

