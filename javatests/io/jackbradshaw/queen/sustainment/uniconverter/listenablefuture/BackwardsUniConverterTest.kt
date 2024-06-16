package io.jackbradshaw.queen.sustainment.uniconverter.listenablefuture

import com.google.common.truth.Truth.assertThat
import com.google.common.util.concurrent.SettableFuture
import io.jackbradshaw.queen.sustainment.operations.ListenableFutureOperation
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BackwardsUniConverterTest {
  private val converter = BackwardsUniConverter()

  @Test
  fun whenOutputOperationIsNotStarted_sourceOperationIsNotCreated() {
    var futureCreated = false
    val source =
        object : ListenableFutureOperation() {
          override fun work() = SettableFuture.create<Unit>().also { futureCreated = true }
        }
    val unused = converter.convert(source)
    assertThat(futureCreated).isFalse()
  }

  @Test
  fun whenOutputOperationIsStarted_sourceOperationIsCreated() {
    var futureCreated = false
    val source =
        object : ListenableFutureOperation() {
          override fun work() = SettableFuture.create<Unit>().also { futureCreated = true }
        }
    val output = converter.convert(source)
    output.work().start()
    assertThat(futureCreated).isTrue()
  }

  @Test
  fun whenOutputOperationIsStopped_sourceOperationIsCancelled() {
    lateinit var future: SettableFuture<Unit>
    val source =
        object : ListenableFutureOperation() {
          override fun work() = SettableFuture.create<Unit>().also { future = it }
        }
    val output = converter.convert(source)
    output.work().let {
      it.start()
      it.stop()
      assertThat(future.isCancelled()).isTrue()
    }
  }

  @Test
  fun whenSourceOperationCompletes_outputOperationIsStopped() = runBlocking {
    lateinit var future: SettableFuture<Unit>
    val source =
        object : ListenableFutureOperation() {
          override fun work() = SettableFuture.create<Unit>().also { future = it }
        }
    val output = converter.convert(source)
    val outputOperation = output.work()
    outputOperation.start()
    future.set(Unit) // Future completes
    delay(DELAY_MS)
    assertThat(outputOperation.isStopped()).isTrue()
  }

  @Test
  fun whenSourceOperationIsCancelled_outputOperationIsStopped() = runBlocking {
    lateinit var future: SettableFuture<Unit>
    val source =
        object : ListenableFutureOperation() {
          override fun work() = SettableFuture.create<Unit>().also { future = it }
        }
    val output = converter.convert(source)
    val outputOperation = output.work()
    outputOperation.start()
    future.cancel(true)
    delay(DELAY_MS)
    assertThat(outputOperation.isStopped()).isTrue()
  }

  companion object {
    private const val DELAY_MS = 5000L
  }
}
