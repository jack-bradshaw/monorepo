package io.matthewbradshaw.merovingian.host

import io.matthewbradshaw.merovingian.model.Game
import io.matthewbradshaw.merovingian.model.LevelSnapshotId
import io.matthewbradshaw.merovingian.model.LevelSnapshot

interface Host {
  suspend fun play(game: Game<*>)
  suspend fun stop()
}