package com.jackbradshaw.closet.rule

import com.google.common.truth.Truth.assertThat
import kotlin.test.assertFailsWith
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

/** Abstract tests that every [ClosetRule] should pass. */
abstract class ClosetRuleTest<T : AutoCloseable> {

  @Test
  fun get_returnsSameObjectEachCall() {
    setupSubject()
    val rule = subject()

    val resource1 = rule.get()
    val resource2 = rule.get()

    assertThat(resource1).isSameInstanceAs(resource2)
  }

  @Test
  fun get_providesOpenResource() {
    setupSubject()
    val rule = subject()

    val resource1 = rule.get()

    assertIsOpen(resource1)
  }

  @Test
  fun get_failsAfterClose() {
    setupSubject()
    val rule = subject()

    rule.proceedThroughTest()

    val error = assertFailsWith<IllegalArgumentException> { rule.get() }
    assertThat(error.message).isEqualTo("Cannot use get() after test case has run.")
  }

  @Test
  fun afterClosed_retainedResourcesAreClosed() {
    setupSubject()
    val rule = subject()
    val resource1 = rule.get()

    rule.proceedThroughTest()

    assertIsClosed(resource1)
  }

  /**
   * Sets up the [subject].
   *
   * Must be called exactly once per test.
   */
  protected abstract fun setupSubject()

  /**
   * Provides the subject under test.
   *
   * Must return the same object on each call.
   */
  protected abstract fun subject(): ClosetRule<T>

  /** Asserts that [resource] is open. */
  protected abstract fun assertIsOpen(resource: T)

  /** Asserts that [resource] is closed. */
  protected abstract fun assertIsClosed(resource: T)

  /** Runs this test rule and advances through the tear-down stage. */
  private fun TestRule.proceedThroughTest() {
    val stub =
        object : Statement() {
          override fun evaluate() {}
        }

    apply(stub, Description.EMPTY).evaluate()
  }
}
