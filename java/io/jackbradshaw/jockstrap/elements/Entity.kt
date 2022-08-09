package io.jackbradshaw.jockstrap.elements

import kotlinx.coroutines.flow.Flow
import io.jackbradshaw.jockstrap.frames.Hostable
import io.jackbradshaw.jockstrap.frames.Restorable
import io.jackbradshaw.jockstrap.frames.Simulatable

import io.jackbradshaw.jockstrap.frames.Placeable
import io.jackbradshaw.jockstrap.physics.Placement
import io.jackbradshaw.klu.flow.BinaryDeltaFlow

interface Entity : Hostable, Placeable, Restorable<EntitySnapshot> {
  val id: EntityId

  fun contents(): Set<Entity>
  fun contentFlow(): BinaryDeltaFlow<Entity>

  fun exports(): Set<Component<*>>
  fun exportFlow(): BinaryDeltaFlow<Component<*>>
}