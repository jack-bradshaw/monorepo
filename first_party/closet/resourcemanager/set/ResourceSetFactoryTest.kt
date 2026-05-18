package com.jackbradshaw.closet.resourcemanager.set

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.runBlocking
import org.junit.Test

/** Abstract tests that all [ResourceSet.Factory] instances should pass. */
abstract class ResourceSetFactoryTest {

  @Test
  fun createResourceSet_returnsNewInstanceEachTime() =
      runBlocking<Unit> {
        val factory = subject()

        val managerA = factory.createResourceSet<ObservableClosable>()
        val managerB = factory.createResourceSet<ObservableClosable>()

        assertThat(managerA).isNotSameInstanceAs(managerB)
      }

  /**
   * Gets the subject under test.
   *
   * Must return the same object on each call.
   */
  abstract fun subject(): ResourceSet.Factory
}
