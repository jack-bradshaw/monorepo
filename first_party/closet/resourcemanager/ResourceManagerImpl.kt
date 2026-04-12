package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.coroutines.Cpu
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/** Default implementation of [ResourceManager]. */
class ResourceManagerImpl<K, V : ObservableClosable>(coroutineContext: CoroutineContext) :
    ResourceManager<K, V> {

  /** Coroutine scope for all work created and managed by this object. */
  private val coroutineScope = CoroutineScope(coroutineContext)

  /**
   * Associates each key in [managedResources] with a job that observes external closure of the
   * resource to automatically deregister it.
   */
  private val observeTerminationJobs = mutableMapOf<K, Job>()

  /** Used to ensure exclusive access to the underlying registry and closure state. */
  private val lock = Mutex()

  /** Associates keys with managed resources. */
  private val managedResources = mutableMapOf<K, V>()

  /** Whether the manager has been closed. */
  private val isClosed = MutableStateFlow(false)

  /** Whether the manager has finished all its asynchronous work. */
  private val isFinishedProcessing = MutableStateFlow(false)

  /**
   * A long-lived operator that non-mutual-exclusive functions delegate to. Avoids duplicating
   * implementation details between the non-mutual-exclusive functions and the operator.
   */
  private val internalOperator = Operator()

  override val hasTerminalState = isClosed.asStateFlow()

  override val hasTerminatedProcesses = isFinishedProcessing.asStateFlow()

  override suspend fun get(key: K): V? {
    return lock.withLock {
      checkOpen()
      internalOperator.get(key)
    }
  }

  override suspend fun put(key: K, resource: V): V? {
    return lock.withLock {
      checkOpen()
      internalOperator.put(key, resource)
    }
  }

  override suspend fun getOrPut(key: K, newValueProvider: () -> V): V {
    return lock.withLock {
      checkOpen()
      internalOperator.getOrPut(key, newValueProvider)
    }
  }

  override suspend fun clear(): List<V> {
    return lock.withLock {
      checkOpen()
      internalOperator.clear()
    }
  }

  override suspend fun size(): Int {
    return lock.withLock {
      checkOpen()
      internalOperator.size()
    }
  }

  override suspend fun isEmpty(): Boolean {
    return lock.withLock {
      checkOpen()
      internalOperator.isEmpty()
    }
  }

  override suspend fun containsKey(key: K): Boolean {
    return lock.withLock {
      checkOpen()
      internalOperator.containsKey(key)
    }
  }

  override suspend fun containsValue(resource: V): Boolean {
    return lock.withLock {
      checkOpen()
      internalOperator.containsValue(resource)
    }
  }

  override suspend fun remove(key: K): V? {
    return lock.withLock {
      checkOpen()
      internalOperator.remove(key)
    }
  }

  override suspend fun <R> exclusiveAccess(
      block: suspend (operator: ResourceManager.Operator<K, V>) -> R
  ): R {
    return lock.withLock {
      checkOpen()
      val op = Operator()
      try {
        block(op)
      } finally {
        op.close()
      }
    }
  }

  override fun close() {
    val alreadyClosed = runBlocking {
      lock.withLock {
        if (isClosed.value) return@withLock true
        isClosed.value = true
        false
      }
    }
    if (alreadyClosed) return

    val jobs = observeTerminationJobs.values.toList()
    observeTerminationJobs.clear()
    runBlocking { jobs.forEach { it.cancelAndJoin() } }
    coroutineScope.cancel()

    val items = managedResources.values.toList()
    managedResources.clear()
    items.forEach { it.close() }

    isFinishedProcessing.value = true
  }

  override fun closeSelfOnly() {
    val alreadyClosed = runBlocking {
      lock.withLock {
        if (isClosed.value) return@withLock true
        isClosed.value = true
        false
      }
    }
    if (alreadyClosed) return

    val jobs = observeTerminationJobs.values.toList()
    observeTerminationJobs.clear()
    runBlocking { jobs.forEach { it.cancelAndJoin() } }

    isFinishedProcessing.value = true
  }

  private fun checkOpen() {
    check(!isClosed.value) { "ResourceManager is closed." }
  }

  /**
   * Performs the actual state mutation operations.
   *
   * The functions perform best-possible checking to prevent race conditions when retrieving values.
   * For example, `get` checks the resource it retrieved is open before returning it to guard
   * against cases where the external resource was closed directly by another thread but the manager
   * has not been updated yet either due to delays in propagating the event or the lock being held.
   * This is not perfect, but catches some cases, and the documentation cautions users to check that
   * returned values are not closed to account for this scenario.
   */
  private inner class Operator : ResourceManager.Operator<K, V>, AutoCloseable {

    /**
     * Prevents concurrent access to operator's various functions to ensure exclusive-access does
     * not create an inconsistent state in the outer object.
     */
    private val lock = Mutex()

    /** Whether this operator has expired. */
    private var isClosed = false

    override fun close() {
      runBlocking { lock.withLock { isClosed = true } }
    }

    override suspend fun get(key: K): V? {
      return lock.withLock {
        checkOpen()

        val existing = managedResources[key]
        return@withLock existing?.takeUnless { it.hasTerminalState.value }
      }
    }

    override suspend fun put(key: K, resource: V): V? {
      return lock.withLock { putActual(key, resource) }
    }

    override suspend fun getOrPut(key: K, newValueProvider: () -> V): V {
      return lock.withLock {
        checkOpen()

        val existing = managedResources[key]?.takeUnless { it.hasTerminalState.value }
        if (existing != null) return@withLock existing

        val newResource = newValueProvider.invoke()
        putActual(key, newResource)
        return@withLock newResource
      }
    }

    override suspend fun remove(key: K): V? {
      return lock.withLock {
        checkOpen()

        val existing = managedResources.remove(key)

        observeTerminationJobs[key]?.cancel()
        observeTerminationJobs.remove(key)

        return@withLock existing
      }
    }

    override suspend fun clear(): List<V> {
      return lock.withLock {
        checkOpen()

        val items = managedResources.values.toList()
        managedResources.clear()

        observeTerminationJobs.values.forEach { it.cancel() }
        observeTerminationJobs.clear()

        return@withLock items
      }
    }

    override suspend fun size(): Int {
      return lock.withLock {
        checkOpen()
        return@withLock managedResources.size
      }
    }

    override suspend fun isEmpty(): Boolean {
      return lock.withLock {
        checkOpen()
        return@withLock managedResources.isEmpty()
      }
    }

    override suspend fun containsKey(key: K): Boolean {
      return lock.withLock {
        checkOpen()
        return@withLock managedResources.containsKey(key)
      }
    }

    override suspend fun containsValue(resource: V): Boolean {
      return lock.withLock {
        checkOpen()
        return@withLock managedResources.containsValue(resource)
      }
    }

    private fun checkOpen() {
      check(!isClosed) {
        "This operator has expired. Each operator should only be used in the exclusiveAccess " +
            "callback that supplied it, and operators should not be retained after the callback " +
            "exits."
      }
    }

    /**
     * Associates [key] with [resource], creates a job to monitor the resource for external closure,
     * and returns the existing value that was associated with the key (if any).
     *
     * This function exists so that `put` and `getOrPut` can reuse the logic, and is necessary
     * because both are guarded by a non-reentrant lock, so `getOrPut` cannot directly call `put`.
     * This function is not locked but checks/updates state so should only be called from a locked
     * context.
     */
    private fun putActual(key: K, resource: V): V? {
      checkOpen()
      check(!resource.hasTerminalState.value) { "New resource is not open, cannot insert." }

      observeTerminationJobs[key]?.cancel()
      observeTerminationJobs[key] = coroutineScope.launch { resource.removeSelfOnClosure(key) }

      val existing = managedResources.put(key, resource)
      return existing?.takeUnless { it.hasTerminalState.value }
    }

    /** Starts a job in [coroutineScope] that monitors this for external closure and removes it. */
    private suspend fun V.removeSelfOnClosure(key: K) {
      combine(hasTerminalState, hasTerminatedProcesses) { stateClosed, processesClosed ->
            stateClosed && processesClosed
          }
          .filter { it }
          .first()

      lock.withLock {
        if (this@ResourceManagerImpl.isClosed.value) return@withLock
        managedResources.remove(key)
        observeTerminationJobs.remove(key)
      }
    }
  }

  /** Factory that provides [ResourceManagerImpl] instances. */
  class FactoryImpl
  @Inject
  internal constructor(@Cpu private val coroutineContext: CoroutineContext) :
      ResourceManager.Factory {
    override fun <K, V : ObservableClosable> createResourceManager(): ResourceManager<K, V> =
        ResourceManagerImpl(coroutineContext)
  }
}
