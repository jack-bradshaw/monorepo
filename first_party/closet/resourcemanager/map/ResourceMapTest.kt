package com.jackbradshaw.closet.resourcemanager.map

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosable.Status
import kotlin.test.assertFailsWith
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Abstract tests that all [ResourceMap]s should pass.
 *
 * These tests are generally structured around making assertions on a particular function given some
 * condition. Given the CRUD nature of the system under test, this means that some functions are
 * tested repeatedly as part of the setup for others (e.g. retrieval cannot be tested without first
 * exercising insertion) but this is preferable to the alternative: Attempting to define series' of
 * operations that minimise overlap, as such tests tend to make it harder to check the behaviours of
 * each function. To ensure emergent properties are checked, various closure conditions and
 * transitions are checked, in addition to advanced tests for concurrency behaviours.
 */
@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
abstract class ResourceMapTest<K, V : ObservableClosable> {

  @Test
  fun size_calledOnTopLevel_beforeClose_nothingInserted_returnsZero(): Unit = runBlocking {
    val resourceMap = subject()

    val size = resourceMap.size()

    assertThat(size).isEqualTo(0)
  }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsZero(): Unit = runBlocking {
    val resourceMap = subject()

    val size = resourceMap.exclusiveAccess { it.size() }

    assertThat(size).isEqualTo(0)
  }

  @Test
  fun size_calledOnTopLevel_beforeClose_oneInsertedAndNotClosed_returnsOne(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    val size = resourceMap.size()

    assertThat(size).isEqualTo(1)
  }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_oneInsertedAndNotClosed_returnsOne(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        val size = resourceMap.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(1)
      }

  @Test
  fun size_calledOnTopLevel_beforeClose_oneInsertedThenClosed_returnsZero(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    val size = resourceMap.size()

    assertThat(size).isEqualTo(0)
  }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_oneInsertedThenClosed_returnsZero(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        val size = resourceMap.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(0)
      }

  @Test
  fun size_calledOnTopLevel_beforeClose_multipleInsertedThenOneClosed_returnsOne(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        awaitTestIdle()

        val size = resourceMap.size()

        assertThat(size).isEqualTo(1)
      }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_multipleInsertedThenOneClosed_returnsOne(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        awaitTestIdle()

        val size = resourceMap.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(1)
      }

  @Test
  fun size_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_returnsTwo(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        awaitTestIdle()

        val size = resourceMap.size()

        assertThat(size).isEqualTo(2)
      }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_returnsTwo(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        awaitTestIdle()

        val size = resourceMap.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(2)
      }

  @Test
  fun size_calledOnTopLevel_beforeClose_multipleInsertedThenAllClosed_returnsZero(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        resource2.value.close()
        awaitTestIdle()

        val size = resourceMap.size()

        assertThat(size).isEqualTo(0)
      }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_multipleInsertedThenAllClosed_returnsZero(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        resource2.value.close()
        awaitTestIdle()

        val size = resourceMap.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(0)
      }

