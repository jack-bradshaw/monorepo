package io.jackbradshaw.queen.sustainment.uniconverter.listenablefuture

import com.google.common.truth.Truth.assertThat
import com.google.common.util.concurrent.SettableFuture
import io.jackbradshaw.queen.sustainment.operations.StartStopOperation
import io.jackbradshaw.queen.sustainment.startstop.StartStop
import io.jackbradshaw.queen.sustainment.startstop.StartStopSimplex
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ForwardsUniConverterTest {
  private val converter = ForwardsUniConverter()

  @Test
  fun whenOutputOperationIsNotCreated_sourceOperationIsNotCreated() {
    var startStopCreated = false
    val source =
        object : StartStopOperation() {
          override fun work() = StartStopSimplex().also { startStopCreated = true }
        }
    val unused = converter.convert(source)
    assertThat(startStopCreated).isFalse()
  }

  @SuppressWarnings("FutureReturnValueIgnored")
  @Test
  fun whenOutputOperationIsCreated_sourceOperationIsCreatedAndStarted() {
    var startStopCreated = false
    lateinit var startStop: StartStop
    val source =
        object : StartStopOperation() {
          override fun work() =
              StartStopSimplex().also {
                startStopCreated = true
                startStop = it
              }
        }
    val output = converter.convert(source)
    val unused = output.work()
    assertThat(startStopCreated).isTrue()
    assertThat(startStop.isStarted()).isTrue()
  }

  @Test
  fun whenOutputOperationCompletes_sourceOperationIsStopped() = runBlocking {
    lateinit var startStop: StartStop
    val source =
        object : StartStopOperation() {
          override fun work() = StartStopSimplex().also { startStop = it }
        }
    val output = converter.convert(source)
    (output.work() as SettableFuture<Unit>).set(Unit)
    delay(DELAY_MS)
    assertThat(startStop.wasStarted()).isTrue()
    assertThat(startStop.isStopped()).isTrue()
  }

  @Test
  fun whenOutputOperationIsCancelled_sourceOperationIsStopped() = runBlocking {
    lateinit var startStop: StartStop
    val source =
        object : StartStopOperation() {
          override fun work() = StartStopSimplex().also { startStop = it }
        }
    val output = converter.convert(source)
    output.work().cancel(true)
    delay(DELAY_MS)
    assertThat(startStop.wasStarted()).isTrue()
    assertThat(startStop.isStopped()).isTrue()
  }

  @Test
  fun whenSourceOperationIsStopped_outputOperationIsCompleted() {
    lateinit var startStop: StartStop
    val source =
        object : StartStopOperation() {
          override fun work() = StartStopSimplex().also { startStop = it }
        }
    val output = converter.convert(source)
    val outputOperation = output.work()
    startStop.stop()
    assertThat(outputOperation.isDone()).isTrue()
  }

  companion object {
    private const val DELAY_MS = 5000L
  }
}
