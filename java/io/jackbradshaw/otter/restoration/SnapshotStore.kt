package io.jackbradshaw.otter.restoration

import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.otter.elements.LevelSnapshot

interface SnapshotStore {
  suspend fun snapshotExists(id: RestorationId): Boolean
  suspend fun allSnapshots(): BinaryDeltaFlow<RestorationId>
  suspend fun saveSnapshot(snapshot: LevelSnapshot): RestorationId
  suspend fun loadSnapshot(id: RestorationId): LevelSnapshot
  suspend fun deleteSnapshot(id: RestorationId)
}
