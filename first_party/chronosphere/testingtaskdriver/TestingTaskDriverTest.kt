package com.jackbradshaw.chronosphere.testingtaskdriver

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.advancable.Advancable
import kotlinx.coroutines.runBlocking
import org.junit.Test

/** Abstract tests that all [TestingTaskDriver]s should pass. */
abstract class TestingTaskDriverTest {

  @Test
  fun advanceAllBy_withNoAdvancables_returnsSuccessfully() = runBlocking {
    setupSubject(emptyList())
    val driver = subject()

    driver.advanceAllBy(100)
  }

  @Test
  fun advanceAllBy_withMultipleAdvancables_advancesEveryVirtualClockUniformly() = runBlocking {
    val advancables = List(5) { ObservableAdvancable() }
    setupSubject(advancables)
    val driver = subject()

    driver.advanceAllBy(10)
    driver.advanceAllBy(15)

    advancables.forEach { assertThat(it.totalTime).isEqualTo(25) }
  }

  /** Sets up the [subject]. Must be called exactly once per test. */
  abstract fun setupSubject(advancables: List<Advancable>)

  /** Provides the subject under test. Must return the same object on each call. */
  abstract fun subject(): TestingTaskDriver

  /** An Advancable for use in tests. Cumulative time can be observed by reading [totalTime]. */
  private class ObservableAdvancable : Advancable {
    @Volatile var totalTime = 0

    override fun advanceBy(millis: Int) {
      totalTime += millis
    }
  }
}
