package com.jackbradshaw.closet.resourcemanager

import kotlinx.coroutines.flow.StateFlow

import com.jackbradshaw.closet.observable.ObservableClosable

/**
 * Manages a collection of [ObservableClosable] resources.
 * 
 * Resources can be registered, deregistered, and retrieved from the manager, and when the manager
 * itself is closed, all of its registered resources are closed. The manager provides various
 * functions for mutation and access, all of which are thread-safe and suspend until such a time as
 * they can be safely evaluated with exclusive access to the manager.
 * 
 * The manager supports three primary groups of operations:
 * 
 * 1. Registration: Associating a resource with this manager so it can inherit the manager's closure.
 * Functions are [put] and [getOrPut].
 * 2. Deregistration: Dissociating a resource from this manager so it no longer inherits the manager's
 * closure. Functions are [remove] and [clear].
 * 3. Access: Reading the state of the manager without modification. Functions are [get], [size],
 * [isEmpty], [containsKey], and [containsValue].
 * 
 * All accessor/mutator functions are thread-safe, meaning calls will suspend until such a time as
 * they can be evaluated safely. The [exclusiveAccess] function is provided for cases where multiple
 * accessor/mutator calls are required in succession without race conditions with other threads. It
 * effectively provides mutex-like access to the manager and causes all other accessor/mutator
 * functions to suspend while in use.
 * 
 * Caveats:
 * 
 * 1. The manager does not close resources when they are deregistered.
 * 2. Closing the manager with [close] causes all resources to close; furthermore, the manager's
 * [close] function will block until every managed resource has reached a terminal state and has
 * terminated its processes. To close the manager without affecting managed resource, the 
 * [closeSelfOnly] function is available.
 * 3. Closing a registered resource externally (i.e. closed by means unrelated to the manager) will cause the manager to automatically deregister it; however,
 * external resources closure can be triggered any time by any thread, so there could be a delay between
 * external closure and automatic deregistration; therefore, functions such as [get], [getOrPut],
 * [remove], and [clear], cannot guarantee the returned value(s) are open, and callers should check their
 * status directly when a particular status is strictly necessary. Such cases are handled gracefully
 * by the manager and do not cause internal failures.
   4. Attempting to register an already closed resource results in an exception.
 * 5. Attempting to call any mutator/accessor function after the manager has been closed results in an exception.
 * 6. All accessor/mutator functions are thread-safe.
 * 7. All calls to accessor/mutator functions suspend while an [exclusiveAccess] block is being evaluated.
 */
interface ResourceManager<K, V : ObservableClosable> : ObservableClosable {

  /** 
   * Returns the registered resource associated with [key], or null if none exsts.
   * 
   * Suspends until exclusive access to the manager can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed.
   * 
   * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
   * unrelated to the manager), and this function checks
   * to ensure the resources are open before returning them; however, an unavoidable race condition
   * exists: A resource could be externally closed after being checked
   * but before being returned; therefore, callers should verify the status of the returned resource
   * when a particular state is strictly necessary, and not assume the returned resource is open.
   */
  suspend fun get(key: K): V?

  /** 
   * Registeres [resource] and associates it with [key]. If another resource is already associated
   * with [key], it is deregistered and returned, but not closed.
   * 
   * Suspends until exclusive access to the manager can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed. Throws [IllegalStateException] if [resource]
   * is closed.
   * 
   * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
   * unrelated to the manager), and this function checks
   * to ensure the resources are open before returning them; however, an unavoidable race condition
   * exists: A resource could be externally closed after being checked
   * but before being returned; therefore, callers should verify the status of the returned resource
   * when a particular state is strictly necessary, and not assume the returned resource is open.
   */
  suspend fun put(key: K, resource: V): V?

  /**
   * Returns the registered resource associated with [key]. If none exists, evaluates [newValueProvider],
   * registers the result, associates it with [key], and returns it.
   * 
   * Suspends until exclusive access to the manager can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed. Throws [IllegalStateException] if the resource
   * returned by [newValueProvider] is closed.
   * 
   * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
   * unrelated to the manager), and this function checks
   * to ensure the resources are open before returning them; however, an unavoidable race condition
   * exists: A resource could be externally closed after being checked
   * but before being returned; therefore, callers should verify the status of the returned resource
   * when a particular state is strictly necessary, and not assume the returned resource is open.
   */
  suspend fun getOrPut(key: K, newValueProvider: () -> V): V

