package io.matthewbradshaw.klu.flow

import kotlinx.coroutines.flow.Flow

interface Flower<T> {
  suspend fun set(t: T)
  suspend fun get(): T
  suspend fun transform(transform: suspend (T) -> T): T
  fun asFlow(): Flow<T>
}