package com.jackbradshaw.kale.resolver.chassis

import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.kale.testing.TestSources.VALID_KOTLIN_SOURCE
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResolverHarnessImplObservableClosableTest :
    ObservableClosableTest<ResolverChassis.ResolverHarness>() {

  private val chassis =
      resolverChassisComponent(realisticCoroutinesTestingComponent()).resolverChassis()

  private val underTest = runBlocking { chassis.open(VALID_KOTLIN_SOURCE) }

  @After
  override fun tearDown() {
    chassis.close()
  }

  override fun subject() = underTest
}
