package com.jackbradshaw.closet.resourcemanager.set

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosable.Status
import kotlin.test.assertFailsWith
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Abstract tests that all [ResourceSet]s should pass.
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
abstract class ResourceSetTest<V : ObservableClosable> {

  @Test
  fun size_calledOnTopLevel_beforeClose_nothingInserted_returnsZero(): Unit = runBlocking {
    val resourceSet = subject()

    val size = resourceSet.size()

    assertThat(size).isEqualTo(0)
  }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsZero(): Unit = runBlocking {
    val resourceSet = subject()

    val size = resourceSet.exclusiveAccess { it.size() }

    assertThat(size).isEqualTo(0)
  }

  @Test
  fun size_calledOnTopLevel_beforeClose_oneInsertedAndNotClosed_returnsOne(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    awaitTestIdle()

    val size = resourceSet.size()

    assertThat(size).isEqualTo(1)
  }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_oneInsertedAndNotClosed_returnsOne(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        awaitTestIdle()

        val size = resourceSet.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(1)
      }

  @Test
  fun size_calledOnTopLevel_beforeClose_oneInsertedThenClosed_returnsZero(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    resource.close()
    awaitTestIdle()

    val size = resourceSet.size()

    assertThat(size).isEqualTo(0)
  }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_oneInsertedThenClosed_returnsZero(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        resource.close()
        awaitTestIdle()

        val size = resourceSet.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(0)
      }

  @Test
  fun size_calledOnTopLevel_beforeClose_multipleInsertedThenOneClosed_returnsOne(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        awaitTestIdle()

        val size = resourceSet.size()

        assertThat(size).isEqualTo(1)
      }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_multipleInsertedThenOneClosed_returnsOne(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        awaitTestIdle()

        val size = resourceSet.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(1)
      }

  @Test
  fun size_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_returnsTwo(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        awaitTestIdle()

        val size = resourceSet.size()

        assertThat(size).isEqualTo(2)
      }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_returnsTwo(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        awaitTestIdle()

        val size = resourceSet.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(2)
      }

  @Test
  fun size_calledOnTopLevel_beforeClose_multipleInsertedThenAllClosed_returnsZero(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        resource2.close()
        awaitTestIdle()

        val size = resourceSet.size()

        assertThat(size).isEqualTo(0)
      }

  @Test
  fun size_calledViaExclusiveAccess_beforeClose_multipleInsertedThenAllClosed_returnsZero(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        resource2.close()
        awaitTestIdle()

        val size = resourceSet.exclusiveAccess { it.size() }

        assertThat(size).isEqualTo(0)
      }

