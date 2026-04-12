package com.jackbradshaw.closet.resourcemanager

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.observable.ObservableClosable
import org.junit.Test

/** Abstract tests that all [ResourceManager.Factory] instances should pass. */
abstract class ResourceManagerFactoryTest {

  @Test
  fun createResourceManager_returnsNewInstanceEachTime() {
    val factory = subject()

    val managerA = factory.createResourceManager<String, ObservableClosable>()
    val managerB = factory.createResourceManager<String, ObservableClosable>()

    assertThat(managerA).isNotSameInstanceAs(managerB)
  }

  /**
   * Gets the subject under test.
   *
   * Must return the same object on each call.
   */
  abstract fun subject(): ResourceManager.Factory
}
