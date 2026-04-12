package com.jackbradshaw.closet.rule

import com.google.common.truth.Truth.assertThat
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ClosetRuleFactoryTest : ClosetRuleTest<TestResource>() {

  private lateinit var underTest: ClosetRule<TestResource>

  override fun setupSubject() {
    underTest = ClosetRuleFactory.create(TestResource())
  }

  override fun subject(): ClosetRule<TestResource> = underTest

  override fun assertIsOpen(resource: TestResource) {
    assertThat(resource.isOpen).isTrue()
  }

  override fun assertIsClosed(resource: TestResource) {
    assertThat(resource.isOpen).isFalse()
  }
}

/** Simple resource for use in tests. */
class TestResource() : AutoCloseable {

  /** Whether [close] has been called. */
  var isOpen = true
    private set

  override fun close() {
    isOpen = false
  }
}
