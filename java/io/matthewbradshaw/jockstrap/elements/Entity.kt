package io.matthewbradshaw.jockstrap.elements

import kotlinx.coroutines.flow.Flow
import io.matthewbradshaw.jockstrap.frames.Hostable
import io.matthewbradshaw.jockstrap.frames.Restorable
import io.matthewbradshaw.jockstrap.frames.Simulatable

import io.matthewbradshaw.jockstrap.frames.Placeable
import io.matthewbradshaw.jockstrap.physics.Placement
import io.matthewbradshaw.klu.flow.BinaryDeltaFlow

interface Entity : Hostable, Placeable, Restorable<EntitySnapshot> {
  val id: EntityId

  fun contents(): Set<Entity>
  fun contentFlow(): BinaryDeltaFlow<Entity>

  fun exports(): Set<Component<*>>
  fun exportFlow(): BinaryDeltaFlow<Component<*>>
}