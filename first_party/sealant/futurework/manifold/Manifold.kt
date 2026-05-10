package com.jackbradshaw.sealant.manifold

import com.jackbradshaw.sealant.hub.Hub
import com.jackbradshaw.sealant.pipe.Pipe

/**
 * An intermediate hub that fans out a [Pipe] into multiple child pipes.
 */
interface Manifold<T> : Hub<T> {
  interface Factory {
    /** Connects a new Hub exclusively to an upstream [Pipe]. */
    fun <T> create(upstreamPipe: Pipe<T>): Manifold<T>
  }
}
