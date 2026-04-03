package com.jackbradshaw.coroutines.testing.realistic.dispatcher

import com.jackbradshaw.chronosphere.idleable.Idleable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Delay

import kotlinx.coroutines.InternalCoroutinesApi

/** A coroutine dispatcher that can report its idle status. */
@OptIn(InternalCoroutinesApi::class)
abstract class IdleableDispatcher : CoroutineDispatcher(), Idleable, Delay {
  /** Creates instances of [IdleableDispatcher]. */
  interface Factory {
    /** Creates a new instance of [IdleableDispatcher] with [threadCount] threads. A new instance
     * is returned each time. */
    fun create(threadCount: Int): IdleableDispatcher
  }
}
