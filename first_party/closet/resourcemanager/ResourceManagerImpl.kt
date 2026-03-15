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
import com.jackbradshaw.closet.resourcemanager.ResourceManager.ManagedResource

internal class ResourceManagerImpl<K, V : ManagedResource>(
  private val coroutineScope: CoroutineScope
) : ResourceManager<K, V> {
  
  private val lock = Mutex()
  
  private val _hasTerminalState = MutableStateFlow(false)

  override val hasTerminalState = _hasTerminalState.asStateFlow()

  private val _hasTerminatedProcesses = MutableStateFlow(false)
  
  override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

  private val managedResources = mutableMapOf<K, V>()

  private val observeTerminationJobs = mutableMapOf<K, Job>()

  private val internalAccessor = SessionAccessor()

  override suspend fun get(key: K): V? {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "get() cannot be called while resourceManager is closed."
      }
      internalAccessor.get(key)
    }
  }

  override suspend fun put(key: K, resource: V): V? {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "put() cannot be called while resourceManager is closed."
      }
      internalAccessor.put(key, resource)
    }
  }

  override suspend fun getOrPut(key: K, newValueProvider: () -> V): V {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "getOrPut() cannot be called while resourceManager is closed."
      }
      internalAccessor.getOrPut(key, newValueProvider)
    }
  }

  override suspend fun <R> exclusiveAccess(
    block: (accessor: ResourceManager.Accessor<K, V>) -> R
  ): R {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "exclusiveAccess() cannot be called while resourceManager is closed."
      }
      val session = SessionAccessor()
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
      internalAccessor.clear()
    }
  }

  override suspend fun size(): Int {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "size() cannot be called while resourceManager is closed."
      }
      internalAccessor.size()
    }
  }

  override suspend fun isEmpty(): Boolean {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "isEmpty() cannot be called while resourceManager is closed."
      }
      internalAccessor.isEmpty()
    }
  }

  override suspend fun containsKey(key: K): Boolean {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "containsKey() cannot be called while resourceManager is closed."
      }
      internalAccessor.containsKey(key)
    }
  }

  override suspend fun containsValue(resource: V): Boolean {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "containsValue() cannot be called while resourceManager is closed."
      }
      internalAccessor.containsValue(resource)
    }
  }

  override suspend fun remove(key: K): V? {
    return lock.withLock {
      check (!hasTerminalState.value) {
        "remove() cannot be called while resourceManager is closed."
      }
      internalAccessor.remove(key)
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

  override suspend fun closeSelfOnly() {
    lock.withLock {
      if (hasTerminalState.value) return@withLock
      _hasTerminalState.value = true
    }

    observeTerminationJobs.values.forEach { it.cancel() }
    observeTerminationJobs.clear()
    managedResources.clear()
    
    _hasTerminatedProcesses.value = true
  }

  private inner class SessionAccessor : ResourceManager.Accessor<K, V>, AutoCloseable {
    
    private var isClosed = false

    override fun close() {
      isClosed = true
    }

    override fun get(key: K): V? {
      checkNotClosed()
      val existing = managedResources[key]
      if (existing != null && existing.hasTerminalState.value) {
        remove(key)
        return null
      }
      return existing
    }
    
    override fun put(key: K, resource: V): V? {
      checkNotClosed()
      if (resource.hasTerminalState.value) return null
      val oldItem = managedResources.put(key, resource)
      
      observeTerminationJobs.remove(key)?.cancel()
      observeTerminationJobs[key] = coroutineScope.launch {
        resource.removeSelfOnClosure(key)
      }

      return oldItem
    }

    override fun getOrPut(key: K, newValueProvider: () -> V): V {
      checkNotClosed()
      
      var existing = managedResources[key]
      if (existing != null) {
        if (!existing.hasTerminalState.value) {
          return existing
        } else {
          remove(key)
        }
      }

      val newResource = newValueProvider()
      if (newResource.hasTerminalState.value) {
        return newResource
      }

      managedResources[key] = newResource

      observeTerminationJobs.remove(key)?.cancel()
      observeTerminationJobs[key] = coroutineScope.launch {
        newResource.removeSelfOnClosure(key)
      }
      
      return newResource
    }

    override fun remove(key: K): V? {
      checkNotClosed()
      observeTerminationJobs.remove(key)?.cancel()
      return managedResources.remove(key)
    }

    override fun clear(): List<V> {
      checkNotClosed()
      val items = managedResources.values.toList()
      observeTerminationJobs.values.forEach { it.cancel() }
      observeTerminationJobs.clear()
      managedResources.clear()
      return items
    }

    override fun size(): Int {
      checkNotClosed()
      return managedResources.size
    }

    override fun isEmpty(): Boolean {
      checkNotClosed()
      return managedResources.isEmpty()
    }

    override fun containsKey(key: K): Boolean {
      checkNotClosed()
      return managedResources.containsKey(key)
    }

    override fun containsValue(resource: V): Boolean {
      checkNotClosed()
      return managedResources.containsValue(resource)
    }

    private fun checkNotClosed() {
      check(!isClosed) {
        "This accessor has expired. Each accessor should only be used in the exclusiveAccess " +
        "callback that supplied it, and accessors should not be retained after the callback " +
        "exits."
      }
    }

    private suspend fun V.removeSelfOnClosure(key: K) {
      combine (hasTerminalState, hasTerminatedProcesses) {s, p ->
        s && p // 500
      }.first { it }

      lock.withLock {
        if (this@ResourceManagerImpl.hasTerminalState.value) return@withLock
        managedResources.remove(key)
        observeTerminationJobs.remove(key)
      }
    }
  }
}
