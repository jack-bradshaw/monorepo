package com.jackbradshaw.coroutines.testing.launcher

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Test

abstract class LauncherTest {

  @Test
  fun launchEagerly_runsWithoutFurtherPrompting(): Unit = runBlocking {
    setupSubject()

    var didStart = false
    val job = subject().launchEagerly { didStart = true }

    assertThat(didStart).isTrue()
  }

  @Test
  fun launchDeferred_doesNotRunWithoutPrompting(): Unit = runBlocking {
    setupSubject()

    var didStart = false
    val job = subject().launchDeferred { didStart = true }

    assertThat(didStart).isFalse()
  }

  @Test
  fun launchDeferred_runsWhenPrompted(): Unit = runBlocking {
    setupSubject()

    var didStart = false
    val job = subject().launchDeferred { didStart = true }
    runScheduledWork()

    assertThat(didStart).isTrue()
  }

  @Test
  fun asyncEagerly_runsWithoutFurtherPrompting(): Unit = runBlocking {
    setupSubject()

    var didStart = false
    val job = subject().asyncEagerly { didStart = true }

    assertThat(didStart).isTrue()
  }

  @Test
  fun asyncEagerly_returnsDeferredValue(): Unit = runBlocking {
    setupSubject()

    var result = subject().asyncEagerly { TEST_STRING }.await()

    assertThat(result === TEST_STRING).isTrue()
  }

  @Test
  fun asyncDeferred_doesNotRunWithoutPrompting() {
    setupSubject()

    var result = subject().asyncDeferred { TEST_STRING }

    assertThat(result.isCompleted).isFalse()
  }

  @Test
  fun asyncDeferred_runsWhenPrompted() {
    setupSubject()

    var result = subject().asyncDeferred { TEST_STRING }
    runScheduledWork()

    assertThat(result.isCompleted).isTrue()
  }

  abstract fun setupSubject()

  abstract fun subject(): Launcher

  abstract fun runScheduledWork()

  companion object {
    /** An arbitrary string that can be used in tests to check values are correct. */
    private val TEST_STRING = "foo bar baz"
  }
}
