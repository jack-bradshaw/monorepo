package com.jackbradshaw.sealant.flow

import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * A [flow] and a signal indicating whether it is being collected.
 *
 * The [flow] adheres to the following guarantees:
 * 1. Zero Replay: Emissions from the underlying flow are not replayed to the collector.
 * 2. Zero Buffering: Emissions from the underlying flow are forwarded to the collector without
 *    buffering or artificial delay.
 * 3. The Zero-Drop Guarantee: The session passes every value received from the underlying flow to
 *    its collector without leakage while [isConnectedToSource] is true.
 *
 * The [isConnectedToSource] flag is true precisely while the [flow] is being collected, meaning:
 * 1. The value begins as `false`
 * 2. The value flips to `true` when collection begins.
 * 3. The value flips to `false` when collection ends.
 *
 * Collection is defined by three operator types:
 * - Terminal operators count as collection (e.g. `collect`).
 * - Active intermediate operators that collect the flow internally and re-emit it count as
 *   collection (e.g. `buffered`).
 * - Passive intermediate operators that do not collect the flow do not count as collection (e.g.
 *   `map`).
 *
 * Connecting a terminal operator or an active operator to the flow (directly or transitively) is
 * considered connected.
 */
interface SealedFlow<T> : ObservableClosable {

  /**
   * The singular flow of this session.
   *
   * Must only be collected once. Fails if collected repeatedly, even if the previous collector has
   * disconnected.
   */
  val flow: Flow<T>

  /** Whether [flow] is actively being collected. */
  val isConnectedToSource: StateFlow<Boolean>

  /** Suspends until [isConnectedToSource] becomes true. */
  suspend fun awaitConnectionToSource()
}
