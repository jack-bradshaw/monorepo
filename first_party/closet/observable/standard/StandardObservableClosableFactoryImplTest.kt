package com.jackbradshaw.closet.observable.standard

import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StandardObservableClosableFactoryImplTest : StandardObservableClosableFactoryTest() {

  private val testFactory =
      standardObservableClosableComponent().standardObservableClosableFactory()

  private val coroutines = realisticCoroutinesTestingComponent()

  override fun factory() = testFactory

  override fun taskBarrier() = coroutines.taskBarrier()

  override fun dispatcher() = coroutines.ioDispatcher()
}
