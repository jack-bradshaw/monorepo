package com.jackbradshaw.closet.resourcemanager

import org.junit.Test
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat

import com.jackbradshaw.closet.observable.ObservableClosable

abstract class ResourceManagerFactoryTest {

  @Test
  fun createResourceManager_returnsNewInstance() {
    val factory = subject()
    
    val managerA = factory.createResourceManager<String, ObservableClosable>()
    val managerB = factory.createResourceManager<String, ObservableClosable>()
    
    assertThat(managerA).isNotSameInstanceAs(managerB)
  }

  abstract fun subject(): ResourceManagerFactory
}
