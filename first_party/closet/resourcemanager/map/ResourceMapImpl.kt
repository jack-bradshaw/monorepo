package com.jackbradshaw.closet.resourcemanager.map

import com.jackbradshaw.closet.ClosetScope
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosable.Status
import com.jackbradshaw.closet.observable.helpers.awaitClosed
import com.jackbradshaw.closet.observable.helpers.checkOpen
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableFactory
import com.jackbradshaw.closet.suspending.SuspendableClosable
import com.jackbradshaw.coroutines.Cpu
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Default implementation of [ResourceMap].
 *
 * The closure and locking system work together to implement the [ResourceMap] contract and ensure
 * closure is not delayed. Each function checks closure status immediately upon entry to ensure
 * post-closure failure, and then checks again after acquiring a shared lock to exit quickly if
 * closure occurred after the initial check and the lock acquisition. Furthermore, active calls to
 * [exclusiveAccess] (if any) are cancelled to prevent closure suspending while it suspends. This
 * system collectively ensures new calls fail after closure, while preventing indefinite suspension
 * of [close], and preventing concurrent access to the underlying state.
 */
class ResourceMapImpl<K, V : ObservableClosable>(
    dispatcher: CoroutineDispatcher,
    private val standardFactory: StandardObservableClosableFactory
) : ResourceMap<K, V> {

  /** Handle to make [resourceClosureObservationScope] cancellable. */
  private val resourceClosureObservationScopeHandle = Job()

  /** Coroutine scope strictly used for jobs that observe external closure of resources. */
  private val resourceClosureObservationScope =
      CoroutineScope(dispatcher + resourceClosureObservationScopeHandle)

  /** Handle to make [exclusiveAccessScope] cancellable. */
  private val exclusiveAccessScopeHandle = SupervisorJob()

  /** Coroutine scope strictly used for running operations within [exclusiveAccess] blocks. */
  private val exclusiveAccessScope = CoroutineScope(dispatcher + exclusiveAccessScopeHandle)

  /**
   * Associates each key in [registry] with a job that observes external closure of the resource to
   * automatically deregister it.
   */
  private val observeTerminationJobs = mutableMapOf<K, Job>()

  /**
   * Used to ensure exclusive access to the underlying registry and closure state.
   *
   * The name explicitly includes "outer" to avoid variable shadowing with the [innerLock].
   */
  private val outerLock = Mutex()

  /** Associates keys with registered resources. */
  private val registry = mutableMapOf<K, V>()

  /**
   * A long-lived operator that non-exclusive-access functions delegate to. Avoids duplicating
   * implementation details between the non-exclusive-access functions and the operator.
   */
  private val internalOperator = Operator()

  /** Handles closure, set during [initialize]. */
  private lateinit var standardClosable: ObservableClosable

  override val closureStatus: StateFlow<Status>
    get() = standardClosable.closureStatus

  /** Performs initial post-construct setup. Will fail if called more than once. */
  protected suspend fun initialize() {
    standardClosable =
        standardFactory.createStandardClosable {
          exclusiveAccessScopeHandle.cancelAndJoin()

          outerLock.withLock {
            resourceClosureObservationScopeHandle.cancelAndJoin()
            observeTerminationJobs.clear()

            registry.values.forEach { it.close() }
            registry.clear()
          }
        }
  }

  override suspend fun close() = standardClosable.close()

  override suspend fun get(key: K): V? {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock null
      internalOperator.get(key)
    }
  }

  override suspend fun getAll(): Map<K, V> {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock emptyMap()
      internalOperator.getAll()
    }
  }

  override suspend fun put(key: K, resource: V): V? {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock null
      internalOperator.put(key, resource)
    }
  }

  override suspend fun getOrPut(key: K, newValueProvider: () -> V): V {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock newValueProvider()
      internalOperator.getOrPut(key, newValueProvider)
    }
  }

  override suspend fun clear() {
    checkOpen()
    outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock
      internalOperator.clear()
    }
  }

  override suspend fun size(): Int {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock 0
      internalOperator.size()
    }
  }

  override suspend fun isEmpty(): Boolean {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock true
      internalOperator.isEmpty()
    }
  }

  override suspend fun containsKey(key: K): Boolean {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock false
      internalOperator.containsKey(key)
    }
  }

  override suspend fun containsValue(resource: V): Boolean {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock false
      internalOperator.containsValue(resource)
    }
  }

  override suspend fun remove(key: K): V? {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock null
      internalOperator.remove(key)
    }
  }

  override suspend fun <R> exclusiveAccess(
      block: suspend (operator: ResourceMap.Operator<K, V>) -> R
  ): R? {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock null
      val op = Operator()
      try {
        exclusiveAccessScope.async { block(op) }.await()
      } finally {
        op.close()
      }
    }
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
  private inner class Operator : ResourceMap.Operator<K, V>, SuspendableClosable {

    /**
     * Prevents concurrent access to operator's functions to ensure exclusive-access does not create
     * an inconsistent state in the outer object.
     *
     * The name explicitly includes "inner" to avoid variable shadowing with the [outerLock].
     */
    private val innerLock = Mutex()

    /** Whether this operator should no longer be used. */
    private var isClosed = false

    override suspend fun close() {
      innerLock.withLock { isClosed = true }
    }

    override suspend fun get(key: K): V? {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock null
        val existing = registry[key]
        return@withLock existing?.takeIf { it.closureStatus.value == Status.OPEN }
      }
    }

    override suspend fun getAll(): Map<K, V> {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock emptyMap()
        return@withLock registry.filterValues { it.closureStatus.value == Status.OPEN }
      }
    }

    override suspend fun put(key: K, resource: V): V? {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock null
        putActual(key, resource)
      }
    }

    override suspend fun getOrPut(key: K, newValueProvider: () -> V): V {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock newValueProvider()

        val existing = registry[key]?.takeIf { it.closureStatus.value == Status.OPEN }
        if (existing != null) return@withLock existing

        val newResource = newValueProvider.invoke()
        putActual(key, newResource)
        return@withLock newResource
      }
    }

    override suspend fun remove(key: K): V? {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock null

        val existing = registry.remove(key)

        observeTerminationJobs[key]?.cancel()
        observeTerminationJobs.remove(key)

        return@withLock existing
      }
    }

    override suspend fun clear() {
      checkOpen()
      innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock

        registry.clear()

        observeTerminationJobs.values.forEach { it.cancel() }
        observeTerminationJobs.clear()
      }
    }

    override suspend fun size(): Int {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock 0
        return@withLock registry.size
      }
    }

    override suspend fun isEmpty(): Boolean {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock true
        return@withLock registry.isEmpty()
      }
    }

    override suspend fun containsKey(key: K): Boolean {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock false
        return@withLock registry.containsKey(key)
      }
    }

    override suspend fun containsValue(resource: V): Boolean {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock false
        return@withLock registry.containsValue(resource)
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
      check(resource.closureStatus.value == Status.OPEN) {
        "New resource is not open, cannot insert."
      }

      val existing = registry[key]

      // Setup task after insertion to prevent race conditions between closure and insertion.
      registry[key] = resource
      observeTerminationJobs[key]?.cancel()
      observeTerminationJobs[key] =
          resourceClosureObservationScope.launch { resource.removeSelfOnClosure(key) }

      return existing?.takeIf { it.closureStatus.value == Status.OPEN }
    }

    /**
     * Starts a job in [resourceClosureObservationScope] that monitors this for external closure and
     * removes it.
     */
    private suspend fun V.removeSelfOnClosure(key: K) {
      this.awaitClosed()

      this@ResourceMapImpl.outerLock.withLock {
        if (this@ResourceMapImpl.closureStatus.value != Status.OPEN) return@withLock
        registry.remove(key)
        observeTerminationJobs.remove(key)
      }
    }
  }

  /** Factory that provides [ResourceMapImpl] instances. */
  @ClosetScope
  class FactoryImpl
  @Inject
  internal constructor(
      @Cpu private val dispatcher: CoroutineDispatcher,
      private val standardFactory: StandardObservableClosableFactory
  ) : ResourceMap.Factory {
    override suspend fun <K, V : ObservableClosable> createResourceMap(): ResourceMap<K, V> {
      return ResourceMapImpl<K, V>(dispatcher, standardFactory).apply { initialize() }
    }
  }
}
