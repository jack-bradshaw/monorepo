package io.jackbradshaw.otter.userio

import kotlinx.coroutines.flow.Flow

interface VrController {
  fun placement(): Flow<Placement>
}