  /**
   * Deregisters all resources and returns them.
   * 
   * Suspends until exclusive access to the manager can be guaranteed. Throws
   * [IllegalStateException] if this manager is closed.
   * 
   * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
   * unrelated to the manager), and this function checks
   * to ensure the resources are open before returning them; however, an unavoidable race condition
   * exists: A resource could be externally closed after being checked
   * but before being returned; therefore, callers should verify the status of the returned resources
   * when a particular state is strictly necessary, and not assume the returned resources are open.
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
   * 
   * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
   * unrelated to the manager), and this function checks
   * to ensure the resources are open before returning them; however, an unavoidable race condition
   * exists: A resource could be externally closed after being checked
   * but before being returned; therefore, callers should verify the status of the returned resource
   * when a particular state is strictly necessary, and not assume the returned resource is open.
   */
  suspend fun remove(key: K): V?

  /** 
   * Performs access/mutation operations on a specific [ResourcesManager].
   * 
   * Follows the same behaviours and access logic as [ResourceManager] with regard to closure and
   * concurrent access. 
   */
  interface Accessor<K, V> {
    /** 
     * Returns the registered resource associated with [key], or null if none exsts.
     * 
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     * 
     * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
     * unrelated to the manager), and this function checks
     * to ensure the resources are open before returning them; however, an unavoidable race condition
     * exists: A resource could be externally closed after being checked
     * but before being returned; therefore, callers should verify the status of the returned resource
     * when a particular state is strictly necessary, and not assume the returned resource is open.
     */
    suspend fun get(key: K): V?

    /** 
     * Registeres [resource] and associates it with [key]. If another resource is already associated
     * with [key], it is deregistered and returned, but not closed.
     * 
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed. Throws [IllegalStateException] if [resource]
     * is closed.
     * 
     * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
     * unrelated to the manager), and this function checks
     * to ensure the resources are open before returning them; however, an unavoidable race condition
     * exists: A resource could be externally closed after being checked
     * but before being returned; therefore, callers should verify the status of the returned resource
     * when a particular state is strictly necessary, and not assume the returned resource is open.
     */
    suspend fun put(key: K, resource: V): V?

    /**
     * Returns the registered resource associated with [key]. If none exists, evaluates [newValueProvider],
     * registers the result, associates it with [key], and returns it.
     * 
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed. Throws [IllegalStateException] if the resource
     * returned by [newValueProvider] is closed.
     * 
     * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
     * unrelated to the manager), and this function checks
     * to ensure the resources are open before returning them; however, an unavoidable race condition
     * exists: A resource could be externally closed after being checked
     * but before being returned; therefore, callers should verify the status of the returned resource
     * when a particular state is strictly necessary, and not assume the returned resource is open.
     */
    suspend fun getOrPut(key: K, newValueProvider: () -> V): V

    /**
     * Deregisters the resource associated with [key] and returns it, or returns null if none exists.
     * 
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     * 
     * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
     * unrelated to the manager), and this function checks
     * to ensure the resources are open before returning them; however, an unavoidable race condition
     * exists: A resource could be externally closed after being checked
     * but before being returned; therefore, callers should verify the status of the returned resource
     * when a particular state is strictly necessary, and not assume the returned resource is open.
     */
    suspend fun remove(key: K): V?

    /**
     * Deregisters all resources and returns them.
     * 
     * Suspends until exclusive access to the manager can be guaranteed. Throws
     * [IllegalStateException] if this manager is closed.
     * 
     * Caveat: Resources are automatically deregistered when closed externally (i.e. closed by means
     * unrelated to the manager), and this function checks
     * to ensure the resources are open before returning them; however, an unavoidable race condition
     * exists: A resource could be externally closed after being checked
     * but before being returned; therefore, callers should verify the status of the returned resources
     * when a particular state is strictly necessary, and not assume the returned resources are open.
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

  /**
   * Waits for exclusive access to the manager then evaluates [block]. 
   * 
   * The accessor provided to [block] has exclusive access to the manager during
   * function execution but must be discarded after the function returns, as usage outside the
   * function call results in an error. Furthermore, [block] must not call any of the
   * accessor/mutator functions on [ResourceManager] as they will suspend until [block] returnes,
   * thus creating a deadlock. 
   * 
   * WARNING: The accessor has exclusive access to the manager but not concurrent access, meaning
   * concurrent calls to exclusive access functions while the lock is held will deadlock. This ensures
   * exclusive access cannot break the internal state of the resource manager. Do not attempt to use
   * exclusive access functions concurrently.
   * 
   * Suspends until exclusive access to the manager can be guaranteed, and blocks all
   * other accessor/mutator functions until [block] completes. Throws [IllegalStateException] if
   * this manager is closed.
   * 
   */
  suspend fun <R> exclusiveAccess(block: suspend (accessor: Accessor<K, V>) -> R): R

  /**
   * Closes this manager without closing managed resources.
   */
  fun closeSelfOnly()
}
