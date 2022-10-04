package io.jackbradshaw.otter.structure.frames

interface Hostable<T> {
  suspend fun attachedTo(host: T) = Unit
  suspend fun detachedFrom(host: T) = Unit
}
