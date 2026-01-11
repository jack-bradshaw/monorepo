package com.jackbradshaw.coroutines.testing.advancer

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.wheatley.ContractTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

/** Configuration for [AdvancerTest]. */
data class AdvancerConfig(val placeholder: Unit = Unit)

/** Abstract tests for [Advancer] that all implementations must pass. */
abstract class AdvancerTest : ContractTest<Advancer, AdvancerConfig> {

  @Test
  fun advanceUntilIdle_withNoWork_completes(): Unit = runBlocking {
    setupSubject(AdvancerConfig())

    subject().advanceUntilIdle()

    teardownSubject()
  }

  @Test
  fun advanceUntilIdle_withImmediateWork_executesWork(): Unit = runBlocking {
    setupSubject(AdvancerConfig())

    var executed = false
    scheduleImmediateWork { executed = true }

    subject().advanceUntilIdle()

    assertThat(executed).isTrue()
    teardownSubject()
  }

  @Test
  fun advanceUntilIdle_withDelayedWork_executesWork(): Unit = runBlocking {
    setupSubject(AdvancerConfig())

    var executed = false
    scheduleDelayedWork { executed = true }

    subject().advanceUntilIdle()

    assertThat(executed).isTrue()
    teardownSubject()
  }

  @Test
  fun advanceUntilIdle_withRecursiveImmediateWork_executesAllWork(): Unit = runBlocking {
    setupSubject(AdvancerConfig())

    var firstExecuted = false
    var secondExecuted = false
    scheduleImmediateWork {
      firstExecuted = true
      scheduleImmediateWork { secondExecuted = true }
    }

    subject().advanceUntilIdle()

    assertThat(firstExecuted).isTrue()
    assertThat(secondExecuted).isTrue()
    teardownSubject()
  }

  @Test
  fun advanceThroughTick_withNoWork_completes(): Unit = runBlocking {
    setupSubject(AdvancerConfig())

    subject().advanceThroughTick()

    teardownSubject()
  }

  @Test
  fun advanceThroughTick_withImmediateWork_executesWork(): Unit = runBlocking {
    setupSubject(AdvancerConfig())

    var executed = false
    scheduleImmediateWork { executed = true }

    subject().advanceThroughTick()

    assertThat(executed).isTrue()
    teardownSubject()
  }

  @Test
  fun advanceThroughTick_withDelayedWork_doesNotExecuteWork(): Unit = runBlocking {
    setupSubject(AdvancerConfig())

    var executed = false
    scheduleDelayedWork { executed = true }

    subject().advanceThroughTick()

    assertThat(executed).isFalse()
    teardownSubject()
  }

  @Test
  fun advanceThroughTick_withRecursiveImmediateWork_executesAllImmediateWork(): Unit =
      runBlocking {
        setupSubject(AdvancerConfig())

        var firstExecuted = false
        var secondExecuted = false
        scheduleImmediateWork {
          firstExecuted = true
          scheduleImmediateWork { secondExecuted = true }
        }

        subject().advanceThroughTick()

        assertThat(firstExecuted).isTrue()
        assertThat(secondExecuted).isTrue()
        teardownSubject()
      }

  /**
   * Schedules work to execute immediately without delay.
   *
   * @param work The work to schedule.
   */
  abstract fun scheduleImmediateWork(work: suspend () -> Unit)

  /**
   * Schedules work to execute after a delay.
   *
   * @param work The work to schedule.
   */
  abstract fun scheduleDelayedWork(work: suspend () -> Unit)
}
