package com.jackbradshaw.quinn

import com.jackbradshaw.closet.observable.ObservableClosable

/**
 * A queueing interceptor that safely delegates closures into an isolated, thread-locked execution 
 * loop.
 */
interface Quinn<T> : ObservableClosable {
  /**
   * Invokes [block] in the context of the drain loop, passing in the active resource,
   * and suspending until the block exits or this structure is closed.
   *
   * Throws an [IllegalStateException] if invoked after closure. If closure occurs after this
   * method is invoked but before [block] is evaluated, no exception is thrown, and [block] is
   * discarded without evaluation.
   *
   * WARNING: Using the supplied resource outside [block] is error prone and unsupported.
   * 
   * WARNING: It is unsafe to make calls to this [Quinn] from [block] as implementations are free to
   * use non-reentrant locks in the various functions, and they likely will due to the multithreaded
   * nature of [Quinn].
   */
  suspend fun submit(block: (T) -> Unit)

  /**
   * Identical to [submit], except it returns `false` instead of throwing an [IllegalStateException]
   * if this [Quinn] is closed (return `true` otherwise).
   */
  suspend fun trySubmit(block: (T) -> Unit): Boolean

  /**
   * Processes submitted [blocks].
   * 
   * Block the calling thread until all submitted blocks have been consumed, including any blocks
   * that are submitted during processing. Blocks are always evaluated in the order they are
   * submitted (submission race conditions excepted).
   *  
   * This function is not suspending to ensure execution occurs in the calling thread, as is the 
   * purpose of Quinn (shifting execution of a thread-bound resource to the owning thread).
   * 
   * Exits immediately after completing the presently executing block if closure occurs during
   * processing. Exits immediately if called after closure with no effect (does not throw an
   * exception because there is nothing to drain).
   */
  suspend fun drain(resource: T)

  /** Creates instances of [Quinn]. */
  interface Factory {
    /** Creates a new instance of [Quinn]. */
    fun <T> createQuinn(): Quinn<T>
  }
}