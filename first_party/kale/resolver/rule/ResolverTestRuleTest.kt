package com.jackbradshaw.kale.resolver.rule

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.rule.ClosetRuleTest
import com.jackbradshaw.kale.resolver.chassis.ResolverChassis
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResolverTestRuleTest : ClosetRuleTest<ResolverChassis>() {

  private lateinit var underTest: ResolverTestRule

  override fun subject() = underTest

  override fun setupSubject() {
    underTest = ResolverTestRule()
  }

  override fun assertIsOpen(resource: ResolverChassis) {
    assertThat(resource.hasTerminalState.value).isFalse()
    assertThat(resource.hasTerminatedProcesses.value).isFalse()
  }

  override fun assertIsClosed(resource: ResolverChassis) {
    assertThat(resource.hasTerminalState.value).isTrue()
    assertThat(resource.hasTerminatedProcesses.value).isTrue()
  }
}
