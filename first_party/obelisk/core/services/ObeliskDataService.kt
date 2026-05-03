package com.jackbradshaw.obelisk.core.services

import kotlinx.coroutines.flow.Flow

import com.jackbradshaw.sluice.Sluice

/**
 * Exposes a continuous stream of targets and provides a mechanism to publish generated results.
 */
interface ObeliskDataService<A, R> {

  /** 
   * Returns a deterministic flow control gate ([Sluice]) that provides the continuous stream of 
   * parsed targets. Downstream must call `awaitConnection()` before allowing the framework to advance.
   */
  fun createSluice(): Sluice<A>

  /**
   * Publishes a generated [result] associated with the given [anchor].
   */
  suspend fun publish(result: R, anchors: Set<A>)
}
