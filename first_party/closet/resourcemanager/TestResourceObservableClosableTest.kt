package com.jackbradshaw.closet.resourcemanager

import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import kotlin.coroutines.CoroutineContext
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestResourceObservableClosableTest :
    ObservableClosableTest<ResourceManagerImplTest.TestResource>() {

  private lateinit var ioContext: CoroutineContext

  @Before
  fun setUp() {
    ioContext = realisticCoroutinesTestingComponent().ioContext()
  }

  override fun subject() = ResourceManagerImplTest.TestResource("managed-resource", ioContext)
}
