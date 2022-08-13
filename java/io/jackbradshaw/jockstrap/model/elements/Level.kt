package io.jackbradshaw.jockstrap.elements

import io.jackbradshaw.jockstrap.model.frames.Hostable
import io.jackbradshaw.jockstrap.model.frames.Restorable
import io.jackbradshaw.jockstrap.model.frames.Placeable
import io.jackbradshaw.jockstrap.model.frames.Simulatable
import io.jackbradshaw.klu.flow.BinaryDeltaFlow

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