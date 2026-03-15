package com.jackbradshaw.closet.rule

import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFailsWith
import com.google.common.truth.Truth.assertThat

abstract class ClosetRuleTest<T : AutoCloseable> {
  
  @Test
  fun providesSameObjectEveryTime() {
    setupSubject()
    val rule = subject()

    val resource1 = rule.get()
    val resource2 = rule.get()
    
    assertThat(resource1).isSameInstanceAs(resource2)
  }

  @Test
  fun providesOpenObjectsBeforeClose() {
    setupSubject()
    val rule = subject()

    val resource1 = rule.get()

    assertResourceIsOpen(resource1)
  }

  @Test
  fun providesClosedObjectsAfterClose_firstAccessBeforeClose() {
    setupSubject()
    val rule = subject()

    val resource1 = rule.get()
    rule.proceedThroughTest()

    assertResourceIsClosed(resource1)
  }

  @Test
  fun closesObjectsProvidedBeforeClose() {
    setupSubject()
    val rule = subject()

    val resource1 = rule.get()
    rule.proceedThroughTest()

    assertResourceIsClosed(resource1)
  }

  @Test
  fun providesClosedObjectsAfterClose_firstAccessAfterClose() {
    setupSubject()
    val rule = subject()

    rule.proceedThroughTest()
    val resource1 = rule.get()

    assertResourceIsClosed(resource1)
  }

  private fun TestRule.proceedThroughTest() {
    val stub = object : Statement() { 
      override fun evaluate() {}
    }
    apply(stub, Description.EMPTY).evaluate()
  }


  /** Sets up the [subject]. Must be called exactly once per test. */
  protected abstract fun setupSubject(marker: String = "testmarker")

  /** Provides the subject under test. Must return the same object on each call. */
  protected abstract fun subject(): ClosetRule<T>

  /** Asserts that the provided [resource] is currently structurally open. */
  protected abstract fun assertResourceIsOpen(resource: T)

  /** Asserts that the provided [resource] is currently structurally closed. */
  protected abstract fun assertResourceIsClosed(resource: T)
}

/** Simple resource for use in tests with [marker] field for ease of identification. */
class TestResource(
  val marker: String
) : AutoCloseable {

  /** Whether [close] has been called. */
  var isOpen = true
  
  override fun close() {
    isOpen = false
  }
}
