package com.jackbradshaw.kale.resolver.chassis

import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ResolverChassisImplAsObservableClosableTest : ObservableClosableTest<ResolverChassis>() {

  private val coroutines = realisticCoroutinesTestingComponent()

  private val underTest = resolverChassisComponent(coroutines).resolverChassis()

  override fun subject(): ResolverChassis = underTest

  override fun testDispatcher() = coroutines.ioDispatcher()
}
