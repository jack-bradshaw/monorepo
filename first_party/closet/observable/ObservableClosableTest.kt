package com.jackbradshaw.closet.observable

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.observable.ObservableClosable.Status
import com.jackbradshaw.closet.suspending.SuspendableClosableTest
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Abstract test that all [ObservableClosable] instances should pass.
 *
 * There is no test to ensure the status becomes CLOSING while closing is executing because not
 * every instance can suspend during closure.
 */
abstract class ObservableClosableTest<T : ObservableClosable> : SuspendableClosableTest<T>() {

  @Test
  fun beforeClose_isOpen() = runBlocking {
    val closable = subject()

    assertThat(closable.closureStatus.value).isEqualTo(Status.OPEN)
  }

  @Test
  fun afterClose_isClosed() = runBlocking {
    val closable = subject()

    closable.close()

    assertThat(closable.closureStatus.value).isEqualTo(Status.CLOSED)
  }
}
