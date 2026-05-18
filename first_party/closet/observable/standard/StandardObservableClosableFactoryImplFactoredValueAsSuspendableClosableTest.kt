package com.jackbradshaw.closet.observable.standard

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.suspending.SuspendableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StandardObservableClosableFactoryImplFactoredValueAsSuspendableClosableTest :
    SuspendableClosableTest<ObservableClosable>() {

  private val coroutines = realisticCoroutinesTestingComponent()

  private val component = standardObservableClosableComponent()

  private val underTest = runBlocking {
    component.standardObservableClosableFactory().createStandardClosable()
  }

  override fun subject() = underTest

  override fun testDispatcher() = coroutines.ioDispatcher()
}
