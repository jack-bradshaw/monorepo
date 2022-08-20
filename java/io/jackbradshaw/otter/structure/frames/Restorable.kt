package io.jackbradshaw.otter.structure.frames

import com.google.protobuf.MessageLite
import io.jackbradshaw.klu.flow.Flower
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface Restorable<S : MessageLite> {
  val snapshotPossible: Flower<Boolean>

  suspend fun restore(snapshot: S) = Unit
  suspend fun snapshot(): S
}