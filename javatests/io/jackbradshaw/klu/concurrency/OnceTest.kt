package io.jackbradshaw.klu.concurrency

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class OnceTest {

  private var callCount = 0
  private val incrementCounter = suspend { callCount += 1 }

  @Test
  fun once_doesNotInvokeBlock() = runBlocking {
    once { callCount += 1 }

    assertThat(callCount).isEqualTo(0)
  }

  @Test
  fun once_calledMultipleTimes_createsDifferentObjects() = runBlocking {
    val instance1 = once { callCount += 1 }
    val instance2 = once { callCount += 1 }

    assertThat(instance1 === instance2).isFalse()
  }

  @Test
  fun invoke_calledOnce_invokesBlock() = runBlocking {
    once { callCount += 1 }.invoke()

    assertThat(callCount).isEqualTo(1)
  }

  @Test
  fun invoke_calledMultipleTimesSequentially_invokesBlockOnceOnly() = runBlocking {
    val once = once { callCount += 1 }

    for (i in 0 until MULTIPLE_RUN_COUNT) once.invoke()

    assertThat(callCount).isEqualTo(1)
  }

  @Test
  fun imvoke_calledMultipleTimesInAsynchronously_invokesBlockOnceOnly() = runBlocking {
    val jobs = mutableSetOf<Job>()
    val once = once { callCount += 1 }

    for (i in 0 until MULTIPLE_RUN_COUNT) {
      val job = launch { once.invoke() }
      jobs.add(job)
    }
    jobs.forEach { it.join() }

    assertThat(callCount).isEqualTo(1)
  }

  companion object {
    private const val MULTIPLE_RUN_COUNT = 100 // Somewhat arbitrary.
  }
}
