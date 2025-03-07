package io.jackbradshaw.codestone.sustainment.uniconverter.startstop

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.codestone.sustainment.operations.StartStopOperation
import io.jackbradshaw.codestone.sustainment.startstop.StartStopSimplex
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PassThroughUniConverterTest {

  private val converter = PassThroughUniConverter()

  @Test
  fun sourceOperationIsEqualToOutputOperation() {
    val sourceOperation = StartStopSimplex()
    val source =
        object : StartStopOperation() {
          override fun work() = sourceOperation
        }
    val output = converter.convert(source)
    assertThat(output.work() == sourceOperation).isTrue()
  }
}
