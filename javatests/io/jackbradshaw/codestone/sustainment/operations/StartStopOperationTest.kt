package io.jackbradshaw.codestone.sustainment.operations

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StartStopOperationTest {

  @Test
  fun workType_alwaysSameInstance() {
    val operation1 =
        object : StartStopOperation() {
          override fun work(): StartStop {
            throw RuntimeException("Unimplemented")
          }
        }
    val operation2 =
        object : StartStopOperation() {
          override fun work(): StartStop {
            throw RuntimeException("Unimplemented")
          }
        }
    assertThat(operation1.workType == operation2.workType).isTrue()
  }
}
