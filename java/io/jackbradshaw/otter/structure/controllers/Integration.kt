package io.jackbradshaw.otter.structure.controllers

import io.jackbradshaw.otter.engine.Engine
import io.jackbradshaw.otter.structure.controllers.Item
import io.jackbradshaw.otter.structure.frames.Hostable
import io.jackbradshaw.klu.flow.BinaryDeltaFlow

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