package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.observable.ObservableClosable

/**
 * Manages a collection of [ObservableClosable] resources.
 *
 * Resources can be registered, deregistered, and retrieved from the manager, and when the manager
 * itself is closed, it closes all of its registered resources. The manager is thread-safe and all
 * calls suspend until state can be safely mutated. The [exclusiveAccess] function provides
 * mutex-like access for cases where multiple calls are required in succession without yielding
 * access to other threads. It effectively holds a lock on the manager and causes all other calls to
 * suspend until it exits.
 *
 * Caveats:
 * 1. The manager does not close resources when they are deregistered.
 * 2. Closing the manager with [close] causes all resources to close; furthermore, the manager's
 *    [close] function will block until every managed resource has completed closure. The
 *    [closeSelfOnly] function is available to close the manager without closing the managed
 *    resources.
 * 3. Attempting to register an already closed resource results in an exception.
 * 4. Attempting to call any function after the manager has been closed results in an exception
 *    (excluding the [close] function, which can be called repeatedly).
 * 5. Resources are automatically deregistered when closed externally (i.e. closed by means
 *    unrelated to the manager), so the various functions that return a resource should never return
 *    a closed resource; however, an unavoidable race condition exists: a resource could be
 *    externally closed by another thread after being checked by the manager but before being
 *    returned; therefore, callers should verify the status of returned resources when a particular
 *    state is strictly necessary, and not assume returned resources are open. Such race conditions
 *    are handled gracefully within the manager and never cause failures.
 */
interface ResourceManager<K, V : ObservableClosable> : ObservableClosable {

  /**
   * Returns the registered resource associated with [key], or null if none exists.
   *
   * Suspends until exclusive access to the manager can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed.
   */
  suspend fun get(key: K): V?

  /**
   * Registers [resource] and associates it with [key].
   *
   * If another resource is already associated with [key], it is deregistered and returned, but not
   * closed.
   *
   * Suspends until exclusive access to the manager can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed. Throws [IllegalStateException] if [resource]
   * is closed.
   */
  suspend fun put(key: K, resource: V): V?

  /**
   * Returns the registered resource associated with [key].
   *
   * If none exists, evaluates [newValueProvider], registers the result (associating it with [key]),
   * and returns it.
   *
   * Suspends until exclusive access to the manager can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed. Throws [IllegalStateException] if the
   * resource returned by [newValueProvider] is closed.
   */
  suspend fun getOrPut(key: K, newValueProvider: () -> V): V

  /**
   * Deregisters all resources and returns them.
   *
   * Suspends until exclusive access to the manager can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed.
   */
  suspend fun clear(): List<V>

  /**
   * Returns the number of registered resources.
   *
   * Suspends until exclusive access to the underlying state can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed.
   */
  suspend fun size(): Int

  /**
   * Returns true if no resources are registered.
   *
   * Suspends until exclusive access to the underlying state can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed.
   */
  suspend fun isEmpty(): Boolean

  /**
   * Returns true if a resource is associated with [key].
   *
   * Suspends until exclusive access to the underlying state can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed.
   */
  suspend fun containsKey(key: K): Boolean

  /**
   * Returns true if [resource] is registered.
   *
   * Suspends until exclusive access to the underlying state can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed.
   */
  suspend fun containsValue(resource: V): Boolean

  /**
   * Deregisters the resource associated with [key] and returns it, or returns null if none exists.
   *
   * Suspends until exclusive access to the manager can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed.
   */
  suspend fun remove(key: K): V?

  /**
   * Waits for exclusive access to the manager then evaluates [block].
   *
   * The operator provided to [block] has exclusive access to the manager during function execution
   * but must be discarded after the function returns, as usage outside the function call results in
   * an error. Furthermore, [block] must not call any of the accessor/mutator functions on
   * [ResourceManager] as they will suspend until [block] returns, thus creating a deadlock.
   *
   * Suspends until exclusive access to the manager can be guaranteed, and blocks all other
   * accessor/mutator functions until [block] completes. Throws [IllegalStateException] if this
   * manager is closed.
   *
   * WARNING: The operator has exclusive access to the manager, but not concurrent access, so
   * concurrent calls to exclusive access functions will be executed sequentially, and can deadlock
   * if not orchestrated accordingly. This ensures exclusive access cannot break the internal state
   * of the resource manager through concurrent access.
   */
  suspend fun <R> exclusiveAccess(block: suspend (operator: Operator<K, V>) -> R): R

  /**
   * Closes this manager without closing managed resources, but otherwise follows the behaviour of
   * [close] exactly.
   */
  fun closeSelfOnly()

  /**
   * Performs access/mutation operations on a specific [ResourceManager].
   *
   * Follows the same behaviours and access logic as [ResourceManager] with regard to closure and
   * concurrent access.
   */
  interface Operator<K, V> {
    /**
     * Returns the registered resource associated with [key], or null if none exists.
     *
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     */
    suspend fun get(key: K): V?

    /**
     * Registers [resource] and associates it with [key].
     *
     * If another resource is already associated with [key], it is deregistered and returned, but
     * not closed.
     *
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed. Throws [IllegalStateException] if
     * [resource] is closed.
     */
    suspend fun put(key: K, resource: V): V?

    /**
     * Returns the registered resource associated with [key].
     *
     * If none exists, evaluates [newValueProvider], registers the result (associating it with
     * [key]), and returns it.
     *
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed. Throws [IllegalStateException] if the
     * resource returned by [newValueProvider] is closed.
     */
    suspend fun getOrPut(key: K, newValueProvider: () -> V): V

    /**
     * Deregisters the resource associated with [key] and returns it, or returns null if none
     * exists.
     *
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     */
    suspend fun remove(key: K): V?

    /**
     * Deregisters all resources and returns them.
     *
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     */
    suspend fun clear(): List<V>

    /**
     * Returns the number of registered resources.
     *
     * Suspends until exclusive access to the underlying state can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     */
    suspend fun size(): Int

    /**
     * Returns true if no resources are registered.
     *
     * Suspends until exclusive access to the underlying state can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     */
    suspend fun isEmpty(): Boolean

    /**
     * Returns true if a resource is associated with [key].
     *
     * Suspends until exclusive access to the underlying state can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     */
    suspend fun containsKey(key: K): Boolean

    /**
     * Returns true if [resource] is registered.
     *
     * Suspends until exclusive access to the underlying state can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     */
    suspend fun containsValue(resource: V): Boolean
  }

  /** Creates instances of [ResourceManager]. */
  interface Factory {
    /** Creates a new instance of [ResourceManager]. */
    fun <K, V : ObservableClosable> createResourceManager(): ResourceManager<K, V>
  }
}
