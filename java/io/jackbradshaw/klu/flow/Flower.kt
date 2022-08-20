package io.jackbradshaw.klu.flow

import kotlinx.coroutines.flow.Flow

interface Flower<T> {
  suspend fun get(): T
  fun asFlow(): Flow<T>
}