package io.jackbradshaw.klu.flow

interface MutableFlower<T> : Flower<T> {
  suspend fun set(t: T)
  suspend fun transform(transform: suspend (T) -> T): T
}
