package io.matthewbradshaw.jockstrap.model.frames

import io.matthewbradshaw.jockstrap.physics.Placement
import kotlinx.coroutines.flow.Flow

interface Placeable {
  fun placement(): Flow<Placement>
  suspend fun place(placement: Placement)
  suspend fun place((placement: Placement) -> Placement)
}