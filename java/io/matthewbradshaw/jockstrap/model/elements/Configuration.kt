package io.matthewbradshaw.jockstrap.model.elements

import com.google.protobuf.MessageLite
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface Configuration<D : MessageLite> {

  fun details(): Flow<D> = flowOf()

  suspend fun configure(details: D) = Unit

  suspend fun configure(transform: suspend (D) -> D) = Unit
}