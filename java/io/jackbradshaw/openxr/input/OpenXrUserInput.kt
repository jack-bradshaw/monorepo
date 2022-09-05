package io.jackbradshaw.otter.openxr.input

import io.jackbradshaw.otter.math.Vector
import io.jackbradshaw.otter.physics.Placement
import kotlinx.coroutines.flow.Flow

interface OpenXrUserInput {

  fun clicked(): Flow<Boolean>

  fun touched(): Flow<Boolean>

  fun forced(): Flow<Float>

  fun valued(): Flow<Float>

  fun moved(): Flow<Vector>

  fun twisted(): Flow<Float>

  fun posed(): Flow<Placement>
}
