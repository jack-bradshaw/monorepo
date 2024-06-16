package io.jackbradshaw.queen.sustainment.uniconverter.listenablefuture

import com.google.common.truth.Truth.assertThat
import com.google.common.util.concurrent.SettableFuture
import io.jackbradshaw.queen.sustainment.operations.ListenableFutureOperation
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PassThroughUniConverterTest {
  private val converter = PassThroughUniConverter()

  @Test
  fun sourceOperationIsEqualToOutputOperation() {
    val sourceOperation = SettableFuture.create<Unit>()
    val source =
        object : ListenableFutureOperation() {
          override fun work() = sourceOperation
        }
    val output = converter.convert(source)
    assertThat(output.work() == sourceOperation).isTrue()
  }
}
