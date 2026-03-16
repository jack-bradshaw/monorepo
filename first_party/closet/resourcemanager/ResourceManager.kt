package com.jackbradshaw.closet.resourcemanager

import kotlinx.coroutines.flow.StateFlow

import com.jackbradshaw.closet.observable.ObservableClosable

/**
 * Manages a collection of [ObservableClosable] resources.
 * 
 * Resources can be registered, deregistered, and retrieved from the manager, and when the manager
 * itself is closed, all of its registered resources are closed. The manager provides various
 * functions for insertion, removal, retrieval, replacement, and query, all of which are thread-safe
 * and suspend until such a time as they can be safely evaluated (referred to as accessor/mutator
 * functions).
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
 * 3. Closing a registered resource externally will cause the manager to automatically deregister it.
   4. Attempting to register an already closed resource results in an exception.
 * 5. Attempting to call any mutator/accessor function after the manager has been closed results in an exception.
 * 6. All accessor/mutator functions are thread-safe.
 * 7. All calls to accessor/mutator functions suspend while an [exclusiveAccess] block is being evaluated.
 * 8. The [exclusiveAccess] function provides exclusive access but not concurrent access, meaning
 * concurrent calls to exclusive access functions while the lock is held will deadlock. This ensures
 * exclusive access cannot break the internal state of the resource manager. Do not attempt to use
 * exclusive access functions concurrently.
 */
interface ResourceManager<K, V : ResourceManager.ManagedResource> : ObservableClosable {

  /** 
   * Returns the resource associated with [key], or null if none exsts.
   * 
   * Suspends until exclusive execution can be guaranteed. Throws [IllegalStateException] if this manager
   * is closed.
   */
  suspend fun get(key: K): V?

  /** 
   * Registeres [resource] and associates it with [key]. If another resource is already associated
   * with [key], it is deregistered and returned, but not closed.
   * 
   * Suspends until exclusive execution can be guaranteed. Throws [IllegalStateException] if this manager
   * is closed. Throws [IllegalStateException] is [resouce] is closed.
   */
  suspend fun put(key: K, resource: V): V?

  /**
   * Returns the resource associated with [key]. If none exists, evaluates [newValueProvider],
   * registers the result, associates it with [key], and returns it.
   * 
   * Suspends until exclusive execution can be guaranteed. Throws [IllegalStateException] if this manager
   * is closed. Throws [IllegalStateException] if the resource returned by [newValueProvider] is closed.
   */
  suspend fun getOrPut(key: K, newValueProvider: () -> V): V

  /**
   * Deregisters all resources and returns them.
   * 
   * Suspends until exclusive execution can be guaranteed. Throws [IllegalStateException] if this manager
   * is closed.
   */
  suspend fun clear(): List<V>

  /**
   * Returns the number of registered resources.
   * 
   * Suspends until exclusive execution can be guaranteed. Throws [IllegalStateException] if this manager
   * is closed.
   */
  suspend fun size(): Int

  /**
   * Returns true if no resources are registered.
   * 
   * Suspends until exclusive execution can be guaranteed. Throws [IllegalStateException] if this manager
   * is closed.
   */
  suspend fun isEmpty(): Boolean

  /**
   * Returns true if a resource is associated with [key].
   * 
   * Suspends until exclusive execution can be guaranteed. Throws [IllegalStateException] if this manager
   * is closed.
   */
  suspend fun containsKey(key: K): Boolean

  /**
   * Returns true if [resource] is registered.
   * 
   * Suspends until exclusive execution can be guaranteed. Throws [IllegalStateException] if this manager
   * is closed.
   */
  suspend fun containsValue(resource: V): Boolean

  /**
   * Deregisters the resource associated with [key] and returns it, or returns null if none exists.
   * 
   * Suspends until exclusive execution can be guaranteed. Throws [IllegalStateException] if this manager
   * is closed.
   */
  suspend fun remove(key: K): V?

  /** 
   * Defines non-suspending, synchronous registry modifications.
   * 
   * WARNING: Do NOT invoke `ResourceManager.get`, `ResourceManager.put`, `ResourceManager.exclusiveAccess`, 
   * or `ResourceManager.close()` from within these methods. The parent [ResourceManager.exclusiveAccess]
   * block already holds the Mutex lock, and attempting to re-enter it will cause a deadlock.
   */
  interface Accessor<K, V> {
    fun get(key: K): V?
    fun put(key: K, resource: V): V?
    fun getOrPut(key: K, newValueProvider: () -> V): V
    fun remove(key: K): V?
    fun clear(): List<V>
    fun size(): Int
    fun isEmpty(): Boolean
    fun containsKey(key: K): Boolean
    fun containsValue(resource: V): Boolean
  }

  /**
   * Evaluates [block] and returns the result.
   * 
   * Suspends until thread-safe execution is possible, and blocks all other accessor/mutator functions
   * until [block] completes. Throws [IllegalStateException] if this manager is closed.
   */
  suspend fun <R> exclusiveAccess(block: (accessor: Accessor<K, V>) -> R): R

  /**
   * Safely closes the resourceManager, marks [hasTerminalState] to true, and cascades the termination signal 
   * to all currently retained managed resources. Subsequent calls to mutating and accessor
   * functions will fail.
   */
  override fun close()

  /**
   * Safely closes the resourceManager, marks [hasTerminalState] to true, without propagating closure 
   * to managed resources. Subsequent [exclusiveAccess] and [put] calls will be rejected.
   */
  suspend fun closeSelfOnly()

    /**
   * A resource managed by a [ResourceManager] registry that can decouple its internal closure
   * state from the parent's generic collection.
   */
  interface ManagedResource : ObservableClosable {

    /** 
     * A flow that emits `true` exactly once when this resource is externally requested 
     * to shut down (e.g. by a downstream consumer calling its proprietary methods).
     */
    val isClosureRequested: StateFlow<Boolean>

    /**
     * The atomic state-teardown phase. 
     * ResourceManager will call this inside the Mutex lock. 
     * Implementations MUST NOT suspend or perform I/O here.
     */
    fun enterTerminalState()

    /**
     * The blocking process-teardown phase.
     * ResourceManager will call this OUTSIDE the Mutex lock.
     * Implementations SHOULD suspend here (e.g. `job.join()`) to wait for 
     * internal processes to finish.
     */
    suspend fun awaitProcessTermination()
  }

}
