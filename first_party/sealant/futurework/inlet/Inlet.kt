package com.jackbradshaw.sealant.inlet

import com.jackbradshaw.sealant.hub.Hub
import kotlinx.coroutines.flow.Flow

/**
 * The entry point where the unmanaged, raw external flow enters the system.
 */
interface Inlet<T> : Hub<T> {
  interface Factory {
    /** Starts a new pipeline exclusively from a raw, unmanaged [Flow]. */
    fun <T> create(externalSource: Flow<T>): Inlet<T>
  }
}
