package com.jackbradshaw.closet.resourcemanager

import org.junit.Test
import org.junit.Ignore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.async
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlin.test.assertFailsWith

import com.jackbradshaw.closet.observable.ObservableClosable

/** 
 * Abstract tests that all [ResourceManager]s should pass.
 *
 * This test suite utilizes a behavior-driven architecture that verifies the manager as a cohesive
 * state machine. Tests are grouped into the following behavioral categories: the headings as a
 * list, correspodning to the [ResouceManager] documentation. This approach focuses on the broad
 * emergent properties of the system instead of the granulur method-level behaviours. Based on
 * experimentation during initial development, migrating ot behaviour-driven tsting reduced overall
 * class length by an order of magnitude.
 */
abstract class ResourceManagerTest<K, V : ObservableClosable> {

  @Test
  fun insertion_directOperation_put_isStoredAndRetrievable() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.put(key, value)
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.isEmpty()).isFalse()
    assertThat(resourceManager.get(key)).isEqualTo(value)
    assertThat(resourceManager.containsKey(key)).isTrue()
    assertThat(resourceManager.containsValue(value)).isTrue()
  }

  @Test
  fun insertion_directOperation_put_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.put(key, value)
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun insertion_directOperation_put_valueNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.put(key, value)
    awaitTestIdle()

    assertThat(value.hasTerminatedProcesses.value).isFalse()
    assertThat(value.hasTerminalState.value).isFalse()
  }

  @Test
  fun insertion_directOperation_getOrPut_isStoredAndRetrievable() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.getOrPut(key) { value }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value)
  }

  @Test
  fun insertion_directOperation_getOrPut_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.getOrPut(key) { value }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun insertion_directOperation_getOrPut_valueNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.getOrPut(key) { value }
    awaitTestIdle()

    assertThat(value.hasTerminatedProcesses.value).isFalse()
    assertThat(value.hasTerminalState.value).isFalse()
  }

  @Test
  fun insertion_exclusiveAccess_put_isStoredAndRetrievable() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.exclusiveAccess { it.put(key, value) }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value)
  }

  @Test
  fun insertion_exclusiveAccess_put_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.exclusiveAccess { it.put(key, value) }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun insertion_exclusiveAccess_put_valueNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.exclusiveAccess { it.put(key, value) }
    awaitTestIdle()

    assertThat(value.hasTerminatedProcesses.value).isFalse()
    assertThat(value.hasTerminalState.value).isFalse()
  }

  @Test
  fun insertion_exclusiveAccess_getOrPut_isStoredAndRetrievable() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.exclusiveAccess { it.put(key, value) }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value)
  }

  @Test
  fun insertion_exclusiveAccess_getOrPut_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.exclusiveAccess { it.put(key, value) }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun insertion_exclusiveAccess_getOrPut_valueNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.exclusiveAccess { it.put(key, value) }
    awaitTestIdle()

    assertThat(value.hasTerminatedProcesses.value).isFalse()
    assertThat(value.hasTerminalState.value).isFalse()
  }


  @Test
  fun replacement_directOperation_put_storesNewAndDisplacesOldWithoutClosing() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    val result1 = resourceManager.put(key, value1)
    awaitTestIdle()
    
    val result2 = resourceManager.put(key, value2)
    awaitTestIdle()

    assertThat(result1).isNull()
    assertThat(result2).isEqualTo(value1)
    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value2)
    assertThat(value1.hasTerminatedProcesses.value).isFalse()
    assertThat(value2.hasTerminatedProcesses.value).isFalse()
  }

  @Test
  fun replacement_directOperation_put_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key, value1)
    awaitTestIdle()
    
    resourceManager.put(key, value2)
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun replacement_directOperation_getOrPut_retainsExistingAndIgnoresIncoming() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    resourceManager.getOrPut(key) { value1 }
    awaitTestIdle()
    
    resourceManager.getOrPut(key) { value2 }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value1)
    assertThat(value1.hasTerminatedProcesses.value).isFalse()
    assertThat(value2.hasTerminatedProcesses.value).isFalse()
  }

  @Test
  fun replacement_directOperation_getOrPut_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    resourceManager.getOrPut(key) { value1 }

  @Test
  fun replacement_exclusiveAccess_put_storesNewAndDisplacesOldWithoutClosing() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    val result1 = resourceManager.put(key, value1)
    awaitTestIdle()
    
    var result2: V? = null
    resourceManager.exclusiveAccess { result2 = it.put(key, value2) }
    awaitTestIdle()

    assertThat(result1).isNull()
    assertThat(result2).isEqualTo(value1)
    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value2)
    assertThat(value1.hasTerminatedProcesses.value).isFalse()
    assertThat(value2.hasTerminatedProcesses.value).isFalse()
  }

  @Test
  fun replacement_exclusiveAccess_put_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key, value1)
    awaitTestIdle()
    
    resourceManager.exclusiveAccess { it.put(key, value2) }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }
    awaitTestIdle()
    
    resourceManager.getOrPut(key) { value2 }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }


  @Test
  fun removal_directOperation_remove_existingResource_isUntrackedButRemainsOpen() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.put(key, value)
    resourceManager.remove(key)
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key)).isNull()
    assertThat(value.hasTerminatedProcesses.value).isFalse()
  }

  @Test
  fun removal_directOperation_remove_existingResource_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.put(key, value)
    resourceManager.remove(key)
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun removal_directOperation_remove_nonExistentResource_doesNotFail() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.remove(key)
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key)).isNull()
    assertThat(value.hasTerminatedProcesses.value).isFalse()
  }

  @Test
  fun removal_directOperation_remove_nonExistentResource_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.remove(key)
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun removal_directOperation_clear_untracksAllButLeavesResourcesOpen() = runBlocking {
    val resourceManager = subject()
    val (key1, value1) = createKeyValuePair("test-key-1")
    val (key2, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key1, value1)
    resourceManager.put(key2, value2)
    resourceManager.clear()
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key1)).isNull()
    assertThat(resourceManager.get(key2)).isNull()
    assertThat(value1.hasTerminatedProcesses.value).isFalse()
    assertThat(value2.hasTerminatedProcesses.value).isFalse()
  }

  @Test
  fun removal_directOperation_clear_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key1, value1) = createKeyValuePair("test-key-1")
    val (key2, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key1, value1)
    resourceManager.put(key2, value2)
    resourceManager.clear()
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun removal_exclusiveAccess_remove_existingResource_isUntrackedButRemainsOpen() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.put(key, value)
    resourceManager.exclusiveAccess { it.remove(key) }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key)).isNull()
    assertThat(value.hasTerminatedProcesses.value).isFalse()
  }

  @Test
  fun removal_exclusiveAccess_remove_existingResource_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.put(key, value)
    resourceManager.exclusiveAccess { it.remove(key) }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun removal_exclusiveAccess_remove_nonExistentResource_doesNotFail() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.exclusiveAccess { it.remove(key) }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key)).isNull()
    assertThat(value.hasTerminatedProcesses.value).isFalse()
  }

  @Test
  fun removal_exclusiveAccess_remove_nonExistentResource_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, _) = createKeyValuePair("test-key")

    resourceManager.exclusiveAccess { it.remove(key) }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun removal_exclusiveAccess_clear_untracksAllButLeavesResourcesOpen() = runBlocking {
    val resourceManager = subject()
    val (key1, value1) = createKeyValuePair("test-key-1")
    val (key2, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key1, value1)
    resourceManager.put(key2, value2)
    resourceManager.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key1)).isNull()
    assertThat(resourceManager.get(key2)).isNull()
    assertThat(value1.hasTerminatedProcesses.value).isFalse()
    assertThat(value2.hasTerminatedProcesses.value).isFalse()
  }

  @Test
  fun removal_exclusiveAccess_clear_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key1, value1) = createKeyValuePair("test-key-1")
    val (key2, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key1, value1)
    resourceManager.put(key2, value2)
    resourceManager.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }


  @Test
  fun resourceClosure_oneClosed_isAutomaticallyUnregistered() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.put(key, value)
    value.close()
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key)).isNull()
    assertThat(resourceManager.containsKey(key)).isFalse()
    assertThat(resourceManager.containsValue(value)).isFalse()
  }

  @Test
  fun resourceClosure_oneClosed_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    resourceManager.put(key, value)
    value.close()
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun resourceClosure_multipleClosed_allAutomaticallyUnregistered() = runBlocking {
    val resourceManager = subject()
    val (key1, value1) = createKeyValuePair("test-key-1")
    val (key2, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key1, value1)
    resourceManager.put(key2, value2)
    
    value1.close()
    value2.close()
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
  }

  @Test
  fun resourceClosure_multipleClosed_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key1, value1) = createKeyValuePair("test-key-1")
    val (key2, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key1, value1)
    resourceManager.put(key2, value2)
    
    value1.close()
    value2.close()
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }


  @Test
  fun managerClosure_close_cascadesClosureToAllResources() = runBlocking {
    val resourceManager = subject()
    val (key1, value1) = createKeyValuePair("test-key-1")
    val (key2, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key1, value1)
    resourceManager.put(key2, value2)
    
    resourceManager.close()
    awaitTestIdle()

    assertThat(value1.hasTerminatedProcesses.value).isTrue()
    assertThat(value2.hasTerminatedProcesses.value).isTrue()
  }

  @Test
  fun managerClosure_closeSelfOnly_closesManagerButLeavesResourcesOpen() = runBlocking {
    val resourceManager = subject()
    val (key1, value1) = createKeyValuePair("test-key-1")
    val (key2, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key1, value1)
    resourceManager.put(key2, value2)
    
    resourceManager.closeSelfOnly()
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isTrue()
    assertThat(resourceManager.hasTerminalState.value).isTrue()
    assertThat(value1.hasTerminatedProcesses.value).isFalse()
    assertThat(value1.hasTerminalState.value).isFalse()
    assertThat(value2.hasTerminatedProcesses.value).isFalse()
    assertThat(value2.hasTerminalState.value).isFalse()
  }

  @Test
  fun managerClosure_close_closesBothManagerAndResources() = runBlocking {
    val resourceManager = subject()
    val (key1, value1) = createKeyValuePair("test-key-1")
    val (key2, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key1, value1)
    resourceManager.put(key2, value2)
    
    resourceManager.close()
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isTrue()
    assertThat(resourceManager.hasTerminalState.value).isTrue()
    assertThat(value1.hasTerminatedProcesses.value).isTrue()
    assertThat(value2.hasTerminatedProcesses.value).isTrue()
  }


  @Test
  fun validation_insertClosedResource_directOperation_put_isIgnoredAndUntracked() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    value.close()
    awaitTestIdle()

    resourceManager.put(key, value)
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key)).isNull()
  }

  @Test
  fun validation_insertClosedResource_directOperation_put_retainsExisting() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key, value1)
    value2.close()
    awaitTestIdle()

    resourceManager.put(key, value2)
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value1)
  }

  @Test
  fun validation_insertClosedResource_directOperation_put_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    value.close()
    awaitTestIdle()

    resourceManager.put(key, value)
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun validation_insertClosedResource_directOperation_getOrPut_isIgnoredAndUntracked() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    value.close()
    awaitTestIdle()

    resourceManager.getOrPut(key) { value }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key)).isNull()
  }

  @Test
  fun validation_insertClosedResource_directOperation_getOrPut_retainsExisting() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key, value1)
    value2.close()
    awaitTestIdle()

    resourceManager.getOrPut(key) { value2 }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value1)
  }

  @Test
  fun validation_insertClosedResource_directOperation_getOrPut_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    value.close()
    awaitTestIdle()

    resourceManager.getOrPut(key) { value }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun validation_insertClosedResource_exclusiveAccess_put_isIgnoredAndUntracked() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    value.close()
    awaitTestIdle()

    resourceManager.exclusiveAccess { it.put(key, value) }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key)).isNull()
  }

  @Test
  fun validation_insertClosedResource_exclusiveAccess_put_retainsExisting() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key, value1)
    value2.close()
    awaitTestIdle()

    resourceManager.exclusiveAccess { it.put(key, value2) }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value1)
  }

  @Test
  fun validation_insertClosedResource_exclusiveAccess_put_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    value.close()
    awaitTestIdle()

    resourceManager.exclusiveAccess { it.put(key, value) }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun validation_insertClosedResource_exclusiveAccess_getOrPut_isIgnoredAndUntracked() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    value.close()
    awaitTestIdle()

    resourceManager.exclusiveAccess { it.getOrPut(key) { value } }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(0)
    assertThat(resourceManager.get(key)).isNull()
  }

  @Test
  fun validation_insertClosedResource_exclusiveAccess_getOrPut_retainsExisting() = runBlocking {
    val resourceManager = subject()
    val (key, value1) = createKeyValuePair("test-key-1")
    val (_, value2) = createKeyValuePair("test-key-2")

    resourceManager.put(key, value1)
    value2.close()
    awaitTestIdle()

    resourceManager.exclusiveAccess { it.getOrPut(key) { value2 } }
    awaitTestIdle()

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(key)).isEqualTo(value1)
  }

  @Test
  fun validation_insertClosedResource_exclusiveAccess_getOrPut_managerNotClosed() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")

    value.close()
    awaitTestIdle()

    resourceManager.exclusiveAccess { it.getOrPut(key) { value } }
    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun atomicity_operationsWithinBlock_areEvaluatedSynchronouslyUnderLock() = runBlocking {
    val resourceManager = subject()
    
    val (keyA, valueA) = createKeyValuePair("key-a")
    val (keyB, valueB) = createKeyValuePair("key-b")
    val (keyC, valueC) = createKeyValuePair("key-c")
    val (keyD, valueD) = createKeyValuePair("key-d")

    resourceManager.put(keyA, valueA)
    resourceManager.put(keyB, valueB)

    resourceManager.exclusiveAccess {
      it.put(keyC, valueC)
      it.clear()
      it.put(keyD, valueD)
    }

    assertThat(resourceManager.size()).isEqualTo(1)
    assertThat(resourceManager.get(keyD)).isEqualTo(valueD)
    assertThat(valueA.hasTerminalState.value).isFalse()
    assertThat(valueB.hasTerminalState.value).isFalse()
    assertThat(valueC.hasTerminalState.value).isFalse()
  }

  @Test
  fun atomicity_operationsWithinBlock_managerNotClosed() = runBlocking {
    val resourceManager = subject()

    val (keyA, valueA) = createKeyValuePair("key-a")
    val (keyB, valueB) = createKeyValuePair("key-b")
    val (keyC, valueC) = createKeyValuePair("key-c")
    val (keyD, valueD) = createKeyValuePair("key-d")

    resourceManager.put(keyA, valueA)
    resourceManager.put(keyB, valueB)

    resourceManager.exclusiveAccess {
      it.put(keyC, valueC)
      it.clear()
      it.put(keyD, valueD)
    }

    awaitTestIdle()

    assertThat(resourceManager.hasTerminatedProcesses.value).isFalse()
    assertThat(resourceManager.hasTerminalState.value).isFalse()
  }

  @Test
  fun atomicity_leakedAccessorUsage_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")
    
    var leakedAccessor: ResourceManager.Accessor<K, V>? = null
    resourceManager.exclusiveAccess { leakedAccessor = it }
    
    val e = assertFailsWith<IllegalStateException> {
      leakedAccessor!!.put(key, value)
    }
    assertThat(e).hasMessageThat().isEqualTo("This accessor has expired. Each accessor should only be used in the exclusiveAccess callback that supplied it, and accessors should not be retained after the callback exits.")
  }


  @Test
  fun postManagerClosure_directOperation_get_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    val (key, _) = createKeyValuePair("test-key")
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.get(key) }
    assertThat(e).hasMessageThat().isEqualTo("get() cannot be called while resourceManager is closed.")
  }

  @Test
  fun postManagerClosure_directOperation_size_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.size() }
    assertThat(e).hasMessageThat().isEqualTo("size() cannot be called while resourceManager is closed.")
  }

  @Test
  fun postManagerClosure_directOperation_isEmpty_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.isEmpty() }
    assertThat(e).hasMessageThat().isEqualTo("isEmpty() cannot be called while resourceManager is closed.")
  }

  @Test
  fun postManagerClosure_directOperation_containsKey_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    val (key, _) = createKeyValuePair("test-key")
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.containsKey(key) }
    assertThat(e).hasMessageThat().isEqualTo("containsKey() cannot be called while resourceManager is closed.")
  }

  @Test
  fun postManagerClosure_directOperation_containsValue_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    val (_, value) = createKeyValuePair("test-key")
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.containsValue(value) }
    assertThat(e).hasMessageThat().isEqualTo("containsValue() cannot be called while resourceManager is closed.")
  }

  @Test
  fun postManagerClosure_directOperation_put_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.put(key, value) }
    assertThat(e).hasMessageThat().isEqualTo("put() cannot be called while resourceManager is closed.")
  }

  @Test
  fun postManagerClosure_directOperation_getOrPut_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    val (key, value) = createKeyValuePair("test-key")
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.getOrPut(key) { value } }
    assertThat(e).hasMessageThat().isEqualTo("getOrPut() cannot be called while resourceManager is closed.")
  }

  @Test
  fun postManagerClosure_directOperation_remove_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    val (key, _) = createKeyValuePair("test-key")
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.remove(key) }
    assertThat(e).hasMessageThat().isEqualTo("remove() cannot be called while resourceManager is closed.")
  }

  @Test
  fun postManagerClosure_directOperation_clear_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.clear() }
    assertThat(e).hasMessageThat().isEqualTo("clear() cannot be called while resourceManager is closed.")
  }

  @Test
  fun postManagerClosure_exclusiveAccess_throwsIllegalStateException() = runBlocking {
    val resourceManager = subject()
    resourceManager.close()
    awaitTestIdle()

    val e = assertFailsWith<IllegalStateException> { resourceManager.exclusiveAccess {} }
    assertThat(e).hasMessageThat().isEqualTo("exclusiveAccess() cannot be called while resourceManager is closed.")
  }

  abstract fun subject(): ResourceManager<K, V>

  /**
   * Optional helper to instantiate the AutoCloseable values used in the tests.
   */
  abstract fun createKeyValuePair(id: String): Pair<K, V>


  /**
   * Called whenever the test suite requires asynchronous background processes (like manager evictions)
   * to settle before evaluating invariants. Must suspend until idle.
   */
  abstract suspend fun awaitTestIdle()
}
