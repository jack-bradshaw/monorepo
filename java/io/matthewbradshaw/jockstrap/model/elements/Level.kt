package io.matthewbradshaw.jockstrap.model.elements

import io.matthewbradshaw.jockstrap.model.frames.Hostable
import io.matthewbradshaw.jockstrap.model.frames.Restorable
import io.matthewbradshaw.jockstrap.model.frames.Placeable
import io.matthewbradshaw.jockstrap.model.frames.Simulatable
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