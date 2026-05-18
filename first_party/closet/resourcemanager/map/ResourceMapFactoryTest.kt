package com.jackbradshaw.closet.resourcemanager.map

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.runBlocking
import org.junit.Test

/** Abstract tests that all [ResourceMap.Factory] instances should pass. */
abstract class ResourceMapFactoryTest {

  @Test
  fun createResourceMap_returnsNewInstanceEachTime() =
      runBlocking<Unit> {
        val factory = subject()

        val managerA = factory.createResourceMap<String, ObservableClosable>()
        val managerB = factory.createResourceMap<String, ObservableClosable>()

        assertThat(managerA).isNotSameInstanceAs(managerB)
      }

  /**
   * Gets the subject under test.
   *
   * Must return the same object on each call.
   */
  abstract fun subject(): ResourceMap.Factory
}
