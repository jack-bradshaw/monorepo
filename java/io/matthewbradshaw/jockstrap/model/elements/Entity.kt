package io.matthewbradshaw.jockstrap.model.elements

import kotlinx.coroutines.flow.Flow
import io.matthewbradshaw.jockstrap.model.frames.Hostable
import io.matthewbradshaw.jockstrap.model.frames.Restorable
import io.matthewbradshaw.jockstrap.model.frames.Simulatable

import io.matthewbradshaw.jockstrap.model.frames.Placeable
import io.matthewbradshaw.jockstrap.physics.Placement
import io.matthewbradshaw.klu.flow.BinaryDeltaFlow

interface Entity : Hostable, Placeable, Restorable<EntitySnapshot> {
  val id: EntityId

  fun contents(): Set<Entity>
  fun contentFlow(): BinaryDeltaFlow<Entity>

  fun exports(): Set<Component<*>>
  fun exportFlow(): BinaryDeltaFlow<Component<*>>
}