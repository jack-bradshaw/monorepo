package com.jackbradshaw.kale.resolver.chassis

import com.jackbradshaw.closet.suspending.SuspendableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.kale.testing.TestSources.VALID_KOTLIN_SOURCE
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResolverHarnessImplAsSuspendableClosableTest :
    SuspendableClosableTest<ResolverChassis.ResolverHarness>() {

  private val coroutines = realisticCoroutinesTestingComponent()

  private val chassis = resolverChassisComponent(coroutines).resolverChassis()

  private val underTest = runBlocking { chassis.open(VALID_KOTLIN_SOURCE) }

  @After
  override fun tearDown() {
    runBlocking { chassis.close() }
    super.tearDown()
  }

  override fun subject() = underTest

  override fun testDispatcher() = coroutines.ioDispatcher()
}
