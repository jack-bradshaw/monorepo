package io.matthewbradshaw.octavius.ignition

import kotlinx.coroutines.flow.Flow

interface Ignition {
  fun started(): Flow<Unit>
  suspend fun ignite()
}