  @Test
  fun size_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    resourceMap.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceMap.size() }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_nothingInserted_returnsTrue(): Unit = runBlocking {
    val resourceMap = subject()

    val isEmpty = resourceMap.isEmpty()

    assertThat(isEmpty).isTrue()
  }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsTrue(): Unit =
      runBlocking {
        val resourceMap = subject()

        val isEmpty = resourceMap.exclusiveAccess { it.isEmpty() }

        assertThat(isEmpty).isTrue()
      }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_oneInsertedAndNotClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        val isEmpty = resourceMap.isEmpty()

        assertThat(isEmpty).isFalse()
      }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_oneInsertedAndNotClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        val isEmpty = resourceMap.exclusiveAccess { it.isEmpty() }

        assertThat(isEmpty).isFalse()
      }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_oneInsertedThenClosed_returnsTrue(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    val isEmpty = resourceMap.isEmpty()

    assertThat(isEmpty).isTrue()
  }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_oneInsertedThenClosed_returnsTrue(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        val isEmpty = resourceMap.exclusiveAccess { it.isEmpty() }

        assertThat(isEmpty).isTrue()
      }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_multipleInsertedThenOneClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        awaitTestIdle()

        val isEmpty = resourceMap.isEmpty()

        assertThat(isEmpty).isFalse()
      }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_multipleInsertedThenOneClosed_returnsFalse():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)
    resource1.value.close()
    awaitTestIdle()

    val isEmpty = resourceMap.exclusiveAccess { it.isEmpty() }

    assertThat(isEmpty).isFalse()
  }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        awaitTestIdle()

        val isEmpty = resourceMap.isEmpty()

        assertThat(isEmpty).isFalse()
      }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_returnsFalse():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)
    awaitTestIdle()

    val isEmpty = resourceMap.exclusiveAccess { it.isEmpty() }

    assertThat(isEmpty).isFalse()
  }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_multipleInsertedThenAllClosed_returnsTrue(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        resource2.value.close()
        awaitTestIdle()

        val isEmpty = resourceMap.isEmpty()

        assertThat(isEmpty).isTrue()
      }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_multipleInsertedThenAllClosed_returnsTrue():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)
    resource1.value.close()
    resource2.value.close()
    awaitTestIdle()

    val isEmpty = resourceMap.exclusiveAccess { it.isEmpty() }

    assertThat(isEmpty).isTrue()
  }

  @Test
  fun isEmpty_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    resourceMap.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceMap.isEmpty() }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun containsKey_calledOnTopLevel_beforeClose_nothingInserted_returnsFalse(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()

    val containsKey = resourceMap.containsKey(resource.key)

    assertThat(containsKey).isFalse()
  }

  @Test
  fun containsKey_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        val containsKey = resourceMap.exclusiveAccess { it.containsKey(resource.key) }

        assertThat(containsKey).isFalse()
      }

  @Test
  fun containsKey_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_returnsTrue(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        val containsKey = resourceMap.containsKey(resource.key)

        assertThat(containsKey).isTrue()
      }

  @Test
  fun containsKey_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_returnsTrue():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    val containsKey = resourceMap.exclusiveAccess { it.containsKey(resource.key) }

    assertThat(containsKey).isTrue()
  }

  @Test
  fun containsKey_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        val containsKey = resourceMap.containsKey(resource.key)

        assertThat(containsKey).isFalse()
      }

  @Test
  fun containsKey_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_returnsFalse():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    val containsKey = resourceMap.exclusiveAccess { it.containsKey(resource.key) }

    assertThat(containsKey).isFalse()
  }

  @Test
  fun containsKey_calledOnTopLevel_beforeClose_oneInsertedWithOtherKey_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        awaitTestIdle()

        val containsKey = resourceMap.containsKey(resource2.key)

        assertThat(containsKey).isFalse()
      }

  @Test
  fun containsKey_calledViaExclusiveAccess_beforeClose_oneInsertedWithOtherKey_returnsFalse():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    awaitTestIdle()

    val containsKey = resourceMap.exclusiveAccess { it.containsKey(resource2.key) }

    assertThat(containsKey).isFalse()
  }

  @Test
  fun containsKey_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceMap.containsKey(resource.key) }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun containsValue_calledOnTopLevel_beforeClose_nothingInserted_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource_ = createResource()

        val containsValue = resourceMap.containsValue(resource_.value)

        assertThat(containsValue).isFalse()
      }

  @Test
  fun containsValue_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource_ = createResource()

        val containsValue = resourceMap.exclusiveAccess { it.containsValue(resource_.value) }

        assertThat(containsValue).isFalse()
      }

  @Test
  fun containsValue_calledOnTopLevel_beforeClose_oneInsertedWithValueAndNotClosed_returnsTrue():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    val containsValue = resourceMap.containsValue(resource.value)

    assertThat(containsValue).isTrue()
  }

  @Test
  fun containsValue_calledViaExclusiveAccess_beforeClose_oneInsertedWithValueAndNotClosed_returnsTrue():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    val containsValue = resourceMap.exclusiveAccess { it.containsValue(resource.value) }

    assertThat(containsValue).isTrue()
  }

  @Test
  fun containsValue_calledOnTopLevel_beforeClose_oneInsertedWithValueThenClosed_returnsFalse():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    val containsValue = resourceMap.containsValue(resource.value)

    assertThat(containsValue).isFalse()
  }

  @Test
  fun containsValue_calledViaExclusiveAccess_beforeClose_oneInsertedWithValueThenClosed_returnsFalse():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    val containsValue = resourceMap.exclusiveAccess { it.containsValue(resource.value) }

    assertThat(containsValue).isFalse()
  }

  @Test
  fun containsValue_calledOnTopLevel_beforeClose_oneInsertedWithOtherValue_returnsFalse(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource_ = createResource()
        resourceMap.put(resource1.key, resource1.value)
        awaitTestIdle()

        val containsValue = resourceMap.containsValue(resource_.value)

        assertThat(containsValue).isFalse()
      }

  @Test
  fun containsValue_calledViaExclusiveAccess_beforeClose_oneInsertedWithOtherValue_returnsFalse():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource_ = createResource()
    resourceMap.put(resource1.key, resource1.value)
    awaitTestIdle()

    val containsValue = resourceMap.exclusiveAccess { it.containsValue(resource_.value) }

    assertThat(containsValue).isFalse()
  }

  @Test
  fun containsValue_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    val resource_ = createResource()
    resourceMap.close()
    awaitTestIdle()

    val error =
        assertFailsWith<IllegalStateException> { resourceMap.containsValue(resource_.value) }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun get_calledOnTopLevel_beforeClose_nothingInserted_returnsNull(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()

    val retrieved = resourceMap.get(resource.key)

    assertThat(retrieved).isNull()
  }

  @Test
  fun get_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsNull(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()

    val retrieved = resourceMap.exclusiveAccess { it.get(resource.key) }

    assertThat(retrieved).isNull()
  }

  @Test
  fun get_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_returnsValue(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        val retrieved = resourceMap.get(resource.key)

        assertThat(retrieved).isEqualTo(resource.value)
      }

  @Test
  fun get_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_returnsValue(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        val retrieved = resourceMap.exclusiveAccess { it.get(resource.key) }

        assertThat(retrieved).isEqualTo(resource.value)
      }

  @Test
  fun get_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_returnsNull(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        val retrieved = resourceMap.get(resource.key)

        assertThat(retrieved).isNull()
      }

  @Test
  fun get_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_returnsNull(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        val retrieved = resourceMap.exclusiveAccess { it.get(resource.key) }

        assertThat(retrieved).isNull()
      }

  @Test
  fun get_calledOnTopLevel_beforeClose_oneInsertedWithOtherKey_returnsNull(): Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    awaitTestIdle()

    val retrieved = resourceMap.get(resource2.key)

    assertThat(retrieved).isNull()
  }

  @Test
  fun get_calledViaExclusiveAccess_beforeClose_oneInsertedWithOtherKey_returnsNull(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        awaitTestIdle()

        val retrieved = resourceMap.exclusiveAccess { it.get(resource2.key) }

        assertThat(retrieved).isNull()
      }

  @Test
  fun get_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_returnedValueRemainsOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.get(resource.key)

    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun get_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_returnedValueRemainsOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.get(resource.key) }

    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun get_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceMap.get(resource.key) }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_nothingInserted_returnsEmpty(): Unit = runBlocking {
    val resourceMap = subject()

    val all = resourceMap.getAll()

    assertThat(all).isEmpty()
  }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsEmpty(): Unit =
      runBlocking {
        val resourceMap = subject()

        val all = resourceMap.exclusiveAccess { it.getAll() }

        assertThat(all).isEmpty()
      }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_oneInsertedThenClosed_returnsEmpty(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    val all = resourceMap.getAll()

    assertThat(all).isEmpty()
  }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_oneInsertedThenClosed_returnsEmpty(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        val all = resourceMap.exclusiveAccess { it.getAll() }

        assertThat(all).isEmpty()
      }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_returnsAll(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        awaitTestIdle()

        val all = resourceMap.getAll()

        assertThat(all)
            .containsExactlyEntriesIn(
                mapOf(resource1.key to resource1.value, resource2.key to resource2.value))
      }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_multipleInsertedThenOneClosed_returnsOnlyOpen(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        awaitTestIdle()

        val all = resourceMap.getAll()

        assertThat(all).containsExactlyEntriesIn(mapOf(resource2.key to resource2.value))
      }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_multipleInsertedThenAllClosed_returnsEmpty(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        resource2.value.close()
        awaitTestIdle()

        val all = resourceMap.getAll()

        assertThat(all).isEmpty()
      }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_multipleInsertedThenAllClosed_returnsEmpty():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)
    resource1.value.close()
    resource2.value.close()
    awaitTestIdle()

    val all = resourceMap.exclusiveAccess { it.getAll() }

    assertThat(all).isEmpty()
  }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_returnedValuesRemainOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    resourceMap.getAll()
    awaitTestIdle()

    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_returnsAll(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        awaitTestIdle()

        val allResources = resourceMap.exclusiveAccess { it.getAll() }

        assertThat(allResources)
            .containsExactlyEntriesIn(
                mapOf(resource1.key to resource1.value, resource2.key to resource2.value))
      }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_multipleInsertedThenOneClosed_returnsOnlyOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)
    resource1.value.close()
    awaitTestIdle()

    val allResources = resourceMap.exclusiveAccess { it.getAll() }

    assertThat(allResources).containsExactlyEntriesIn(mapOf(resource2.key to resource2.value))
  }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_returnedValuesRemainOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)

    resourceMap.exclusiveAccess { it.getAll() }
    awaitTestIdle()

    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun getAll_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    resourceMap.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceMap.getAll() }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun put_calledOnTopLevel_beforeClose_nothingInserted_newValueIsRetrievable(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()

    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    val retrieved = resourceMap.get(resource.key)

    assertThat(retrieved).isEqualTo(resource.value)
  }

  @Test
  fun put_calledViaExclusiveAccess_beforeClose_nothingInserted_newValueIsRetrievable(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        resourceMap.exclusiveAccess { it.put(resource.key, resource.value) }
        awaitTestIdle()

        val retrieved = resourceMap.get(resource.key)

        assertThat(retrieved).isEqualTo(resource.value)
      }

  @Test
  fun put_calledOnTopLevel_beforeClose_nothingInserted_nullIsReturned(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()

    val previous = resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    assertThat(previous).isNull()
  }

  @Test
  fun put_calledViaExclusiveAccess_beforeClose_nothingInserted_nullIsReturned(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        var previous: V? = resource.value
        resourceMap.exclusiveAccess { previous = it.put(resource.key, resource.value) }
        awaitTestIdle()

        assertThat(previous).isNull()
      }

  @Test
  fun put_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_newValueIsRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.put(resource.key, resource_.value)
    awaitTestIdle()

    val retrieved = resourceMap.get(resource.key)

    assertThat(retrieved).isEqualTo(resource_.value)
  }

  @Test
  fun put_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_newValueIsRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.put(resource.key, resource_.value) }
    awaitTestIdle()

    val retrieved = resourceMap.get(resource.key)

    assertThat(retrieved).isEqualTo(resource_.value)
  }

  @Test
  fun put_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_oldValueIsNotClosed(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        val resource_ = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        resourceMap.put(resource.key, resource_.value)
        awaitTestIdle()

        assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun put_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_oldValueIsNotClosed():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.put(resource.key, resource_.value) }
    awaitTestIdle()

    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun put_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_oldValueIsReturned(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        val resource_ = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        val previous = resourceMap.put(resource.key, resource_.value)
        awaitTestIdle()

        assertThat(previous).isEqualTo(resource.value)
      }

  @Test
  fun put_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_oldValueIsReturned():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    var previous: V? = null
    resourceMap.exclusiveAccess { previous = it.put(resource.key, resource_.value) }
    awaitTestIdle()

    assertThat(previous).isEqualTo(resource.value)
  }

  @Test
  fun put_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_newValueIsRetrievable(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        val resource_ = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        resourceMap.put(resource.key, resource_.value)
        awaitTestIdle()

        val retrieved = resourceMap.get(resource.key)

        assertThat(retrieved).isEqualTo(resource_.value)
      }

  @Test
  fun put_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_newValueIsRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.put(resource.key, resource_.value) }
    awaitTestIdle()

    val retrieved = resourceMap.get(resource.key)

    assertThat(retrieved).isEqualTo(resource_.value)
  }

  @Test
  fun put_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_nullIsReturned(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        val resource_ = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        val previous = resourceMap.put(resource.key, resource_.value)
        awaitTestIdle()

        assertThat(previous).isNull()
      }

  @Test
  fun put_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_nullIsReturned(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        val resource_ = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        var previous: V? = resource_.value
        resourceMap.exclusiveAccess { previous = it.put(resource.key, resource_.value) }
        awaitTestIdle()

        assertThat(previous).isNull()
      }

  @Test
  fun put_calledOnTopLevel_beforeClose_closedResourceProvided_throwsIllegalStateException(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resource.value.close()
        awaitTestIdle()

        val error =
            assertFailsWith<IllegalStateException> { resourceMap.put(resource.key, resource.value) }

        assertThat(error).hasMessageThat().isEqualTo("New resource is not open, cannot insert.")
      }

  @Test
  fun put_calledViaExclusiveAccess_beforeClose_closedResourceProvided_throwsIllegalStateException():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resource.value.close()
    awaitTestIdle()

    val error =
        assertFailsWith<IllegalStateException> {
          resourceMap.exclusiveAccess { it.put(resource.key, resource.value) }
        }

    assertThat(error).hasMessageThat().isEqualTo("New resource is not open, cannot insert.")
  }

  @Test
  fun put_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.close()
    awaitTestIdle()

    val error =
        assertFailsWith<IllegalStateException> { resourceMap.put(resource.key, resource.value) }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_nothingInserted_newValueIsRetrievable(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        resourceMap.getOrPut(resource.key) { resource.value }
        awaitTestIdle()

        assertThat(resourceMap.get(resource.key)).isEqualTo(resource.value)
      }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_nothingInserted_newValueIsReturned(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        val returned = resourceMap.getOrPut(resource.key) { resource.value }
        awaitTestIdle()

        assertThat(returned).isEqualTo(resource.value)
      }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_nothingInserted_newValueIsRetrievable(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        resourceMap.exclusiveAccess { it.getOrPut(resource.key) { resource.value } }
        awaitTestIdle()

        assertThat(resourceMap.get(resource.key)).isEqualTo(resource.value)
      }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_nothingInserted_newValueIsReturned(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        var returned: V? = null
        resourceMap.exclusiveAccess { returned = it.getOrPut(resource.key) { resource.value } }
        awaitTestIdle()

        assertThat(returned).isEqualTo(resource.value)
      }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_nothingInserted_returnedValueRemainsOpen(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        val returned = resourceMap.getOrPut(resource.key) { resource.value }
        awaitTestIdle()

        assertThat(returned.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_nothingInserted_returnedValueRemainsOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()

    var returned: V? = null
    resourceMap.exclusiveAccess { returned = it.getOrPut(resource.key) { resource.value } }
    awaitTestIdle()

    assertThat(returned!!.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_oldValueIsRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.getOrPut(resource.key) { resource_.value }
    awaitTestIdle()

    assertThat(resourceMap.get(resource.key)).isEqualTo(resource.value)
  }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_oldValueIsReturned():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    val returned = resourceMap.getOrPut(resource.key) { resource_.value }
    awaitTestIdle()

    assertThat(returned).isEqualTo(resource.value)
  }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_oldValueIsRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.getOrPut(resource.key) { resource_.value } }
    awaitTestIdle()

    assertThat(resourceMap.get(resource.key)).isEqualTo(resource.value)
  }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_oldValueIsReturned():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    var returned: V? = null
    resourceMap.exclusiveAccess { returned = it.getOrPut(resource.key) { resource_.value } }
    awaitTestIdle()

    assertThat(returned).isEqualTo(resource.value)
  }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_returnedValueRemainsOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    val returned = resourceMap.getOrPut(resource.key) { resource_.value }
    awaitTestIdle()

    assertThat(returned.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_returnedValueRemainsOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    var returned: V? = null
    resourceMap.exclusiveAccess { returned = it.getOrPut(resource.key) { resource_.value } }
    awaitTestIdle()

    assertThat(returned!!.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_newValueIsRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    resourceMap.getOrPut(resource.key) { resource_.value }
    awaitTestIdle()

    assertThat(resourceMap.get(resource.key)).isEqualTo(resource_.value)
  }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_newValueIsReturned():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    val returned = resourceMap.getOrPut(resource.key) { resource_.value }
    awaitTestIdle()

    assertThat(returned).isEqualTo(resource_.value)
  }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_newValueIsRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.getOrPut(resource.key) { resource_.value } }
    awaitTestIdle()

    assertThat(resourceMap.get(resource.key)).isEqualTo(resource_.value)
  }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_newValueIsReturned():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    var returned: V? = null
    resourceMap.exclusiveAccess { returned = it.getOrPut(resource.key) { resource_.value } }
    awaitTestIdle()

    assertThat(returned).isEqualTo(resource_.value)
  }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_providingClosedResource_oldValueIsReturned():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    resource_.value.close()
    awaitTestIdle()

    val returned = resourceMap.getOrPut(resource.key) { resource_.value }
    awaitTestIdle()

    assertThat(returned).isEqualTo(resource.value)
  }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_providingClosedResource_oldValueIsReturned():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    resource_.value.close()
    awaitTestIdle()

    var returned: V? = null
    resourceMap.exclusiveAccess { returned = it.getOrPut(resource.key) { resource_.value } }
    awaitTestIdle()

    assertThat(returned).isEqualTo(resource.value)
  }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_closedResourceProvided_throwsIllegalStateException():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resource.value.close()
    awaitTestIdle()

    val error =
        assertFailsWith<IllegalStateException> {
          resourceMap.getOrPut(resource.key) { resource.value }
        }

    assertThat(error).hasMessageThat().isEqualTo("New resource is not open, cannot insert.")
  }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_closedResourceProvided_throwsIllegalStateException():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resource.value.close()
    awaitTestIdle()

    val error =
        assertFailsWith<IllegalStateException> {
          resourceMap.exclusiveAccess { it.getOrPut(resource.key) { resource.value } }
        }

    assertThat(error).hasMessageThat().isEqualTo("New resource is not open, cannot insert.")
  }

  @Test
  fun getOrPut_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_providingClosedResource_oldValueIsRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    resource_.value.close()
    awaitTestIdle()

    val returned = resourceMap.getOrPut(resource.key) { resource_.value }
    awaitTestIdle()

    assertThat(resourceMap.get(resource.key)).isEqualTo(resource.value)
    assertThat(returned).isEqualTo(resource.value)
  }

  @Test
  fun getOrPut_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_providingClosedResource_oldValueIsRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceMap.put(resource.key, resource.value)
    resource_.value.close()
    awaitTestIdle()

    var returned: V? = null
    resourceMap.exclusiveAccess { returned = it.getOrPut(resource.key) { resource_.value } }
    awaitTestIdle()

    assertThat(resourceMap.get(resource.key)).isEqualTo(resource.value)
    assertThat(returned).isEqualTo(resource.value)
  }

  @Test
  fun getOrPut_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.close()
    awaitTestIdle()

    val error =
        assertFailsWith<IllegalStateException> {
          resourceMap.getOrPut(resource.key) { resource.value }
        }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_valueIsReturned(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        val returned = resourceMap.remove(resource.key)
        awaitTestIdle()

        assertThat(returned).isEqualTo(resource.value)
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_valueIsReturned():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    var returned: V? = null
    resourceMap.exclusiveAccess { returned = it.remove(resource.key) }
    awaitTestIdle()

    assertThat(returned).isEqualTo(resource.value)
  }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_valueRemainsOpen(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        resourceMap.remove(resource.key)
        awaitTestIdle()

        assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_valueRemainsOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.remove(resource.key) }
    awaitTestIdle()

    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_valueIsNotRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.remove(resource.key)
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
    assertThat(resourceMap.get(resource.key)).isNull()
  }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_valueIsNotRetrievable():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.remove(resource.key) }
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
    assertThat(resourceMap.get(resource.key)).isNull()
  }

  @Test
  fun remove_calledOnTopLevel_beforeClose_nothingInserted_doesNotFail(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()

    resourceMap.remove(resource.key)
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
    assertThat(resourceMap.get(resource.key)).isNull()
    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_nothingInserted_doesNotFail(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        resourceMap.exclusiveAccess { it.remove(resource.key) }
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(0)
        assertThat(resourceMap.get(resource.key)).isNull()
        assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithOtherKey_doesNotFail(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        awaitTestIdle()

        resourceMap.remove(resource2.key)
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(1)
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithOtherKey_doesNotFail(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        awaitTestIdle()

        resourceMap.exclusiveAccess { it.remove(resource2.key) }
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(1)
      }

  @Test
  fun remove_calledOnTopLevel_beforeClose_nothingInserted_returnsNull(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()

    val returned = resourceMap.remove(resource.key)
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
    assertThat(resourceMap.get(resource.key)).isNull()
    assertThat(returned).isNull()
    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsNull(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()

        var returned: V? = resource.value
        resourceMap.exclusiveAccess { returned = it.remove(resource.key) }
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(0)
        assertThat(resourceMap.get(resource.key)).isNull()
        assertThat(returned).isNull()
        assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithOtherKey_returnsNull(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        awaitTestIdle()

        val returned = resourceMap.remove(resource2.key)
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(1)
        assertThat(resourceMap.get(resource1.key)).isEqualTo(resource1.value)
        assertThat(returned).isNull()
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithOtherKey_returnsNull(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        awaitTestIdle()

        var returned: V? = resource1.value
        resourceMap.exclusiveAccess { returned = it.remove(resource2.key) }
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(1)
        assertThat(resourceMap.get(resource1.key)).isEqualTo(resource1.value)
        assertThat(returned).isNull()
      }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_returnsNull(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        val returned = resourceMap.remove(resource.key)
        awaitTestIdle()

        assertThat(returned).isNull()
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_returnsNull(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        var returned: V? = resource.value
        resourceMap.exclusiveAccess { returned = it.remove(resource.key) }
        awaitTestIdle()

        assertThat(returned).isNull()
      }

  @Test
  fun remove_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceMap.remove(resource.key) }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_nothingInserted_doesNotFail(): Unit = runBlocking {
    val resourceMap = subject()
    resourceMap.clear()
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
  }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_nothingInserted_doesNotFail(): Unit = runBlocking {
    val resourceMap = subject()
    resourceMap.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_oneInsertedAndNotClosed_untracksButLeavesOpen(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        awaitTestIdle()

        resourceMap.clear()
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(0)
        assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_oneInsertedAndNotClosed_untracksButLeavesOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
    assertThat(resource.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_oneInsertedThenClosed_doesNotFail(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)
    resource.value.close()
    awaitTestIdle()

    resourceMap.clear()
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
  }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_oneInsertedThenClosed_doesNotFail(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)
        resource.value.close()
        awaitTestIdle()

        resourceMap.exclusiveAccess { it.clear() }
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(0)
      }

  @Test
  fun clear_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_untracksAllButLeavesOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)
    awaitTestIdle()

    resourceMap.clear()
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
    assertThat(resource1.value.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resource2.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_untracksAllButLeavesOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
    assertThat(resource1.value.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resource2.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_multipleInsertedThenSomeClosed_untracksAllButLeavesOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)
    resource1.value.close()
    awaitTestIdle()

    resourceMap.clear()
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
    assertThat(resource2.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_multipleInsertedThenSomeClosed_untracksAllButLeavesOpen():
      Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)
    resource1.value.close()
    awaitTestIdle()

    resourceMap.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceMap.size()).isEqualTo(0)
    assertThat(resource2.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_multipleInsertedThenAllClosed_doesNotFail(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        resource2.value.close()
        awaitTestIdle()

        resourceMap.clear()
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(0)
      }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_multipleInsertedThenAllClosed_doesNotFail(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)
        resource1.value.close()
        resource2.value.close()
        awaitTestIdle()

        resourceMap.exclusiveAccess { it.clear() }
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(0)
      }

  @Test
  fun clear_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    resourceMap.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceMap.clear() }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun autonomousEviction_beforeClose_oneResourceClosesExternally_unregistersResource(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource = createResource()
        resourceMap.put(resource.key, resource.value)

        resource.value.close()
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(0)
        assertThat(resourceMap.get(resource.key)).isNull()
        assertThat(resourceMap.containsKey(resource.key)).isFalse()
        assertThat(resourceMap.containsValue(resource.value)).isFalse()
      }

  @Test
  fun autonomousEviction_beforeClose_multipleResourcesCloseExternally_unregistersAll(): Unit =
      runBlocking {
        val resourceMap = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceMap.put(resource1.key, resource1.value)
        resourceMap.put(resource2.key, resource2.value)

        resource1.value.close()
        resource2.value.close()
        awaitTestIdle()

        assertThat(resourceMap.size()).isEqualTo(0)
      }

  @Test
  fun exclusiveAccess_beforeClose_multipleOperations_evaluatesSynchronously(): Unit = runBlocking {
    val resourceMap = subject()
    val resourceA = createResource()
    val resourceB = createResource()
    val resourceC = createResource()
    val resourceD = createResource()
    resourceMap.put(resourceA.key, resourceA.value)
    resourceMap.put(resourceB.key, resourceB.value)

    resourceMap.exclusiveAccess {
      it.put(resourceC.key, resourceC.value)
      it.clear()
      it.put(resourceD.key, resourceD.value)
    }

    assertThat(resourceMap.size()).isEqualTo(1)
    assertThat(resourceMap.get(resourceD.key)).isEqualTo(resourceD.value)
    assertThat(resourceA.value.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resourceB.value.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resourceC.value.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun exclusiveAccess_beforeClose_operatorLeaked_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    var leakedOperator: ResourceMap.Operator<K, V>? = null

    resourceMap.exclusiveAccess { leakedOperator = it }
    val error =
        assertFailsWith<IllegalStateException> {
          leakedOperator!!.put(resource.key, resource.value)
        }

    assertThat(error)
        .hasMessageThat()
        .isEqualTo(
            "This operator has expired. Each operator should only be used in the exclusiveAccess callback that supplied it, and operators should not be retained after the callback exits.")
  }

  @Test
  fun exclusiveAccess_onClose_withSuspendedBlock_cancelsCoroutine(): Unit = runBlocking {
    val resourceMap = subject()
    val blocker = CompletableDeferred<Unit>()
    val started = CompletableDeferred<Unit>()
    var completedNormally = false

    val job = launch {
      try {
        resourceMap.exclusiveAccess {
          started.complete(Unit)
          blocker.await()
        }
        completedNormally = true
      } catch (e: Exception) {
        // Ignored, we just want to ensure it didn't complete normally
      }
    }

    started.await()
    resourceMap.close()
    awaitTestIdle()

    assertThat(completedNormally).isEqualTo(false)
    assertThat(job.isCompleted).isEqualTo(true)
  }

  @Test
  fun exclusiveAccess_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceMap = subject()
    resourceMap.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceMap.exclusiveAccess {} }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  /** Returns the subject under test. Must return the same instance on each call. */
  @Test
  fun close_noneInserted_noFailures(): Unit = runBlocking {
    val resourceMap = subject()

    resourceMap.close()
    awaitTestIdle()

    assertThat(resourceMap.closureStatus.value).isEqualTo(Status.CLOSED)
  }

  @Test
  fun close_oneInserted_closesInserted(): Unit = runBlocking {
    val resourceMap = subject()
    val resource = createResource()
    resourceMap.put(resource.key, resource.value)

    resourceMap.close()
    awaitTestIdle()

    assertThat(resourceMap.closureStatus.value).isEqualTo(Status.CLOSED)
    assertThat(resource.value.closureStatus.value).isEqualTo(Status.CLOSED)
  }

  @Test
  fun close_multipleInserted_closesInserted(): Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    resourceMap.put(resource2.key, resource2.value)

    resourceMap.close()
    awaitTestIdle()

    assertThat(resourceMap.closureStatus.value).isEqualTo(Status.CLOSED)
    assertThat(resource1.value.closureStatus.value).isEqualTo(Status.CLOSED)
    assertThat(resource2.value.closureStatus.value).isEqualTo(Status.CLOSED)
  }

  @Test
  fun getAll_calledOnTopLevel_insertionDoesNotReceiveUpdates(): Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    val all = resourceMap.getAll()

    val resource2 = createResource()
    resourceMap.put(resource2.key, resource2.value)
    awaitTestIdle()

    assertThat(all).containsExactlyEntriesIn(mapOf(resource1.key to resource1.value))
  }

  @Test
  fun getAll_calledViaExclusiveAccess_insertionDoesNotReceiveUpdates(): Unit = runBlocking {
    val resourceMap = subject()
    val resource1 = createResource()
    resourceMap.put(resource1.key, resource1.value)
    val all = resourceMap.exclusiveAccess { it.getAll() }

    val resource2 = createResource()
    resourceMap.put(resource2.key, resource2.value)
    awaitTestIdle()

    assertThat(all).containsExactlyEntriesIn(mapOf(resource1.key to resource1.value))
  }

  abstract fun subject(): ResourceMap<K, V>

  /** Creates a new key-value pair for use in the tests. */
  abstract fun createResource(): ResourceItem<K, V>

  /**
   * Called whenever the test suite requires asynchronous background processes (like manager
   * evictions) to settle before evaluating invariants. Must suspend until idle.
   */
  abstract suspend fun awaitTestIdle()

  data class ResourceItem<K, V>(val key: K, val value: V)
}
