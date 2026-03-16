package com.jackbradshaw.closet.resourcemanager

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import com.jackbradshaw.coroutines.io.Io
import com.jackbradshaw.closet.observable.ObservableClosable

class ResourceManagerImpl<K, V : ObservableClosable>(
  private val coroutineScope: CoroutineScope
) : ResourceManager<K, V> {
  
  private val lock = Mutex()
  
  private val _hasTerminalState = MutableStateFlow(false)

  override val hasTerminalState = _hasTerminalState.asStateFlow()

  private val _hasTerminatedProcesses = MutableStateFlow(false)
  
  override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

  private val managedResources = mutableMapOf<K, V>()

  private val observeTerminationJobs = mutableMapOf<K, Job>()

  private val internalOperator = OperatorImpl()

  override suspend fun get(key: K): V? {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "get() cannot be called while resourceManager is closed."
      }
      internalOperator.get(key)
    }
  }

  override suspend fun put(key: K, resource: V): V? {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "put() cannot be called while resourceManager is closed."
      }
      internalOperator.put(key, resource)
    }
  }

  override suspend fun getOrPut(key: K, newValueProvider: () -> V): V {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "getOrPut() cannot be called while resourceManager is closed."
      }
      internalOperator.getOrPut(key, newValueProvider)
    }
  }

  override suspend fun <R> exclusiveAccess(
    block: suspend (operator: ResourceManager.Operator<K, V>) -> R
  ): R {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "exclusiveAccess() cannot be called while resourceManager is closed."
      }
      val session = OperatorImpl()
      try {
        block(session)
      } finally {
        session.close()
      }
    }
  }

  override suspend fun clear(): List<V> {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "clear() cannot be called while resourceManager is closed."
      }
      internalOperator.clear()
    }
  }

  override suspend fun size(): Int {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "size() cannot be called while resourceManager is closed."
      }
      internalOperator.size()
    }
  }

  override suspend fun isEmpty(): Boolean {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "isEmpty() cannot be called while resourceManager is closed."
      }
      internalOperator.isEmpty()
    }
  }

  override suspend fun containsKey(key: K): Boolean {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "containsKey() cannot be called while resourceManager is closed."
      }
      internalOperator.containsKey(key)
    }
  }

  override suspend fun containsValue(resource: V): Boolean {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "containsValue() cannot be called while resourceManager is closed."
      }
      internalOperator.containsValue(resource)
    }
  }

  override suspend fun remove(key: K): V? {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "remove() cannot be called while resourceManager is closed."
      }
      internalOperator.remove(key)
    }
  }

  override fun close() {
    val itemsToClose = runBlocking {
      lock.withLock {
        if (hasTerminalState.value) return@withLock emptyList<V>()
        _hasTerminalState.value = true
        
        val currentItems = managedResources.values.toList()
        observeTerminationJobs.values.forEach { it.cancel() }
        observeTerminationJobs.clear()
        managedResources.clear()
        
        currentItems
      }
    }

    itemsToClose.forEach { it.close() }
    _hasTerminatedProcesses.value = true
  }

  override fun closeSelfOnly() {
    runBlocking {
      lock.withLock {
        if (hasTerminalState.value) return@withLock
        _hasTerminalState.value = true
      }
    }

    observeTerminationJobs.values.forEach { it.cancel() }
    observeTerminationJobs.clear()
    managedResources.clear()
    
    _hasTerminatedProcesses.value = true
  }

  private inner class OperatorImpl : ResourceManager.Operator<K, V>, AutoCloseable {

    private val lock = Mutex()
    
    private var isClosed = false

    override fun close() {
      runBlocking {
        lock.withLock {
          isClosed = true
        }
      }
    }

    override suspend fun get(key: K): V? {
      return lock.withLock {
        checkNotClosed()
        val existing = managedResources[key]
        
        // Guards against race conditions where external closure happens mid-get.
        if (existing != null && existing.hasTerminalState.value) {
          return@withLock null
        }
        return@withLock existing
      }
    }
    
    override suspend fun put(key: K, resource: V): V? {
      return lock.withLock {
        checkNotClosed()
        if (resource.hasTerminalState.value) return@withLock null
        val oldItem = managedResources.put(key, resource)
        
        observeTerminationJobs.remove(key)?.cancel()
        observeTerminationJobs[key] = coroutineScope.launch {
          resource.removeSelfOnClosure(key)
        }

        return@withLock oldItem
      }
    }

    override suspend fun getOrPut(key: K, newValueProvider: () -> V): V {
      return lock.withLock {
        checkNotClosed()
        
        var existing = managedResources[key]

        // Guards against race conditions where external closure happens mid-get.
        if (existing != null && !existing.hasTerminalState.value) {
          return@withLock existing
        }

        val newResource = newValueProvider()
        if (newResource.hasTerminalState.value) {
          return@withLock newResource
        }

        managedResources[key] = newResource

        observeTerminationJobs.remove(key)?.cancel()
        observeTerminationJobs[key] = coroutineScope.launch {
          newResource.removeSelfOnClosure(key)
        }
        
        return@withLock newResource
      }
    }

    override suspend fun remove(key: K): V? {
      return lock.withLock {
        checkNotClosed()
        observeTerminationJobs.remove(key)?.cancel()
        return@withLock managedResources.remove(key)
      }
    }

    override suspend fun clear(): List<V> {
      return lock.withLock {
        checkNotClosed()
        val items = managedResources.values.toList()
        observeTerminationJobs.values.forEach { it.cancel() }
        observeTerminationJobs.clear()
        managedResources.clear()
        return@withLock items
      }
    }

    override suspend fun size(): Int {
      return lock.withLock {
        checkNotClosed()
        return@withLock managedResources.size
      }
    }

    override suspend fun isEmpty(): Boolean {
      return lock.withLock {
        checkNotClosed()
        return@withLock managedResources.isEmpty()
      }
    }

    override suspend fun containsKey(key: K): Boolean {
      return lock.withLock {
        checkNotClosed()
        return@withLock managedResources.containsKey(key)
      }
    }

    override suspend fun containsValue(resource: V): Boolean {
      return lock.withLock {
        checkNotClosed()
        return@withLock managedResources.containsValue(resource)
      }
    }

    private fun checkNotClosed() {
      check(!isClosed) {
        "This operator has expired. Each operator should only be used in the exclusiveAccess " +
        "callback that supplied it, and operators should not be retained after the callback " +
        "exits."
      }
    }

    private suspend fun V.removeSelfOnClosure(key: K) {
      combine (hasTerminalState, hasTerminatedProcesses) {stateClosed, processesClosed ->
        stateClosed && processesClosed
      }.first { it }

      lock.withLock {
        if (this@ResourceManagerImpl.hasTerminalState.value) return@withLock
        managedResources.remove(key)
        observeTerminationJobs.remove(key)
      }
    }
  }

  class FactoryImpl @Inject internal constructor(
    @Io private val coroutineScope: CoroutineScope
  ) : ResourceManager.Factory {
    override fun <K, V : ObservableClosable> createResourceManager(): ResourceManager<K, V> = ResourceManagerImpl(coroutineScope)
  }
}
