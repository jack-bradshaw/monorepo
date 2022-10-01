package io.jackbradshaw.otter.structure.controllers

import io.jackbradshaw.klu.flow.BinaryDeltaFlow
import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.structure.frames.Hostable

interface Integration : Hostable<Item> {

  val id: IntegrationId

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