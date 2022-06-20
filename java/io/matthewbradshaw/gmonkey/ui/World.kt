package io.matthewbradshaw.gmonkey.ui


import io.matthewbradshaw.gmonkey.lifecycle.Pausable
import io.matthewbradshaw.gmonkey.lifecycle.Restorable
import com.google.protobuf.MessageLite
import io.matthewbradshaw.gmonkey.lifecycle.Preparable
import kotlinx.coroutines.flow.Flow

interface World<S: MessageLite> : Restorable<S>, Preparable, Pausable {
  fun currentLevel(): Flow<Level<*>>
}