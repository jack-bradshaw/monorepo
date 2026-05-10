package com.jackbradshaw.obelisk.core.services

import com.jackbradshaw.sealant.flow.SealedFlow

/** Exposes a continuous stream of targets and provides a mechanism to publish generated results. */
interface ObeliskDataService<A, R> {

  /**
   * Returns a deterministic flow control gate ([SealedHub]) that provides the continuous stream of
   * parsed targets. Downstream must call `open()` before allowing the framework to advance.
   */
  suspend fun observeTargets(): SealedFlow<A>

  /** Publishes a generated [result] associated with the given [anchor]. */
  suspend fun publish(result: R, anchors: Set<A>)
}
