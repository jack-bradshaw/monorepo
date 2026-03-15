package com.jackbradshaw.closet.resourcemanager

import org.junit.Test
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat

abstract class ResourceManagerFactoryTest {

  @Test
  fun createResourceManager_returnsNewInstance() {
    val factory = subject()
    
    val managerA = factory.createResourceManager<String, ResourceManager.ManagedResource>()
    val managerB = factory.createResourceManager<String, ResourceManager.ManagedResource>()
    
    assertThat(managerA).isNotSameInstanceAs(managerB)
  }

  abstract fun subject(): ResourceManagerFactory
}
