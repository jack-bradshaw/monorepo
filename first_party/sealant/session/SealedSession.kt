package com.jackbradshaw.sealant.session

import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * A [flow] and a signal indicating whether it is being collected.
 *
 * The [isConnectedToSource] value is `true` only when [flow] is being collected, meaning it begins
 * as `false`, becomes `true` when a collector attaches, and returns to `false` when the collection
 * job is cancelled (or the flow completes). What constitutes collection is consistent with the core
 * flow framework, meaning a flow is considered collected when its `collect` function is called
 * directly or transitively, including by operations such as `buffered` which collect into memory
 * without a downstream collector of their own.
 *
 * The flow must only be collected once, and any attempt to collect again (concurrently or
 * sequentially) will fail.
 */
interface SealedSession<T> : ObservableClosable {

  /**
   * The flow of this session.
   *
   * Must only be collected once. Fails if collected repeatedly, even if the previous collection has
   * been cancelled.
   */
  val flow: Flow<T>

  /** Whether [flow] is being collected. */
  val isConnectedToSource: StateFlow<Boolean>

  /** Suspends until [isConnectedToSource] becomes true. */
  suspend fun awaitConnectionToSource()

  /** Creates instances of [SealedSession]. */
  interface Factory {

    /**
     * Creates a [SealedSession] that represents [source] passed through [transformation]. The
     * transformation is not applied until the resulting flow is collected.
     */
    suspend fun <T, R> create(
        source: Flow<T>,
        transformation: suspend (Flow<T>) -> Flow<R>
    ): SealedSession<R>
  }
}
