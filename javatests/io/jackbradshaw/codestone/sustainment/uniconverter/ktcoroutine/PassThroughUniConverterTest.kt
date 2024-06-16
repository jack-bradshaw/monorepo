package io.jackbradshaw.codestone.sustainment.uniconverter.ktcoroutine

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.codestone.sustainment.operations.KtCoroutineOperation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PassThroughUniConverterTest {

  private val converter = PassThroughUniConverter()

  @Test
  fun sourceOperationIsEqualToOutputOperation() {
    val sourceOperation = GlobalScope.launch {}
    val source =
        object : KtCoroutineOperation() {
          override fun work() = sourceOperation
        }
    val output = converter.convert(source)
    assertThat(output.work() == sourceOperation).isTrue()
  }
}
