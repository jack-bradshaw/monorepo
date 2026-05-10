package com.jackbradshaw.sealant.funnel

import com.jackbradshaw.sealant.hub.Hub
import com.jackbradshaw.sealant.pipe.Pipe

/**
 * An intermediate hub that merges multiple upstream pipes of the SAME type into a single flow.
 * (Analogous to Flow.merge() rather than Flow.combine()).
 */
interface Funnel<T> : Hub<T> {
  interface Factory {
    /** 
     * Connects a new Hub that indiscriminately merges multiple identical upstream pipes. 
     * The resulting hub's transitive connection state is dependent on ALL upstream pipes 
     * being transitively connected.
     */
    fun <T> create(pipes: List<Pipe<T>>): Funnel<T>
  }
}
