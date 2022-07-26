package io.matthewbradshaw.jockstrap.restoration

import io.matthewbradshaw.klu.flow.BinaryDeltaFlow
import io.matthewbradshaw.jockstrap.model.elements.LevelSnapshot

interface SnapshotStore {
  suspend fun snapshotExists(id: RestorationId): Boolean
  suspend fun allSnapshots(): BinaryDeltaFlow<RestorationId>
  suspend fun saveSnapshot(snapshot: LevelSnapshot): RestorationId
  suspend fun loadSnapshot(id: RestorationId): LevelSnapshot
  suspend fun deleteSnapshot(id: RestorationId)
}