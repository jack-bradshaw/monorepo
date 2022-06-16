package io.matthewbradshaw.octavius.lifecycle

/**
 * Something which can be paused and resumed.
 *
 * A Suspendable in the resume state should respond to physical interactions and game dynamics (e.g. firing triggers,
 * taking damage, falling due to gravity, and playing animations etc). A Suspendable in the paused state should not
 * respond to any physical interactions or game dynamics. Each Suspendable should begin in the paused state.
 *
 * This class has nothing to do with suspend functions, although it does use them.
 */
interface Suspendable {
  /**
   * Moves this object to the resumed state. All physical interactions and game dynamics should engage.
   */
  suspend fun resume()

  /**
   * Moves this object to the pause state. All physical interactions and game dynamics should disengage.
   */
  suspend fun pause()
}