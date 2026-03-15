package com.jackbradshaw.closet.resourcemanager

import kotlinx.coroutines.flow.StateFlow

import com.jackbradshaw.closet.observable.ObservableClosable

/**
 * A generalized, thread-safe, managed registry of auto-closeable resources.
 * 
 * Provides atomic access and modification mechanisms that guarantee 
 * deterministic closure and state integrity without deadlocks.
 *
 * ## System Behaviors
 * 
 * The resource manager acts as a cohesive state machine governing the following 8 core behaviors:
 *
 * 1. Insertion: Inserting a valid resource registers it for tracking and retrieval.
 * 2. Replacement: Key collisions are resolved by retaining exactly one active resource. 
 *    Overwrites explicitly displace and request closure of the displaced resource. Soft inserts
 *    deduplicate and request closure of the incoming ignored resource.
 * 3. Direct Removal: Removing a resource unregisters it without explicitly requesting its closure.
 * 4. Resource-Driven Closure: The manager observes resources and actively unregisters any 
 *    resource that terminates externally.
 * 5. Manager-Driven Closure: The manager acts as a parent node. Closing the manager
 *    cascades a closure request to all active tracked resources.
 * 6. Validation: Attempting to insert an already-closed resource is a no-op; the resource 
 *    is rejected and untracked.
 * 7. Atomicity: Operations executed within an `exclusiveAccess` block are guaranteed to be 
 *    evaluated synchronously without thread interleaving.
 * 8. Post-Manager-Closure: Post-closure, the manager violently rejects all mutation and query attempts
 *    with an `IllegalStateException`.
 */
interface ResourceManager<K, V : ResourceManager.ManagedResource> : ObservableClosable {

  /** 
   * Retrieves an item if it exists, independently of the accessor.
   * Can be invoked freely.
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
