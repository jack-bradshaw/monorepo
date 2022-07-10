package io.matthewbradshaw.frankl.model

import io.matthewbradshaw.frankl.model.LevelSnapshot
import io.matthewbradshaw.frankl.model.LevelKey
import kotlinx.coroutines.flow.Flow

interface Game {
  suspend fun createCleanLevel(levelKey: LevelKey): LevelItem
  fun levelReloadedOrChanged(): Flow<LevelSnapshot>

  suspend fun onAttachToHost()
  suspend fun onDetachFromHost()
}