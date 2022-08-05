package io.matthewbradshaw.klu.flow

import kotlinx.coroutines.flow.Flow

interface MutableFlower<T> : Flower<T> {
  suspend fun set(t: T)
  suspend fun transform(transform: suspend (T) -> T): T
}