package com.jackbradshaw.closet.observable

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

/** Abstract test that all [ObservableClosable] instances should pass. */
abstract class ObservableClosableTest<T : ObservableClosable> {

  @After
  open fun tearDown() {
    subject().close()
  }

  @Test
  fun afterClose_hasTerminalState() = runBlocking {
    val closable = subject()

    closable.close()

    assertThat(closable.hasTerminalState.value).isTrue()
  }

  @Test
  fun afterClose_hasTerminatedProcesses() = runBlocking {
    val closable = subject()

    closable.close()

    assertThat(closable.hasTerminatedProcesses.value).isTrue()
  }

  @Test
  fun close_isIdempotent() = runBlocking {
    val closable = subject()

    closable.close()
    closable.close()
    closable.close()

    // If here, no exception was thrown, test passed
  }

  /* Gets the subject under test. The same instance must be returned on each call. */
  abstract fun subject(): T
}
