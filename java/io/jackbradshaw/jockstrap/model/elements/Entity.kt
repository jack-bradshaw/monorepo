package io.jackbradshaw.jockstrap.model.elements

import kotlinx.coroutines.flow.Flow
import io.jackbradshaw.jockstrap.model.frames.Hostable
import io.jackbradshaw.jockstrap.model.frames.Restorable
import io.jackbradshaw.jockstrap.model.frames.Simulatable

import io.jackbradshaw.jockstrap.model.frames.Placeable
import io.jackbradshaw.jockstrap.physics.Placement
import io.jackbradshaw.klu.flow.BinaryDeltaFlow

interface Entity : Hostable, Placeable, Restorable<EntitySnapshot> {
  val id: EntityId

  fun contents(): Set<Entity>
  fun contentFlow(): BinaryDeltaFlow<Entity>

  fun exports(): Set<Component<*>>
  fun exportFlow(): BinaryDeltaFlow<Component<*>>
}