package io.jackbradshaw.jockstrap.structure.controllers

import io.jackbradshaw.jockstrap.engine.Engine
import io.jackbradshaw.jockstrap.structure.frames.Hostable
import io.jackbradshaw.jockstrap.structure.frames.Placeable
import io.jackbradshaw.jockstrap.structure.frames.Playable
import io.jackbradshaw.jockstrap.structure.frames.Restorable
import io.jackbradshaw.klu.flow.BinaryDeltaFlow

interface Primitive : Hostable<Item>, Playable, Placeable {

  val id: PrimitiveId

  fun engineElements(): BinaryDeltaFlow<Any>

  /**
   * Registers this primitive with the game engine.
   */
  suspend fun registerWith(engine: Engine)

  /**
   * Removes the registration for this primitive, essentially undoing the effect of [registerWith].
   */
  suspend fun unregisterFrom(engine: Engine)
}