  @Test
  fun size_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceSet = subject()
    resourceSet.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceSet.size() }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_nothingInserted_returnsTrue(): Unit = runBlocking {
    val resourceSet = subject()

    val isEmpty = resourceSet.isEmpty()

    assertThat(isEmpty).isTrue()
  }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsTrue(): Unit =
      runBlocking {
        val resourceSet = subject()

        val isEmpty = resourceSet.exclusiveAccess { it.isEmpty() }

        assertThat(isEmpty).isTrue()
      }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_oneInsertedAndNotClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        awaitTestIdle()

        val isEmpty = resourceSet.isEmpty()

        assertThat(isEmpty).isFalse()
      }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_oneInsertedAndNotClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        awaitTestIdle()

        val isEmpty = resourceSet.exclusiveAccess { it.isEmpty() }

        assertThat(isEmpty).isFalse()
      }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_oneInsertedThenClosed_returnsTrue(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    resource.close()
    awaitTestIdle()

    val isEmpty = resourceSet.isEmpty()

    assertThat(isEmpty).isTrue()
  }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_oneInsertedThenClosed_returnsTrue(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        resource.close()
        awaitTestIdle()

        val isEmpty = resourceSet.exclusiveAccess { it.isEmpty() }

        assertThat(isEmpty).isTrue()
      }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_multipleInsertedThenOneClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        awaitTestIdle()

        val isEmpty = resourceSet.isEmpty()

        assertThat(isEmpty).isFalse()
      }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_multipleInsertedThenOneClosed_returnsFalse():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)
    resource1.close()
    awaitTestIdle()

    val isEmpty = resourceSet.exclusiveAccess { it.isEmpty() }

    assertThat(isEmpty).isFalse()
  }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        awaitTestIdle()

        val isEmpty = resourceSet.isEmpty()

        assertThat(isEmpty).isFalse()
      }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_returnsFalse():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)
    awaitTestIdle()

    val isEmpty = resourceSet.exclusiveAccess { it.isEmpty() }

    assertThat(isEmpty).isFalse()
  }

  @Test
  fun isEmpty_calledOnTopLevel_beforeClose_multipleInsertedThenAllClosed_returnsTrue(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        resource2.close()
        awaitTestIdle()

        val isEmpty = resourceSet.isEmpty()

        assertThat(isEmpty).isTrue()
      }

  @Test
  fun isEmpty_calledViaExclusiveAccess_beforeClose_multipleInsertedThenAllClosed_returnsTrue():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)
    resource1.close()
    resource2.close()
    awaitTestIdle()

    val isEmpty = resourceSet.exclusiveAccess { it.isEmpty() }

    assertThat(isEmpty).isTrue()
  }

  @Test
  fun isEmpty_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceSet = subject()
    resourceSet.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceSet.isEmpty() }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun contains_calledOnTopLevel_beforeClose_nothingInserted_returnsFalse(): Unit = runBlocking {
    val resourceSet = subject()
    val resource_ = createResource()

    val containsValue = resourceSet.contains(resource_)

    assertThat(containsValue).isFalse()
  }

  @Test
  fun contains_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource_ = createResource()

        val containsValue = resourceSet.exclusiveAccess { it.contains(resource_) }

        assertThat(containsValue).isFalse()
      }

  @Test
  fun contains_calledOnTopLevel_beforeClose_oneInsertedWithValueAndNotClosed_returnsTrue(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        awaitTestIdle()

        val containsValue = resourceSet.contains(resource)

        assertThat(containsValue).isTrue()
      }

  @Test
  fun contains_calledViaExclusiveAccess_beforeClose_oneInsertedWithValueAndNotClosed_returnsTrue():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    awaitTestIdle()

    val containsValue = resourceSet.exclusiveAccess { it.contains(resource) }

    assertThat(containsValue).isTrue()
  }

  @Test
  fun contains_calledOnTopLevel_beforeClose_oneInsertedWithValueThenClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        resource.close()
        awaitTestIdle()

        val containsValue = resourceSet.contains(resource)

        assertThat(containsValue).isFalse()
      }

  @Test
  fun contains_calledViaExclusiveAccess_beforeClose_oneInsertedWithValueThenClosed_returnsFalse():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    resource.close()
    awaitTestIdle()

    val containsValue = resourceSet.exclusiveAccess { it.contains(resource) }

    assertThat(containsValue).isFalse()
  }

  @Test
  fun contains_calledOnTopLevel_beforeClose_oneInsertedWithOtherValue_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource_ = createResource()
        resourceSet.add(resource1)
        awaitTestIdle()

        val containsValue = resourceSet.contains(resource_)

        assertThat(containsValue).isFalse()
      }

  @Test
  fun contains_calledViaExclusiveAccess_beforeClose_oneInsertedWithOtherValue_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource_ = createResource()
        resourceSet.add(resource1)
        awaitTestIdle()

        val containsValue = resourceSet.exclusiveAccess { it.contains(resource_) }

        assertThat(containsValue).isFalse()
      }

  @Test
  fun contains_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceSet = subject()
    val resource_ = createResource()
    resourceSet.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceSet.contains(resource_) }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_nothingInserted_returnsEmpty(): Unit = runBlocking {
    val resourceSet = subject()

    val all = resourceSet.getAll()

    assertThat(all).isEmpty()
  }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsEmpty(): Unit =
      runBlocking {
        val resourceSet = subject()

        val all = resourceSet.exclusiveAccess { it.getAll() }

        assertThat(all).isEmpty()
      }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_oneInsertedThenClosed_returnsEmpty(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    resource.close()
    awaitTestIdle()

    val all = resourceSet.getAll()

    assertThat(all).isEmpty()
  }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_oneInsertedThenClosed_returnsEmpty(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        resource.close()
        awaitTestIdle()

        val all = resourceSet.exclusiveAccess { it.getAll() }

        assertThat(all).isEmpty()
      }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_returnsAll(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        awaitTestIdle()

        val all = resourceSet.getAll()

        assertThat(all).containsExactly(resource1, resource2)
      }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_multipleInsertedThenOneClosed_returnsOnlyOpen(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        awaitTestIdle()

        val all = resourceSet.getAll()

        assertThat(all).containsExactly(resource2)
      }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_multipleInsertedThenAllClosed_returnsEmpty(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        resource2.close()
        awaitTestIdle()

        val all = resourceSet.getAll()

        assertThat(all).isEmpty()
      }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_multipleInsertedThenAllClosed_returnsEmpty():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)
    resource1.close()
    resource2.close()
    awaitTestIdle()

    val all = resourceSet.exclusiveAccess { it.getAll() }

    assertThat(all).isEmpty()
  }

  @Test
  fun getAll_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_returnedValuesRemainOpen():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    resourceSet.getAll()
    awaitTestIdle()

    assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_returnsAll(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        awaitTestIdle()

        val allResources = resourceSet.exclusiveAccess { it.getAll() }

        assertThat(allResources).containsExactly(resource1, resource2)
      }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_multipleInsertedThenOneClosed_returnsOnlyOpen():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)
    resource1.close()
    awaitTestIdle()

    val allResources = resourceSet.exclusiveAccess { it.getAll() }

    assertThat(allResources).containsExactly(resource2)
  }

  @Test
  fun getAll_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_returnedValuesRemainOpen():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)

    resourceSet.exclusiveAccess { it.getAll() }
    awaitTestIdle()

    assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun getAll_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceSet = subject()
    resourceSet.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceSet.getAll() }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun add_calledOnTopLevel_beforeClose_nothingInserted_newValueIsRetrievable(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()

    resourceSet.add(resource)
    awaitTestIdle()
  }

  @Test
  fun add_calledViaExclusiveAccess_beforeClose_nothingInserted_newValueIsRetrievable(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()

        resourceSet.exclusiveAccess { it.add(resource) }
        awaitTestIdle()
      }

  @Test
  fun add_calledOnTopLevel_beforeClose_nothingInserted_trueIsReturned(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()

    val previous = resourceSet.add(resource)
    awaitTestIdle()

    assertThat(previous).isTrue()
  }

  @Test
  fun add_calledViaExclusiveAccess_beforeClose_nothingInserted_trueIsReturned(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()

        var previous: Boolean? = null
        resourceSet.exclusiveAccess { previous = it.add(resource) }
        awaitTestIdle()

        assertThat(previous).isTrue()
      }

  @Test
  fun add_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_newValueIsRetrievable():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceSet.add(resource)
    awaitTestIdle()

    resourceSet.add(resource_)
    awaitTestIdle()
  }

  @Test
  fun add_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_newValueIsRetrievable():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceSet.add(resource)
    awaitTestIdle()

    resourceSet.exclusiveAccess { it.add(resource_) }
    awaitTestIdle()
  }

  @Test
  fun add_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_newValueIsRetrievable(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        val resource_ = createResource()
        resourceSet.add(resource)
        resource.close()
        awaitTestIdle()

        resourceSet.add(resource_)
        awaitTestIdle()
      }

  @Test
  fun add_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_newValueIsRetrievable():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    val resource_ = createResource()
    resourceSet.add(resource)
    resource.close()
    awaitTestIdle()

    resourceSet.exclusiveAccess { it.add(resource_) }
    awaitTestIdle()
  }

  @Test
  fun add_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_trueIsReturned(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        val resource_ = createResource()
        resourceSet.add(resource)
        resource.close()
        awaitTestIdle()

        val previous = resourceSet.add(resource_)
        awaitTestIdle()

        assertThat(previous).isTrue()
      }

  @Test
  fun add_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_trueIsReturned(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        val resource_ = createResource()
        resourceSet.add(resource)
        resource.close()
        awaitTestIdle()

        var previous: Boolean? = null
        resourceSet.exclusiveAccess { previous = it.add(resource_) }
        awaitTestIdle()

        assertThat(previous).isTrue()
      }

  @Test
  fun add_calledOnTopLevel_beforeClose_closedResourceProvided_throwsIllegalStateException(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resource.close()
        awaitTestIdle()

        val error = assertFailsWith<IllegalStateException> { resourceSet.add(resource) }

        assertThat(error).hasMessageThat().isEqualTo("New resource is not open, cannot insert.")
      }

  @Test
  fun add_calledViaExclusiveAccess_beforeClose_closedResourceProvided_throwsIllegalStateException():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resource.close()
    awaitTestIdle()

    val error =
        assertFailsWith<IllegalStateException> { resourceSet.exclusiveAccess { it.add(resource) } }

    assertThat(error).hasMessageThat().isEqualTo("New resource is not open, cannot insert.")
  }

  @Test
  fun add_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceSet.add(resource) }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_returnsTrue(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        awaitTestIdle()

        val returned = resourceSet.remove(resource)
        awaitTestIdle()

        assertThat(returned).isTrue()
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_returnsTrue():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    awaitTestIdle()

    var returned: Boolean? = null
    resourceSet.exclusiveAccess { returned = it.remove(resource) }
    awaitTestIdle()

    assertThat(returned).isTrue()
  }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_valueRemainsOpen(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        awaitTestIdle()

        resourceSet.remove(resource)
        awaitTestIdle()

        assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_valueRemainsOpen():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    awaitTestIdle()

    resourceSet.exclusiveAccess { it.remove(resource) }
    awaitTestIdle()

    assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithKeyAndNotClosed_valueIsNotRetrievable():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    awaitTestIdle()

    resourceSet.remove(resource)
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
  }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyAndNotClosed_valueIsNotRetrievable():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    awaitTestIdle()

    resourceSet.exclusiveAccess { it.remove(resource) }
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
  }

  @Test
  fun remove_calledOnTopLevel_beforeClose_nothingInserted_doesNotFail(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()

    resourceSet.remove(resource)
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
    assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_nothingInserted_doesNotFail(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()

        resourceSet.exclusiveAccess { it.remove(resource) }
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(0)
        assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithOtherKey_doesNotFail(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        awaitTestIdle()

        resourceSet.remove(resource2)
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(1)
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithOtherKey_doesNotFail(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        awaitTestIdle()

        resourceSet.exclusiveAccess { it.remove(resource2) }
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(1)
      }

  @Test
  fun remove_calledOnTopLevel_beforeClose_nothingInserted_returnsFalse(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()

    val returned = resourceSet.remove(resource)
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
    assertThat(returned).isFalse()
    assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_nothingInserted_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()

        var returned: Boolean? = null
        resourceSet.exclusiveAccess { returned = it.remove(resource) }
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(0)
        assertThat(returned).isFalse()
        assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithOtherKey_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        awaitTestIdle()

        val returned = resourceSet.remove(resource2)
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(1)
        assertThat(returned).isFalse()
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithOtherKey_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        awaitTestIdle()

        var returned: Boolean? = null
        resourceSet.exclusiveAccess { returned = it.remove(resource2) }
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(1)
        assertThat(returned).isFalse()
      }

  @Test
  fun remove_calledOnTopLevel_beforeClose_oneInsertedWithKeyThenClosed_returnsFalse(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        resource.close()
        awaitTestIdle()

        val returned = resourceSet.remove(resource)
        awaitTestIdle()

        assertThat(returned).isFalse()
      }

  @Test
  fun remove_calledViaExclusiveAccess_beforeClose_oneInsertedWithKeyThenClosed_returnsFalse():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    resource.close()
    awaitTestIdle()

    var returned: Boolean? = null
    resourceSet.exclusiveAccess { returned = it.remove(resource) }
    awaitTestIdle()

    assertThat(returned).isFalse()
  }

  @Test
  fun remove_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceSet.remove(resource) }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_nothingInserted_doesNotFail(): Unit = runBlocking {
    val resourceSet = subject()
    resourceSet.clear()
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
  }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_nothingInserted_doesNotFail(): Unit = runBlocking {
    val resourceSet = subject()
    resourceSet.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_oneInsertedAndNotClosed_untracksButLeavesOpen(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        awaitTestIdle()

        resourceSet.clear()
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(0)
        assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
      }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_oneInsertedAndNotClosed_untracksButLeavesOpen():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    awaitTestIdle()

    resourceSet.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
    assertThat(resource.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_oneInsertedThenClosed_doesNotFail(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)
    resource.close()
    awaitTestIdle()

    resourceSet.clear()
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
  }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_oneInsertedThenClosed_doesNotFail(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)
        resource.close()
        awaitTestIdle()

        resourceSet.exclusiveAccess { it.clear() }
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(0)
      }

  @Test
  fun clear_calledOnTopLevel_beforeClose_multipleInsertedAndNotClosed_untracksAllButLeavesOpen():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)
    awaitTestIdle()

    resourceSet.clear()
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
    assertThat(resource1.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resource2.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_multipleInsertedAndNotClosed_untracksAllButLeavesOpen():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)
    awaitTestIdle()

    resourceSet.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
    assertThat(resource1.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resource2.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_multipleInsertedThenSomeClosed_untracksAllButLeavesOpen():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)
    resource1.close()
    awaitTestIdle()

    resourceSet.clear()
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
    assertThat(resource2.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_multipleInsertedThenSomeClosed_untracksAllButLeavesOpen():
      Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)
    resource1.close()
    awaitTestIdle()

    resourceSet.exclusiveAccess { it.clear() }
    awaitTestIdle()

    assertThat(resourceSet.size()).isEqualTo(0)
    assertThat(resource2.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun clear_calledOnTopLevel_beforeClose_multipleInsertedThenAllClosed_doesNotFail(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        resource2.close()
        awaitTestIdle()

        resourceSet.clear()
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(0)
      }

  @Test
  fun clear_calledViaExclusiveAccess_beforeClose_multipleInsertedThenAllClosed_doesNotFail(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)
        resource1.close()
        resource2.close()
        awaitTestIdle()

        resourceSet.exclusiveAccess { it.clear() }
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(0)
      }

  @Test
  fun clear_calledOnTopLevel_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceSet = subject()
    resourceSet.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceSet.clear() }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  @Test
  fun autonomousEviction_beforeClose_oneResourceClosesExternally_unregistersResource(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource = createResource()
        resourceSet.add(resource)

        resource.close()
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(0)
        assertThat(resourceSet.contains(resource)).isFalse()
      }

  @Test
  fun autonomousEviction_beforeClose_multipleResourcesCloseExternally_unregistersAll(): Unit =
      runBlocking {
        val resourceSet = subject()
        val resource1 = createResource()
        val resource2 = createResource()
        resourceSet.add(resource1)
        resourceSet.add(resource2)

        resource1.close()
        resource2.close()
        awaitTestIdle()

        assertThat(resourceSet.size()).isEqualTo(0)
      }

  @Test
  fun exclusiveAccess_beforeClose_multipleOperations_evaluatesSynchronously(): Unit = runBlocking {
    val resourceSet = subject()
    val resourceA = createResource()
    val resourceB = createResource()
    val resourceC = createResource()
    val resourceD = createResource()
    resourceSet.add(resourceA)
    resourceSet.add(resourceB)

    resourceSet.exclusiveAccess {
      it.add(resourceC)
      it.clear()
      it.add(resourceD)
    }

    assertThat(resourceSet.size()).isEqualTo(1)
    assertThat(resourceA.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resourceB.closureStatus.value).isEqualTo(Status.OPEN)
    assertThat(resourceC.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun exclusiveAccess_beforeClose_operatorLeaked_throwsIllegalStateException(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    var leakedOperator: ResourceSet.Operator<V>? = null

    resourceSet.exclusiveAccess { leakedOperator = it }
    val error = assertFailsWith<IllegalStateException> { leakedOperator!!.add(resource) }

    assertThat(error)
        .hasMessageThat()
        .isEqualTo(
            "This operator has expired. Each operator should only be used in the exclusiveAccess callback that supplied it, and operators should not be retained after the callback exits.")
  }

  @Test
  fun exclusiveAccess_onClose_withSuspendedBlock_cancelsCoroutine(): Unit = runBlocking {
    val resourceSet = subject()
    val blocker = CompletableDeferred<Unit>()
    val started = CompletableDeferred<Unit>()
    var completedNormally = false

    val job = launch {
      try {
        resourceSet.exclusiveAccess {
          started.complete(Unit)
          blocker.await()
        }
        completedNormally = true
      } catch (e: Exception) {
        // Ignored, we just want to ensure it didn't complete normally
      }
    }

    started.await()
    resourceSet.close()
    awaitTestIdle()

    assertThat(completedNormally).isEqualTo(false)
    assertThat(job.isCompleted).isEqualTo(true)
  }

  @Test
  fun exclusiveAccess_afterClose_throwsIllegalStateException(): Unit = runBlocking {
    val resourceSet = subject()
    resourceSet.close()
    awaitTestIdle()

    val error = assertFailsWith<IllegalStateException> { resourceSet.exclusiveAccess {} }

    assertThat(error).hasMessageThat().isEqualTo("This resource is not open.")
  }

  /** Returns the subject under test. Must return the same instance on each call. */
  @Test
  fun close_noneInserted_noFailures(): Unit = runBlocking {
    val resourceSet = subject()

    resourceSet.close()
    awaitTestIdle()

    assertThat(resourceSet.closureStatus.value).isEqualTo(Status.CLOSED)
  }

  @Test
  fun close_oneInserted_closesInserted(): Unit = runBlocking {
    val resourceSet = subject()
    val resource = createResource()
    resourceSet.add(resource)

    resourceSet.close()
    awaitTestIdle()

    assertThat(resourceSet.closureStatus.value).isEqualTo(Status.CLOSED)
    assertThat(resource.closureStatus.value).isEqualTo(Status.CLOSED)
  }

  @Test
  fun close_multipleInserted_closesInserted(): Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    val resource2 = createResource()
    resourceSet.add(resource1)
    resourceSet.add(resource2)

    resourceSet.close()
    awaitTestIdle()

    assertThat(resourceSet.closureStatus.value).isEqualTo(Status.CLOSED)
    assertThat(resource1.closureStatus.value).isEqualTo(Status.CLOSED)
    assertThat(resource2.closureStatus.value).isEqualTo(Status.CLOSED)
  }

  @Test
  fun getAll_calledOnTopLevel_insertionDoesNotReceiveUpdates(): Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    resourceSet.add(resource1)
    val all = resourceSet.getAll()

    val resource2 = createResource()
    resourceSet.add(resource2)
    awaitTestIdle()

    assertThat(all).containsExactly(resource1)
  }

  @Test
  fun getAll_calledViaExclusiveAccess_insertionDoesNotReceiveUpdates(): Unit = runBlocking {
    val resourceSet = subject()
    val resource1 = createResource()
    resourceSet.add(resource1)
    val all = resourceSet.exclusiveAccess { it.getAll() }

    val resource2 = createResource()
    resourceSet.add(resource2)
    awaitTestIdle()

    assertThat(all).containsExactly(resource1)
  }

  abstract fun subject(): ResourceSet<V>

  /** Creates a new key-value pair for use in the tests. */
  abstract fun createResource(): V

  /**
   * Called whenever the test suite requires asynchronous background processes (like manager
   * evictions) to settle before evaluating invariants. Must suspend until idle.
   */
  abstract suspend fun awaitTestIdle()
}
