package io.matthewbradshaw.frankl.snapshot

import io.matthewbradshaw.klu.flow.BinaryDeltaFlow
import io.matthewbradshaw.frankl.model.LevelSnapshotId
import io.matthewbradshaw.frankl.model.LevelSnapshot

interface SnapshotStore {
  suspend fun snapshotExists(id: LevelSnapshotId): Boolean
  suspend fun allSnapshots(): BinaryDeltaFlow<LevelSnapshotId>
  suspend fun saveSnapshot(snapshot: LevelSnapshot): LevelSnapshotId
  suspend fun loadSnapshot(id: LevelSnapshotId): LevelSnapshot
  suspend fun deleteSnapshot(id: LevelSnapshotId)
}