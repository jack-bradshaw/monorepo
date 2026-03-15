package com.jackbradshaw.closet.resourcemanager

import kotlinx.coroutines.flow.StateFlow

import com.jackbradshaw.closet.observable.ObservableClosable

/**
 * Manages a collection of [ObservableClosable] objects.
 * 
 * Closables can be registered, deregistered, and retrieved from the manager, and when the manager
 * itself is closed, all of its registered closables are closed. The manager provides various
 * functions for insertion, removal, retrieval, replacement, and query, all of which are thread-safe
 * and suspend until such a time as they can be safely evaluated. After the manager is closed, all
 * calls to accessors/mutators will results in an IllegalStateException). 
 * 
 * There are three primary operations supported by a variety of paths:
 * 
 * 1. Registration: Associating a resource with this manager so it can inherit the managers closure.
 * Functions are [put], [getOrPut], and the equivalent operations provided by [exclusiveAccess].
 * 2. Deregistration: Dissociating a resource from this manager so it no longer inherits the managers
 * closure. Functions are [remove], [clear], and the equivalent operations provided by
 * [exclusiveAccess].
 * 3. Access: Reading the state of the manager without modification. Functions are [get], [size],
 * [isEmpty], [containsKey], [containsValue], and the equivalent operations provided by
 * [exclusiveAccess].
 * 
 * Registration and deregistration are referred to as mutator functions.
 * 
 * Implementations must ensure all accessor/mutator functions are thread-safe, such that calls may
 * suspends until they can be evaluated safely. In cases where multiple accessor/mutator calls are
 * required in succession without giving other threads an opportunity to mutate state, the
 * [exclusiveAccess] function  can be used. It ensures mutex-like access to the manager and causes
 * all other accessor/mutator functions to suspend while in use.
 * 
 * Caveats:
 * 
 * 1. The manager does not close resources when they are deregistered.
 * 2. Closing the manager with [close] will close all resources; furthermore, the manager's [close] function will
 * block until every managed resource has reached a terminal state and has terminated its processes.
 * 3. Closing a registered resource externally will cause the manager to deregister it.
   4. Attempting to register an already closed resource results in an exception.
 * 5. Attempting to call any mutator/accessor function after the manager has been closed rsults in an exception.
 * 6. All accessor/mutator functions are thread-safe.
 * 7. Call to accessor/mutator functions suspend while a [exclusiveAccess] block is being evaluated.
 * 8. The [exclusiveAccess] function provides exclusive access but not concurrent access, such that
 * concurrent calls to 
 */
interface ResourceManager<K, V : ResourceManager.ManagedResource> : ObservableClosable {

  /** 
   * Retrieves the item associated with [key].
   * 
   * Calls will suspend while other calls to accessors/mutators occur.
   */
  suspend fun get(key: K): V?

  /** 
   * Directly puts a resource by key, independently of the accessor.
   * Will throw IllegalStateException if called when `isOpen` is false.
   */
  suspend fun put(key: K, resource: V): V?

  /**
   * Atomically gets the resource if it exists, or evaluates the [newValueProvider],
   * stores the result, and returns it.
   */
  suspend fun getOrPut(key: K, newValueProvider: () -> V): V

  /**
   * Clears the entire registry, un-tracking and returning all resources concurrently outside the lock.
   */
  suspend fun clear(): List<V>

  /**
   * Returns the current number of resources held in the registry.
   */
  suspend fun size(): Int

  /**
   * Returns `true` if this resourceManager contains no managed resources.
   */
  suspend fun isEmpty(): Boolean

  /**
   * Returns `true` if this resourceManager contains a resource associated with the specified [key].
   */
  suspend fun containsKey(key: K): Boolean

  /**
   * Returns `true` if this resourceManager maps one or more keys to the specified [resource].
   */
  suspend fun containsValue(resource: V): Boolean

  /**
   * Removes and returns the resource associated with the given [key], or `null` if the key is not present.
   * Cancels the underlying observation job tracking its closure.
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
   * Acquires the underlying Mutex lock and executes the provided block synchronously
   * against the managed resource map. 
   * 
   * This guarantees atomic read-modify-write cycles across multiple keys.
   * Throws IllegalStateException if the resourceManager is closed prior to acquiring the lock.
   */
  suspend fun <R> exclusiveAccess(block: (accessor: Accessor<K, V>) -> R): R

  /**
   * Safely closes the resourceManager, marks [isOpen] to false, and cascades the termination signal 
   * to all currently retained managed resources. Subsequent calls to mutating and accessor
   * functions will fail.
   */
  override fun close()

  /**
   * Safely closes the resourceManager, marks [isOpen] to false, without propagating closure 
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
