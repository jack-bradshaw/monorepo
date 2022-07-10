package io.matthewbradshaw.merovingian.snapshot

import io.matthewbradshaw.klu.flow.BinaryDeltaFlow
import io.matthewbradshaw.merovingian.model.LevelSnapshotId
import io.matthewbradshaw.merovingian.model.LevelSnapshot

interface SnapshotStore {
  suspend fun snapshotExists(id: LevelSnapshotId): Boolean
  suspend fun allSnapshots(): BinaryDeltaFlow<LevelSnapshotId>
  suspend fun saveSnapshot(snapshot: LevelSnapshot): LevelSnapshotId
  suspend fun loadSnapshot(id: LevelSnapshotId): LevelSnapshot
  suspend fun deleteSnapshot(id: LevelSnapshotId)
}