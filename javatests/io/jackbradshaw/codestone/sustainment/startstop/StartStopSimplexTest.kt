package io.jackbradshaw.codestone.sustainment.startstop

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StartStopSimplexTest {

  @Test
  fun start_runsOnStart() {
    var startCallCount = 0
    val startStop = StartStopSimplex(onStart = { startCallCount += 1 })
    startStop.start()
    assertThat(startCallCount).isEqualTo(1)
  }

  @Test
  fun stop_runsonstop() {
    var stopCallCount = 0
    val startStop = StartStopSimplex(onStop = { stopCallCount += 1 })
    startStop.start()
    startStop.stop()
    assertThat(stopCallCount).isEqualTo(1)
  }

  @Test
  fun start_notifiesStartListener() {
    var notified = false
    val startStop = StartStopSimplex().also { it.onStart { notified = true } }
    startStop.start()
    assertThat(notified).isTrue()
  }

  @Test
  fun stop_notifiesStopListener() {
    var notified = false
    val startStop = StartStopSimplex().also { it.onStop { notified = true } }
    startStop.start()
    startStop.stop()
    assertThat(notified).isTrue()
  }

  @Test
  fun beforeStart_wasStartedIsFalse() {
    val startStop = StartStopSimplex()
    assertThat(startStop.wasStarted()).isEqualTo(false)
  }

  @Test
  fun beforeState_isStoppedIsFalse() {
    val startStop = StartStopSimplex()
    assertThat(startStop.isStopped()).isEqualTo(false)
  }

  @Test
  fun betweenStartAndStop_isStartedIsTrue() {
    val startStop = StartStopSimplex()
    startStop.start()
    assertThat(startStop.isStarted()).isEqualTo(true)
  }

  @Test
  fun betweenStartAndStop_wasStartedIsTrue() {
    val startStop = StartStopSimplex()
    startStop.start()
    assertThat(startStop.wasStarted()).isEqualTo(true)
  }

  @Test
  fun betweenStartAndStop_isStoppedIsFalse() {
    val startStop = StartStopSimplex()
    startStop.start()
    assertThat(startStop.isStopped()).isEqualTo(false)
  }

  @Test
  fun afterStop_isStartedIsFalse() {
    val startStop = StartStopSimplex()
    startStop.start()
    startStop.stop()
    assertThat(startStop.isStarted()).isEqualTo(false)
  }

  @Test
  fun afterStop_wasStartedIsTrue() {
    val startStop = StartStopSimplex()
    startStop.start()
    startStop.stop()
    assertThat(startStop.wasStarted()).isEqualTo(true)
  }

  @Test
  fun afterStop_isStoppedIsTrue() {
    val startStop = StartStopSimplex()
    startStop.start()
    startStop.stop()
    assertThat(startStop.isStopped()).isEqualTo(true)
  }
}
