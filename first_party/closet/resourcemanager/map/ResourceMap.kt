package com.jackbradshaw.closet.resourcemanager.map

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.suspending.SuspendableClosable

/**
 * A dynamic map of [ObservableClosable] resources.
 *
 * All closables in the map are closed when the map closes, and [close] remains suspended until
 * every item in the map has reached its final closed state. Items can be added and removed at any
 * time before the map begins closing; however, attempting to register an already closed resource
 * will throw an exception. Items are not closed when deregistered, but items are autmatically
 * deregistered by the map when closed externally. Race conditions between retrieval and automatic
 * closure can lead to closed resources being returned, so callers should not assume retrieved
 * resources are open simply because they were sourced from the map.
 *
 * The mutator/accessor functions of the map are thread-safe, meaning concurrent access is safely
 * arbitrated, and calls will suspend until the map is able to safety process them. The
 * [exclusiveAccess] function provides an isolated session that ensures a sequence of operations can
 * be executed without interleaving from other callers. After the map begins closing, new calls to
 * its functions will fail, and existing suspended calls will resume without effect.
 */
interface ResourceMap<K, V : ObservableClosable> : ObservableClosable {

  /** Returns the registered resource associated with [key], or null if none exists. */
  suspend fun get(key: K): V?

  /**
   * Returns a read-only map containing all registered resources and their associated keys. The map
   * is immutable and is not updated when the underlying [ResourceMap] changes.
   */
  suspend fun getAll(): Map<K, V>

  /**
   * Registers [resource] and associates it with [key].
   *
   * Returns the previously associated resource, or null if none was registered.
   */
  suspend fun put(key: K, resource: V): V?

  /**
   * The behavior of this function depends on the value associated with [key]. When there is no
   * associated value, [newValueProvider] is invoked, the value is registered and associated with
   * the key, and then returned. Otherwise, [newValueProvider] is not invoked, and the existing
   * value is returned.
   */
  suspend fun getOrPut(key: K, newValueProvider: () -> V): V

  /** Deregisters all resources. */
  suspend fun clear()

  /** Returns the number of registered resources. */
  suspend fun size(): Int

  /** Returns true if no resources are registered, false otherwise. */
  suspend fun isEmpty(): Boolean

  /** Returns true if a resource is associated with [key]. */
  suspend fun containsKey(key: K): Boolean

  /** Returns true if [resource] is registered. */
  suspend fun containsValue(resource: V): Boolean

  /**
   * Deregisters the resource associated with [key] and returns it, or returns null if none exists.
   */
  suspend fun remove(key: K): V?

  /**
   * Suspends until exclusive access to the map can be guaranteed, then invokes [block] with an
   * operator that acts on this map. All other calls to the map suspend while [block] is running.
   *
   * WARNING: The `operator` provided to [block] provides an isolated session for the map while
   * [block] runs, allowing multiple operations to occur sequentially without external interleaving,
   * but it is not thread-safe, and concurrent calls to its functions results in undefined behavior.
   *
   * WARNING: The provided `operator` is confined to this session. It cannot be used after [block]
   * exits.
   *
   * WARNING: The `operator` passed to [block] is the only reliable way to interact with the map
   * while [block] is running as all other top-level functions suspend.
   */
  suspend fun <R> exclusiveAccess(block: suspend (operator: Operator<K, V>) -> R): R?

  /**
   * Performs access/mutation operations on a specific [ResourceMap].
   *
   * The closure and concurrency behaviours match [ResourceMap].
   */
  interface Operator<K, V> : SuspendableClosable {
    /** Returns the registered resource associated with [key], or null if none exists. */
    suspend fun get(key: K): V?

    /**
     * Returns a read-only map containing all registered resources and their associated keys. The
     * map is immutable and is not updated when the underlying [ResourceMap] changes.
     */
    suspend fun getAll(): Map<K, V>

    /**
     * Registers [resource] and associates it with [key].
     *
     * Returns the previously associated resource, or null if none was registered.
     */
    suspend fun put(key: K, resource: V): V?

    /**
     * The behavior of this function depends on the value associated with [key]. When there is no
     * associated value, [newValueProvider] is invoked, the value is registered and associated with
     * the key, and then returned. Otherwise, [newValueProvider] is not invoked, and the existing
     * value is returned.
     */
    suspend fun getOrPut(key: K, newValueProvider: () -> V): V

    /**
     * Deregisters the resource associated with [key] and returns it, or returns null if none
     * exists.
     */
    suspend fun remove(key: K): V?

    /** Deregisters all resources. */
    suspend fun clear()

    /** Returns the number of registered resources. */
    suspend fun size(): Int

    /** Returns true if no resources are registered, false otherwise. */
    suspend fun isEmpty(): Boolean

    /** Returns true if a resource is associated with [key]. */
    suspend fun containsKey(key: K): Boolean

    /** Returns true if [resource] is registered. */
    suspend fun containsValue(resource: V): Boolean
  }

  /** Creates instances of [ResourceMap]. */
  interface Factory {
    /** Creates a new instance of [ResourceMap]. */
    suspend fun <K, V : ObservableClosable> createResourceMap(): ResourceMap<K, V>
  }
}
