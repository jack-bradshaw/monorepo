package com.jackbradshaw.sealant.hub

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.sealant.flow.SealedFlow
import kotlinx.coroutines.flow.Flow

/**
 * Transforms a single underlying flow into a series of isolated [SealedFlow]s and tracks them
 * to ensure adequate resource management.
 *
 * Hub provides the following guarantees:
 * 1. Session Generation: Every call to [createFlow] produces a distinctly new session instance.
 * 2. The Zero-Drop Guarantee: The instant a session is created, it receives all future emissions
 *    from the hub without exception. The hub passes every received value to every open session
 *    without leakage.
 * 3. Zero Replay: Emissions from the underlying flow are not replayed to any session created after
 *    the emission is received at the hub.
 * 4. Zero Buffering: Emissions from the underlying flow are forwarded to existing sessions without
 *    buffering or artificial delay.
 * 5. Closure Cascading: All associated sessions are closed when this hub is closed, and no further
 *    sessions can be created. Since closing a session terminates its flow, closing this hub
 *    implicitly finishes the flows of all associated sessions, and no more emissions are forwarded.
 *    Closing a session does not close the hub.
 *
 * No guarantees are made about the ordering of emissions or the parallelism of emissions, meaning
 * sessions may receive emissions simultaneously or sequentially, depending on the implementation.
 *
 * When combined with the [SealedFlow] contract, these behaviours reliably produce a sealed
 * flow. Since the hub forwards all values to every open session immediately without delay or
 * replay, sessions can guarantee that once they start collecting, they will receive all values from
 * the upstream flow, and can therefore reliably declare they are connected to the source with
 * `isConnectedToHub`.
 */
interface SealedHub<T> : ObservableClosable {

  /**
   * Creates a new session that receives emissions from the underlying flow of this hub.
   *
   * A new instance is provided on every call.
   *
   * Fails if this hub is closed when called.
   */
  suspend fun createFlow(): SealedFlow<T>

  /**
   * Creates a new flow that applies [transformation] to the underlying flow before exposing it.
   */
  suspend fun <R> createFlow(transformation: suspend (Flow<T>) -> Flow<R>): SealedFlow<R>

  /** Produces [SealedHub] instances. */
  interface Factory {
    /** Creates a new [SealedHub] backed by [underlyingFlow]. */
    fun <T> create(underlyingFlow: Flow<T>): SealedHub<T>

    /**
     * Creates a new [SealedHub] backed by [underlyingFlow] which closes automatically when
     * [underlyingFlow] closes.
     *
     * Closing the returned hub does NOT close [underlyingFlow].
     */
    fun <T> createWithAutomaticClosure(underlyingFlow: SealedFlow<T>): SealedHub<T>
  }
}
