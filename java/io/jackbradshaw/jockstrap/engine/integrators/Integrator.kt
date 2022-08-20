package io.jackbradshaw.jockstrap.engine.integrators

import io.jackbradshaw.jockstrap.engine.Engine

/**
 * Integrates an arbitrary game element into a game engine.
 */
interface Integrator<T> {
  /**
   * Registers [element] with the game engine.
   */
  suspend fun integrate(element: T)

  /**
   * Deregisters [element] from the game engine.
   */
  suspend fun disintegrate(element: T)
}