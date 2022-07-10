package io.matthewbradshaw.frankl.host

import io.matthewbradshaw.frankl.model.Game
import io.matthewbradshaw.frankl.model.LevelSnapshotId
import io.matthewbradshaw.frankl.model.LevelSnapshot

interface Host {
  suspend fun play(game: Game<*>)
  suspend fun stop()
}