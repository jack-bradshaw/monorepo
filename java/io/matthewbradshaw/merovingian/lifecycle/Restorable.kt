package io.matthewbradshaw.merovingian.lifecycle

import com.google.protobuf.MessageLite

/**
 * Something which can be represented as a state of type [S] and later restored. Restoration may occur across sessions
 * and machines.
 */
interface Restorable<S : MessageLite> {

  /**
   * Restores the object to the provided state. Any two objects with the same state should be functionally identical
   * from the perspective of the consumer.
   */
  suspend fun restore(snapshot: S?)

  /**
   * Creates a static representation of this instance. If the returned state were passed to [restore] in another session
   * or on another machine, the restored object should be functionally identical from the perspective of the consumers.
   */
  suspend fun snapshot(): S?
}