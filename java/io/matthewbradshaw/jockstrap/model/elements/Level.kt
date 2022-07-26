package io.matthewbradshaw.jockstrap.model.elements

import io.matthewbradshaw.jockstrap.model.core.Hostable
import io.matthewbradshaw.jockstrap.model.core.Restorable
import io.matthewbradshaw.jockstrap.model.core.Stageable
import io.matthewbradshaw.jockstrap.model.frames.Simulatable

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