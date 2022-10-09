package io.jackbradshaw.otter.scene.primitives

/** Integrates an arbitrary game element into a game engine. */
interface EngineIntegrator<T> {
  /** Registers [element] with the game engine. */
  suspend fun integrate(element: T)

  /** Deregisters [element] from the game engine. */
  suspend fun disintegrate(element: T)
}
