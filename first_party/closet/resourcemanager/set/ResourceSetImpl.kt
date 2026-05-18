package com.jackbradshaw.closet.resourcemanager.set

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
 * Default implementation of [ResourceSet].
 *
 * The closure and locking system work together to implement the [ResourceSet] contract and ensure
 * closure is not delayed. Each function checks closure status immediately upon entry to ensure
 * post-closure failure, and then checks again after acquiring a shared lock to exit quickly if
 * closure occurred after the initial check and the lock acquisition. Furthermore, active calls to
 * [exclusiveAccess] (if any) are cancelled to prevent closure suspending while it suspends. This
 * system collectively ensures new calls fail after closure, while preventing indefinite suspension
 * of [close], and preventing concurrent access to the underlying state.
 */
class ResourceSetImpl<V : ObservableClosable>(
    dispatcher: CoroutineDispatcher,
    private val standardFactory: StandardObservableClosableFactory
) : ResourceSet<V> {

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
   * Associates each resource in [registry] with a job that observes external closure of the
   * resource to automatically deregister it.
   */
  private val observeTerminationJobs = mutableMapOf<V, Job>()

  /**
   * Used to ensure exclusive access to the underlying registry and closure state.
   *
   * The name explicitly includes "outer" to avoid variable shadowing with the [innerLock].
   */
  private val outerLock = Mutex()

  /** Registered resources. */
  private val registry = mutableSetOf<V>()

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
            registry.forEach { it.close() }
            registry.clear()
          }
        }
  }

  override suspend fun close() = standardClosable.close()

  override suspend fun getAll(): Set<V> {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock emptySet()
      internalOperator.getAll()
    }
  }

  override suspend fun add(resource: V): Boolean {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock false
      internalOperator.add(resource)
    }
  }

  override suspend fun remove(resource: V): Boolean {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock false
      internalOperator.remove(resource)
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

  override suspend fun contains(resource: V): Boolean {
    checkOpen()
    return outerLock.withLock {
      if (closureStatus.value != Status.OPEN) return@withLock false
      internalOperator.contains(resource)
    }
  }

  override suspend fun <R> exclusiveAccess(
      block: suspend (operator: ResourceSet.Operator<V>) -> R
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
   */
  private inner class Operator : ResourceSet.Operator<V>, SuspendableClosable {

    /**
     * Prevents concurrent access to operator's various functions to ensure exclusive-access does
     * not create an inconsistent state in the outer object.
     *
     * The name explicitly includes "inner" to avoid variable shadowing with the [outerLock].
     */
    private val innerLock = Mutex()

    /** Whether this operator should no longer be used. */
    private var isClosed = false

    override suspend fun getAll(): Set<V> {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock emptySet()
        return@withLock registry.filter { it.closureStatus.value == Status.OPEN }.toSet()
      }
    }

    override suspend fun add(resource: V): Boolean {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock false
        putActual(resource)
      }
    }

    override suspend fun remove(resource: V): Boolean {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock false

        val existing = registry.remove(resource)

        if (existing) {
          observeTerminationJobs[resource]?.cancel()
          observeTerminationJobs.remove(resource)
        }

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

    override suspend fun contains(resource: V): Boolean {
      checkOpen()
      return innerLock.withLock {
        if (closureStatus.value != Status.OPEN) return@withLock false
        return@withLock registry.contains(resource)
      }
    }

    override suspend fun close() {
      innerLock.withLock { isClosed = true }
    }

    private fun checkOpen() {
      check(!isClosed) {
        "This operator has expired. Each operator should only be used in the exclusiveAccess " +
            "callback that supplied it, and operators should not be retained after the callback " +
            "exits."
      }
    }

    private fun putActual(resource: V): Boolean {
      check(resource.closureStatus.value == Status.OPEN) {
        "New resource is not open, cannot insert."
      }

      val wasAdded = registry.add(resource)
      if (wasAdded) {
        // Setup task after insertion to prevent race conditions between closure and insertion.
        observeTerminationJobs[resource]?.cancel()
        observeTerminationJobs[resource] =
            resourceClosureObservationScope.launch { resource.removeSelfOnClosure() }
      }
      return wasAdded
    }

    /**
     * Starts a job in [resourceClosureObservationScope] that monitors this for external closure and
     * removes it.
     */
    private suspend fun V.removeSelfOnClosure() {
      this.awaitClosed()

      this@ResourceSetImpl.outerLock.withLock {
        if (this@ResourceSetImpl.closureStatus.value != Status.OPEN) return@withLock
        registry.remove(this@removeSelfOnClosure)
        observeTerminationJobs.remove(this@removeSelfOnClosure)
      }
    }
  }

  /** Factory that provides [ResourceSetImpl] instances. */
  @ClosetScope
  class FactoryImpl
  @Inject
  internal constructor(
      @Cpu private val dispatcher: CoroutineDispatcher,
      private val standardFactory: StandardObservableClosableFactory
  ) : ResourceSet.Factory {
    override suspend fun <V : ObservableClosable> createResourceSet(): ResourceSet<V> {
      return ResourceSetImpl<V>(dispatcher, standardFactory).apply { initialize() }
    }
  }
}
