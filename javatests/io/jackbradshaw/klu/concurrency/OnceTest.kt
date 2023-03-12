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

  @Test
  fun createPlainOnce_doesNotRunOperation() = runBlocking {
    var callCount = 0

    once { callCount += 1 }

    assertThat(callCount).isEqualTo(0)
  }

  @Test
  fun createThrowingOnce_doesNotThrow() = runBlocking {
    // No setup.

    @Suppress("UNUSED_VARIABLE") // Needs to be assigned or the test won't compile.
    val unused = once { /* no op */ }.throwing()

    // No assertion. Successful if here.
  }

  @Test
  fun invokePlainOnce_oneTime_runsOperationOnce() = runBlocking {
    var callCount = 0
    val once = once { callCount += 1 }

    once.invoke()

    assertThat(callCount).isEqualTo(1)
  }

  @Test
  fun invokePlainOnce_twoTimes_runsOperationOnce() = runBlocking {
    var callCount = 0
    val once = once { callCount += 1 }
    once.invoke()

    once.invoke()

    assertThat(callCount).isEqualTo(1)
  }

  @Test
  fun invokePlainOnce_multipleTimesAsynchronously_runsOperationOnce() = runBlocking {
    var callCount = 0
    val once = once { callCount += 1 }

    val jobs = mutableSetOf<Job>()
    for (i in 0 until 100) {
      val job = launch { once.invoke() }
      jobs.add(job)
    }
    jobs.forEach { it.join() }

    assertThat(callCount).isEqualTo(1)
  }

  @Test
  fun invokeThrowingOnce_generatorVariant_oneTime_doesNotThrow() = runBlocking {
    val once = once { /* no-op */ }.throwing { RuntimeException() }

    once.invoke()

    // No assertion. Successful if here.
  }

  @Test
  fun invokeThrowingOnce_errorVariant_oneTime_doesNotThrow() = runBlocking {
    val once = once { /* no-op */ }.throwing(RuntimeException())

    once.invoke()

    // No assertion. Successful if here.
  }

  @Test
  fun invokeThrowingOnce_messageVariant_oneTime_doesNotThrow() = runBlocking {
    var callCount = 0
    val once = once { callCount += 1 }.throwing("multiple invocations")

    once.invoke()

    // No assertion. Successful if here.
  }

  @Test
  fun invokeThrowingOnce_noArgVariant_oneTime_doesNotThrow() = runBlocking {
    val once = once { /* no-op */ }.throwing()

    once.invoke()

    // No assertion. Successful if here.
  }

  @Test
  fun invokeThrowingOnce_generatorVariant_twoTimes_throwsOnSecondInvocation() = runBlocking {
    val customException = object : RuntimeException() {}
    val once = once { /* no-op */ }.throwing { customException }

    once.invoke()
    val error= catching { once.invoke() }

    assertThat(error).isNotNull()
    assertThat(error === customException).isTrue()
  }

  @Test
  fun invokeThrowingOnce_errorVariant_twoTimes_throwsOnSecondInvocation() = runBlocking {
    val customException = object : RuntimeException() {}
    val once = once { /* no-op */ }.throwing(customException)

    once.invoke()
    val error= catching { once.invoke() }

    assertThat(error).isNotNull()
    assertThat(error === customException).isTrue()
  }

  @Test
  fun invokeThrowingOnce_messageVariant_twoTimes_throwsOnSecondInvocation() = runBlocking {
    val text = "multiple invocations"
    val once = once { /* no-op */ }.throwing(text)

    once.invoke()
    val error= catching { once.invoke() }

    assertThat(error).isNotNull()
    assertThat(error is IllegalStateException)
    assertThat((error as IllegalStateException).message).isEqualTo(text)
  }

  @Test
  fun invokeThrowingOnce_noArgVariant_twoTimes_throwsOnSecondInvocation() = runBlocking {
    val once = once { /* no-op */ }.throwing()

    once.invoke()
    val error = catching { once.invoke() }

    assertThat(error).isNotNull()
    assertThat(error is IllegalStateException)
    assertThat((error as IllegalStateException).message)
        .isEqualTo("A once block was called multiple times.")
  }

  private suspend fun catching(operation: suspend () -> Any): Throwable? = try {
    operation()
    null
  } catch (all: Throwable) {
    all
  }
}
