package io.matthewbradshaw.octavius.lifecycle

/**
 * Something which can be prepared.
 *
 * Consider a scene which consists of a room containing boxes, where the boxes are generated in random locations when
 * the level is first loaded. In rare cases the boxes will spawn in their final location, but in many cases it will be
 * necessary to run a physics engine to let the scene play out. Ideally the player should not see this transition since
 * it would break the immersion, so the scene needs to be prepared ahead of time. Preparable gives scenes this
 * opportunity.
 */
interface Preparable {
  /**
   * Prepares the scene and suspends until preparations are complete. When this function completes, the scene should be
   * ready for the player.
   */
  suspend fun prepare()
}