package io.matthewbradshaw.gmonkey.ui

import io.matthewbradshaw.gmonkey.lifecycle.Pausable
import io.matthewbradshaw.gmonkey.lifecycle.Restorable
import io.matthewbradshaw.gmonkey.lifecycle.Preparable
import com.google.protobuf.MessageLite
import kotlinx.coroutines.flow.Flow

interface Region<S: MessageLite> : Restorable<S>, Preparable, Pausable {
  fun allItems(): Flow<Set<Item<*>>>
  fun playerIsNearby(): Flow<Boolean>
}