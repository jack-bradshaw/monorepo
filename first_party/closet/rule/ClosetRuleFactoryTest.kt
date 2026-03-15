package com.jackbradshaw.closet.rule

import org.junit.runner.RunWith
import org.junit.runners.JUnit4

import com.google.common.truth.Truth.assertThat

@RunWith(JUnit4::class)
class ClosetRuleFactoryTest : ClosetRuleTest<TestResource>() {

  private lateinit var underTest: ClosetRule<TestResource>

  override fun setupSubject(marker: String) {
    val resource = TestResource(marker)
    underTest = ClosetRuleFactory.create(resource)
  }

  override fun subject(): ClosetRule<TestResource> = underTest

  override fun assertResourceIsOpen(resource: TestResource) {
    assertThat(resource.isOpen).isTrue()
  }

  override fun assertResourceIsClosed(resource: TestResource) {
    assertThat(resource.isOpen).isFalse()
  }
}
