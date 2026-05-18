package com.jackbradshaw.closet.resourcemanager.set

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.suspending.SuspendableClosable

/**
 * A dynamic set of [ObservableClosable]s.
 *
 * All closables in the set are closed when the set closes, and [close] remains suspended until
 * every item in the set has reached its final closed state. Items can be added and removed at any
 * time before the set begins closing; however, attempting to register an already closed resource
 * will throw an exception. Items are not closed when deregistered, and items are deregistered
 * automatically when they are closed externally. After the set begins closing its functions cannot
 * be used.
 *
 * While resources are a automatically deregistered when closed, race conditions could close
 * resources as they are being returned; therefore, callers should not assume returned resources are
 * necessarily open.
 *
 * The mutator/accessor functions of the set are thread-safe, meaning concurrent access is safely
 * arbitrated, and calls will suspend until the set is able to safely process them. The
 * [exclusiveAccess] function provides an isolated session that ensures a sequence of operations can
 * be executed without interleaving from external callers. After the set begins closing, new calls
 * to its functions will fail, and suspended calls will resume without effect.
 */
interface ResourceSet<V : ObservableClosable> : ObservableClosable {

  /**
   * Returns a read-only set containing all registered resources. The set is immutable and is not
   * updated when the underlying [ResourceSet] changes.
   */
  suspend fun getAll(): Set<V>

  /**
   * Registers [resource].
   *
   * Returns true if the resource was added, false if it was already present.
   */
  suspend fun add(resource: V): Boolean

  /** Deregisters all resources. */
  suspend fun clear()

  /** Returns the number of registered resources. */
  suspend fun size(): Int

  /** Returns true if no resources are registered, false otherwise. */
  suspend fun isEmpty(): Boolean

  /** Returns true if [resource] is registered. */
  suspend fun contains(resource: V): Boolean

  /** Deregisters the resource and returns true if it was present. */
  suspend fun remove(resource: V): Boolean

  /**
   * Suspends until exclusive access to the set can be guaranteed, then invokes [block] with an
   * operator that acts on this set. All other calls to the set suspend while [block] is running.
   *
   * WARNING: The `operator` provided to [block] provides an isolated session for the set while
   * [block] runs, allowing multiple operations to occur sequentially without external interleaving,
   * but it is not thread-safe, and concurrent calls to its functions results in undefined behavior.
   *
   * WARNING: The provided `operator` is confined to this session. It cannot be used after [block]
   * exits.
   *
   * WARNING: The `operator` passed to [block] is the only reliable way to interact with the set
   * while [block] is running as all other top-level functions suspend.
   */
  suspend fun <R> exclusiveAccess(block: suspend (operator: Operator<V>) -> R): R?

  /**
   * Performs access/mutation operations on a specific [ResourceSet].
   *
   * The closure and concurrency behaviours match [ResourceSet].
   */
  interface Operator<V> : SuspendableClosable {

    /**
     * Returns a read-only set containing all registered resources. The set is immutable and is not
     * updated when the underlying [ResourceSet] changes.
     */
    suspend fun getAll(): Set<V>

    /**
     * Registers [resource].
     *
     * Returns true if the resource was added, false if it was already present.
     */
    suspend fun add(resource: V): Boolean

    /** Deregisters the resource and returns true if it was removed. */
    suspend fun remove(resource: V): Boolean

    /** Deregisters all resources. */
    suspend fun clear()

    /** Returns the number of registered resources. */
    suspend fun size(): Int

    /** Returns true if no resources are registered, false otherwise. */
    suspend fun isEmpty(): Boolean

    /** Returns true if [resource] is registered. */
    suspend fun contains(resource: V): Boolean
  }

  /** Creates instances of [ResourceSet]. */
  interface Factory {
    /** Creates a new instance of [ResourceSet]. */
    suspend fun <V : ObservableClosable> createResourceSet(): ResourceSet<V>
  }
}
