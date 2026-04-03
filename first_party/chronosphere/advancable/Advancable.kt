package com.jackbradshaw.chronosphere.advancable

/**
 * A functional interface for any system that can be advanced in millisecond increments.
 *
 * The system may advance on its own independent of calls to [advanceBy], and implementing this
 * interface provides no guarantee that a system will not change independently of [advanceBy].
 */
interface Advancable {
  /** Advances the system by one millisecond. */
  fun advanceBy(millis: Int)
}
