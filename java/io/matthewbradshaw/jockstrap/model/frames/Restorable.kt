package io.matthewbradshaw.jockstrap.model.frames

import com.google.protobuf.MessageLite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface Restorable<S : MessageLite> {
  suspend fun restore(snapshot: S)
  suspend fun captureSnapshot(): S
  fun isCapturable(): Flow<Boolean> = flowOf(true)
}