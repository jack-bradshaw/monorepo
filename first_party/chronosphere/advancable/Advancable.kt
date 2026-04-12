package com.jackbradshaw.chronosphere.advancable

/**
 * A functional interface for any system that can be manually advanced in millisecond increments.
 *
 * Advancement is defined as the internal clock backing the system moving forward by a fixed
 * duration; however, the exact method of advancement is not specified by the interface, and
 * implementations are free to advance in any way that ensures the final time is reached with
 * millisecond precision. Implementations may jump forward instantaneously to the final time, step
 * through millisecond increments until the final time is reached, or use any other system that
 * achieves the same result. This definition avoids limiting implementations or constraining what
 * can be [Advancable].
 *
 * The system may advance independently of calls to [advanceBy], and implementing this interface
 * provides no guarantee that the system will remain at a specific instant after [advanceBy]
 * returns. This is a practical concern in multithreaded systems because multiple threads may
 * advance a resource independently.
 */
fun interface Advancable {

  /** Advances the system by [millis] milliseconds. */
  fun advanceBy(millis: Int)
}
