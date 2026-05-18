package com.jackbradshaw.closet.rule

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.suspending.SuspendableClosable
import kotlin.test.assertFailsWith
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.Description
import org.junit.runners.model.Statement

/** Abstract tests that all [AutoCloseRule] instances should pass. */
abstract class AutoCloseRuleTest {

  private val testScopeHandle = Job()

  private val testScope by lazy { CoroutineScope(testDispatcher() + testScopeHandle) }

  @After
  open fun tearDown() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  @Test
  fun onClose_noClosables_doesNotFail() {
    val rule = subject()

    rule
        .apply(
            object : Statement() {
              override fun evaluate() {}
            },
            Description.EMPTY)
        .evaluate()

    // If here no error occurred
  }

  @Test
  fun onClose_multipleAutoClosablesRegistered_closesAll() {
    val rule = subject()
    val resource1 = BasicAutoCloseable()
    val resource2 = BasicAutoCloseable()

    rule.register(resource1)
    rule.register(resource2)

    rule
        .apply(
            object : Statement() {
              override fun evaluate() {}
            },
            Description.EMPTY)
        .evaluate()

    assertThat(resource1.isClosed).isTrue()
    assertThat(resource2.isClosed).isTrue()
  }

  @Test
  fun onClose_multipleSuspendableClosablesRegistered_closesAll() {
    val rule = subject()
    val resource1 = BasicSuspendableClosable()
    val resource2 = BasicSuspendableClosable()

    rule.register(resource1)
    rule.register(resource2)

    rule
        .apply(
            object : Statement() {
              override fun evaluate() {}
            },
            Description.EMPTY)
        .evaluate()

    assertThat(resource1.isClosed).isTrue()
    assertThat(resource2.isClosed).isTrue()
  }

  @Test
  fun onClose_mixOfResourceTypes_closesAll() {
    val rule = subject()
    val resource1 = BasicAutoCloseable()
    val resource2 = BasicSuspendableClosable()

    rule.register(resource1)
    rule.register(resource2)

    rule
        .apply(
            object : Statement() {
              override fun evaluate() {}
            },
            Description.EMPTY)
        .evaluate()

    assertThat(resource1.isClosed).isTrue()
    assertThat(resource2.isClosed).isTrue()
  }

  @Test
  fun onClose_failingAutoClosable_closesOthersAndThrows() {
    val rule = subject()
    val failingResource =
        object : AutoCloseable {
          override fun close() = throw RuntimeException("AutoCloseable failed")
        }
    val nonFailingResource = BasicAutoCloseable()

    rule.register(failingResource)
    rule.register(nonFailingResource)

    val e =
        assertFailsWith<CompositeException> {
          rule
              .apply(
                  object : Statement() {
                    override fun evaluate() {}
                  },
                  Description.EMPTY)
              .evaluate()
        }

    assertThat(e).hasMessageThat().isEqualTo("Some closables failed to close")
    assertThat(e.exceptions).hasSize(1)
    assertThat(e.exceptions.first()).hasMessageThat().isEqualTo("AutoCloseable failed")
    assertThat(nonFailingResource.isClosed).isTrue()
  }

  @Test
  fun onClose_failingSuspendableClosable_closesOthersAndThrows() {
    val rule = subject()
    val failingResource =
        object : SuspendableClosable {
          override suspend fun close() = throw RuntimeException("SuspendableClosable failed")
        }
    val nonFailingResource = BasicSuspendableClosable()

    rule.register(failingResource)
    rule.register(nonFailingResource)

    val e =
        assertFailsWith<CompositeException> {
          rule
              .apply(
                  object : Statement() {
                    override fun evaluate() {}
                  },
                  Description.EMPTY)
              .evaluate()
        }

    assertThat(e).hasMessageThat().isEqualTo("Some closables failed to close")
    assertThat(e.exceptions).hasSize(1)
    assertThat(e.exceptions.first()).hasMessageThat().isEqualTo("SuspendableClosable failed")
    assertThat(nonFailingResource.isClosed).isTrue()
  }

  @Test
  fun onClose_mixOfFailingResouceTypes_closesOthersAndThrowsAll() {
    val rule = subject()

    val failingAutoCloseable =
        object : AutoCloseable {
          override fun close() = throw RuntimeException("AutoCloseable failed")
        }
    val failingSuspendableClosable =
        object : SuspendableClosable {
          override suspend fun close() = throw RuntimeException("SuspendableClosable failed")
        }

    val nonFailingResource1 = BasicAutoCloseable()
    val nonFailingResource2 = BasicSuspendableClosable()

    rule.register(failingAutoCloseable)
    rule.register(nonFailingResource1)
    rule.register(failingSuspendableClosable)
    rule.register(nonFailingResource2)

    val e =
        assertFailsWith<CompositeException> {
          rule
              .apply(
                  object : Statement() {
                    override fun evaluate() {}
                  },
                  Description.EMPTY)
              .evaluate()
        }

    assertThat(e).hasMessageThat().isEqualTo("Some closables failed to close")
    assertThat(e.exceptions).hasSize(2)
    val messages = e.exceptions.map { it.message }
    assertThat(messages).containsExactly("AutoCloseable failed", "SuspendableClosable failed")

    assertThat(nonFailingResource1.isClosed).isTrue()
    assertThat(nonFailingResource2.isClosed).isTrue()
  }

  @Test
  fun register_autoCloseable_returnsPassedInValue() {
    val rule = subject()
    val resource = BasicAutoCloseable()

    val returnedResource = rule.register(resource)

    assertThat(returnedResource).isSameInstanceAs(resource)
  }

  @Test
  fun register_suspendableClosable_returnsPassedInValue() {
    val rule = subject()
    val resource = BasicSuspendableClosable()

    val returnedResource = rule.register(resource)

    assertThat(returnedResource).isSameInstanceAs(resource)
  }

  /** Adds jobs concurrently to detect write races dropping registrations. */
  @Test
  fun register_isThreadSafe() = runBlocking {
    val rule = subject()

    // Safe to use non-atomic values because AutoCloseRule contract closes resources sequentially.
    var autoClosableClosedCount = 0
    var suspendingClosableClosedCount = 0

    val jobs =
        List(10) {
          testScope.launch {
            repeat(100) {
              rule.register(AutoCloseable { autoClosableClosedCount++ })
              rule.register(SuspendableClosable { suspendingClosableClosedCount++ })
            }
          }
        }
    jobs.joinAll()

    rule
        .apply(
            object : Statement() {
              override fun evaluate() {}
            },
            Description.EMPTY)
        .evaluate()

    assertThat(autoClosableClosedCount).isEqualTo(1000)
    assertThat(suspendingClosableClosedCount).isEqualTo(1000)
  }

  abstract fun subject(): AutoCloseRule

  abstract fun testDispatcher(): CoroutineDispatcher

  private class BasicAutoCloseable : AutoCloseable {
    var isClosed = false

    override fun close() {
      isClosed = true
    }
  }

  private class BasicSuspendableClosable : SuspendableClosable {
    var isClosed = false

    override suspend fun close() {
      isClosed = true
    }
  }
}
