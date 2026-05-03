package com.jackbradshaw.sluice

import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.flow.Flow

/**
 * A deterministic flow gate that guarantees the safe routing and observation of hot, unbuffered sources.
 *
 * A Sluice wraps an underlying hot flow and provides an intermediate pipe ([flow]) to downstream 
 * consumers. It exposes an [awaitConnection] primitive that suspends until the downstream consumer 
 * has actively attached to the intermediate pipe, guaranteeing that the end-to-end circuit is physically
 * wired before the upstream source emits.
 *
 * Implements [ObservableClosable] so that if a downstream consumer abandons the pipe or crashes, 
 * calling [close] will instantly terminate the internal routing coroutine and free the memory.
 */
interface Sluice<T> : ObservableClosable {

  /** The intermediate pipe carrying the data from the hot source. */
  val flow: Flow<T>

  /** Suspends until at least one downstream consumer has actively attached to [flow]. */
  suspend fun awaitConnection()
}
