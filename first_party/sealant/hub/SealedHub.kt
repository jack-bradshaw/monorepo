package com.jackbradshaw.sealant.hub

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.sealant.session.SealedSession
import kotlinx.coroutines.flow.Flow

/**
 * Transforms a single upstream flow into a series of isolated [SealedSession]s and tracks them to
 * ensure cascading resource closure.
 *
 * Hub provides the following guarantees to each session:
 * 1. Zero-Drop: The instant a session is created, it receives all future emissions from the hub
 *    without exception. The hub passes every received value to every open session without leakage.
 * 2. Zero-Replay: Emissions from the upstream flow are not replayed to any session created after
 *    the emission is received at the hub.
 * 3. Zero-Buffering: Emissions from the upstream flow are forwarded to existing sessions without
 *    buffering or artificial delay.
 *
 * No guarantees are made about the ordering of emissions when multiple sessions exist, meaning
 * sessions may receive emissions simultaneously or sequentially, depending on the implementation,
 * and slow sessions may not necessarily delay emission to faster sessions.
 *
 * All sessions created by a hub are closed when the hub is closed, and no further sessions can be
 * created after closure, but closing a session does not close the associated hub. Since closing a
 * session terminates its flow, closing this hub implicitly finishes the flows of all associated
 * sessions.
 *
 * This contract ensure each [SealedSession] produced by a hub can operate reliably. Since the hub
 * forwards all values to every open session immediately without delay, drop, or replay, every
 * session can guarantee that once it starts collecting, it will receive all new values from the
 * upstream flow, and can reliably declare `isConnectedToSource` to be true.
 */
interface SealedHub<T> : ObservableClosable {

  /**
   * Creates a new session that receives emissions from the upstream flow of this hub.
   *
   * A new instance is provided on every call. Fails if this hub is closed when called.
   */
  suspend fun createSession(): SealedSession<T>

  /**
   * Creates a new session that applies [transformation] to the upstream flow before exposing it.
   */
  suspend fun <R> createSession(transformation: suspend (Flow<T>) -> Flow<R>): SealedSession<R>

  /** Produces [SealedHub] instances. */
  interface Factory {

    /** Creates a new [SealedHub] backed by [upstreamFlow]. */
    suspend fun <T> create(upstreamFlow: Flow<T>): SealedHub<T>

    /**
     * Creates a new [SealedHub] backed by [upstreamFlow].
     *
     * The provided hub closes automatically when [upstreamFlow] closes, and closing the returned
     * hub stops collection from the [upstreamFlow].
     */
    suspend fun <T> createWithAutomaticClosure(upstreamFlow: SealedSession<T>): SealedHub<T>
  }
}
