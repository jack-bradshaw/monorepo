package com.jackbradshaw.sealant.junction

import com.jackbradshaw.sealant.hub.Hub
import com.jackbradshaw.sealant.pipe.Pipe
import kotlinx.coroutines.flow.Flow

/**
 * An intermediate hub that merges multiple upstream pipes into a single flow, preserving 
 * the transitive connection integrity of all upstream branches.
 */
interface Junction<T> : Hub<T> {
  interface Factory {
    /** 
     * Connects a new Hub that merges two upstream pipes. The resulting hub's transitive connection
     * state is dependent on BOTH upstream pipes being transitively connected.
     */
    fun <A, B, T> create(
        pipeA: Pipe<A>, 
        pipeB: Pipe<B>, 
        combinator: suspend (Flow<A>, Flow<B>) -> Flow<T>
    ): Junction<T>
  }
}
