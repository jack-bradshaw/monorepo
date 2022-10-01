package io.jackbradshaw.klu.collections

import java.util.*

/**
 * A dual buffer containing items of type [T].
 *
 * This can be for batch processing where a stream of values needs to be buffered and processed as a
 * batch while new entries continue to be produced for processing in the next batch. For example:
 *
 * // In one thread fun accept(someItem: Item) { buffer.getActive().add(someItem) }
 *
 * // In another thread fun process() { buffer.switch() buffer.getInactive().forEach { /* process it
 * */ } }
 *
 * See [NiceDoubleListBuffer] for a simple implementation.
 */
interface DoubleListBuffer<T> {
  /** Gets the current active list. */
  suspend fun getActive(): LinkedList<T>

  /** Gets the current inactive list. */
  suspend fun getInactive(): LinkedList<T>

  /** Switches the active list and the inactive list atomically. */
  suspend fun switch()
}
