package com.jackbradshaw.closet.resourcemanager

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.jackbradshaw.closet.observable.ObservableClosableTest

@RunWith(JUnit4::class)
class TestResourceObservableClosableTest : ObservableClosableTest<ResourceManagerImplTest.TestResource>() {
  override fun subject() = ResourceManagerImplTest.TestResource("managed-resource")
}
