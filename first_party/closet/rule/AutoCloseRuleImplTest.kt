package com.jackbradshaw.closet.rule

import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class AutoCloseRuleImplTest : AutoCloseRuleTest() {

  private val coroutines = realisticCoroutinesTestingComponent()

  override fun subject() = AutoCloseRuleImpl()

  override fun testDispatcher() = coroutines.cpuDispatcher()
}
