package io.matthewbradshaw.gmonkey.core.ignition

/**
 * Starts the game engine.
 */
interface Ignition {
  /**
   * Starts the game engine now.
   */
  suspend fun go()
}