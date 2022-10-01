package io.jackbradshaw.otter.structure.frames

import com.google.protobuf.MessageLite
import io.jackbradshaw.klu.flow.Flower

interface Restorable<S : MessageLite> {
  val snapshotPossible: Flower<Boolean>

  suspend fun restore(snapshot: S) = Unit
  suspend fun snapshot(): S
}