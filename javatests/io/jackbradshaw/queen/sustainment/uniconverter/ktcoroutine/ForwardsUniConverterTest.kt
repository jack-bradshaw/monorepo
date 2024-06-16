package io.jackbradshaw.queen.sustainment.uniconverter.coroutine

import com.google.common.truth.Truth.assertThat
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
  fun whenOutputOperationIsNotCreated_sourceOperationIsNotCreated() = runBlocking {
    var startStopCreated = false
    val source =
        object : StartStopOperation() {
          override fun work() = StartStopSimplex().also { startStopCreated = true }
        }
    val unused = converter.convert(source)
    assertThat(startStopCreated).isFalse()
  }

  @Test
  fun whenOutputOperationIsCreated_sourceOperationIsCreatedAndStarted() = runBlocking {
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
    delay(DELAY_MS)
    assertThat(startStopCreated).isTrue()
    assertThat(startStop.isStarted()).isTrue()
  }

  @Test
  fun whenOutputOperationIsCancelled_sourceOperationIsStopped() = runBlocking {
    lateinit var startStop: StartStop
    val source =
        object : StartStopOperation() {
          override fun work() = StartStopSimplex().also { startStop = it }
        }
    val output = converter.convert(source)
    val outputWork = output.work()
    delay(DELAY_MS)
    outputWork.cancel()
    delay(DELAY_MS)
    assertThat(startStop.wasStarted()).isTrue()
    assertThat(startStop.isStopped()).isTrue()
  }

  @Test
  fun whenSourceDperationIsStopped_outputOperationIsCompleted() = runBlocking {
    lateinit var startStop: StartStop
    val source =
        object : StartStopOperation() {
          override fun work() = StartStopSimplex().also { startStop = it }
        }
    val output = converter.convert(source)
    val outputOperation = output.work()
    delay(DELAY_MS)
    startStop.stop()
    delay(DELAY_MS)
    assertThat(outputOperation.isCompleted).isTrue()
  }

  companion object {
    private const val DELAY_MS = 2000L
  }
}
