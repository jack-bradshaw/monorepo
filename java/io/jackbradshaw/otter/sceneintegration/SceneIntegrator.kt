package io.jackbradshaw.otter.engine.sceneintegration

/**
 * Integrates an arbitrary game element into a game engine.
 */
interface SceneIntegrator<T> {
  /**
   * Registers [element] with the game engine.
   */
  suspend fun integrate(element: T)

  /**
   * Deregisters [element] from the game engine.
   */
  suspend fun disintegrate(element: T)
}