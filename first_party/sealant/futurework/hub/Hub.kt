package com.jackbradshaw.sealant.hub

import com.jackbradshaw.sealant.connectable.Connectable
import com.jackbradshaw.sealant.pipe.Pipe
import com.jackbradshaw.sealant.outlet.Outlet
import kotlinx.coroutines.flow.Flow

/**
 * Common behavior for any hub capable of spawning pipes.
 */
interface Hub<T> : Connectable {
  fun <R> createPipe(transformation: suspend (Flow<T>) -> Flow<R> = { it }): Pipe<R>
  fun <R> createOutlet(transformation: suspend (Flow<T>) -> Flow<R> = { it }): Outlet<R>
}
