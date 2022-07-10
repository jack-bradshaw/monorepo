package io.matthewbradshaw.merovingian.model

import io.matthewbradshaw.merovingian.model.LevelSnapshot
import io.matthewbradshaw.merovingian.model.LevelKey
import kotlinx.coroutines.flow.Flow

interface Game {
  suspend fun createCleanLevel(levelKey: LevelKey): LevelItem
  fun levelReloadedOrChanged(): Flow<LevelSnapshot>

  suspend fun onAttachToHost()
  suspend fun onDetachFromHost()
}