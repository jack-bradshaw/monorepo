package io.matthewbradshaw.jockstrap.elements

import io.matthewbradshaw.jockstrap.frames.Hostable
import io.matthewbradshaw.jockstrap.frames.Restorable
import io.matthewbradshaw.jockstrap.frames.Placeable
import io.matthewbradshaw.jockstrap.frames.Simulatable
import io.matthewbradshaw.klu.flow.BinaryDeltaFlow

interface Level : Hostable, Restorable<LevelSnapshot>, Simulatable {
  val id: LevelId

  suspend fun findEntity(id: EntityId, recursive: Boolean = false): Entity?
  suspend fun findComponent(id: ComponentId): Component<*>?
  suspend fun findComponent(intrinsic: Any): Component<*>?

  fun entities(): Set<Entity>
  fun entityFlow(): BinaryDeltaFlow<Entity>

  fun components(): Set<Component<*>>
  fun componentFlow(): BinaryDeltaFlow<Component<*>>
}