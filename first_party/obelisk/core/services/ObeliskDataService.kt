package com.jackbradshaw.obelisk.core.services

import com.jackbradshaw.sealant.hub.SealedHub

/** Exposes a continuous stream of targets and provides a mechanism to publish generated results. */
interface ObeliskDataService<A, R> {

  /**
   * Returns a deterministic flow control gate ([SealedHub]) that provides the continuous stream of
   * parsed targets. Downstream must call `open()` before allowing the framework to advance.
   */
  fun observeTargets(): SealedHub<A>

  /** Publishes a generated [result] associated with the given [anchor]. */
  suspend fun publish(result: R, anchors: Set<A>)
}
