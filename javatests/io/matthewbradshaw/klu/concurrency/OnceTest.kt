package io.matthewbradshaw.klu.concurrency

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.Job

@RunWith(JUnit4::class)
class OnceTest {

  private var callCount = 0
  private val callCounter = suspend {
    callCount += 1
  }

  @Test
  fun once_doesNotInvokeBlock() = runBlocking {
    once(callCounter)

    assertThat(callCount).isEqualTo(0)
  }

  @Test
  fun once_calledMultipleTimes_createsDifferentObjects() = runBlocking {
    val instance1 = once(callCounter)
    val instance2 = once(callCounter)

    assertThat(instance1 === instance2).isFalse()
  }

  @Test
  fun runIfNotRun_calledOnce_invokesBlock() = runBlocking {
    once(callCounter).runIfNotRun()

    assertThat(callCount).isEqualTo(1)
  }

  @Test
  fun runIfNotRun_calledMultipleTimesSequentially_invokesBlockOnceOnly() = runBlocking {
    val once = once(callCounter)

    for (i in 0 until MULTIPLE_RUN_COUNT) once.runIfNotRun()

    assertThat(callCount).isEqualTo(1)
  }

  @Test
  fun runIfNotRun_calledMultipleTimesInAsynchronously_invokesBlockOnceOnly() = runBlocking {
    val jobs = mutableSetOf<Job>()
    val once = once(callCounter)

    for (i in 0 until MULTIPLE_RUN_COUNT) {
      val job = launch {
        once.runIfNotRun()
      }
      jobs.add(job)
    }
    jobs.forEach { it.join() }

    assertThat(callCount).isEqualTo(1)
  }

  companion object {
    private const val MULTIPLE_RUN_COUNT = 100 // Somewhat arbitrary.
  }
}