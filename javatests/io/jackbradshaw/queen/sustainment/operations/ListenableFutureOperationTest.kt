package io.jackbradshaw.queen.sustainment.operations

import com.google.common.truth.Truth.assertThat
import com.google.common.util.concurrent.ListenableFuture
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ListenableFutureOperationTest {
  @Test
  fun workType_alwaysSameInstance() {
    val operation1 =
        object : ListenableFutureOperation() {
          override fun work(): ListenableFuture<Unit> {
            throw RuntimeException("Unimplemented")
          }
        }

    val operation2 =
        object : ListenableFutureOperation() {
          override fun work(): ListenableFuture<Unit> {
            throw RuntimeException("Unimplemented")
          }
        }
    assertThat(operation1.workType === operation2.workType).isTrue()
  }
}
