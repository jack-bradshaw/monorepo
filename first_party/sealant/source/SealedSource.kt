package com.jackbradshaw.sealant.source

import com.jackbradshaw.sealant.hub.SealedHub

/**
 * A [SealedHub] that can has no external upstream, and instead emits directly into its sessions via
 * [emit].
 */
interface SealedSource<T> : SealedHub<T> {
  /** Emits [value] to all existing sessions. */
  suspend fun emit(value: T)

  /** Creates new instances of [SealedSource]. */
  interface Factory {
    /** Creates a new instance of [SealedSource]. */
    suspend fun <T> create(): SealedSource<T>
  }
}
