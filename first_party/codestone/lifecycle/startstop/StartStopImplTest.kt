package com.jackbradshaw.codestone.lifecycle.startstop

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.codestone.lifecycle.startstop.StartStopImpl
import com.jackbradshaw.codestone.lifecycle.startstop.ExecutionState
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StartStopImplTest {

  @Test
  fun start_runsOnStart() {
    var startCallCount = 0
    val startStop = StartStopImpl<Unit, Throwable>().also { it.onStart { startCallCount += 1 } }
    startStop.start()
    assertThat(startCallCount).isEqualTo(1)
  }

  @Test
  fun stop_runsonstop() {
    var stopCallCount = 0
    val startStop = StartStopImpl<Unit, Throwable>().also { it.onStop { stopCallCount += 1 } }
    startStop.start()
    startStop.stop()
    assertThat(stopCallCount).isEqualTo(1)
  }

  @Test
  fun start_notifiesStartListener() {
    var notified = false
    val startStop = StartStopImpl<Unit, Throwable>().also { it.onStart { notified = true } }
    startStop.start()
    assertThat(notified).isTrue()
  }

  @Test
  fun stop_notifiesStopListener() {
    var notified = false
    val startStop = StartStopImpl<Unit, Throwable>().also { it.onStop { notified = true } }
    startStop.start()
    startStop.stop()
    assertThat(notified).isTrue()
  }

  @Test
  fun beforeStart_wasStartedIsFalse() {
    val startStop = StartStopImpl<Unit, Throwable>()
    assertThat(startStop.state.value.isPostStart).isFalse()
  }

  @Test
  fun beforeState_isStoppedIsFalse() {
    val startStop = StartStopImpl<Unit, Throwable>()
    assertThat(startStop.state.value.isPostStop).isFalse()
  }

  @Test
  fun betweenStartAndStop_isStartedIsTrue() {
    val startStop = StartStopImpl<Unit, Throwable>()
    startStop.start()
    assertThat(startStop.state.value == ExecutionState.Running).isTrue()
  }

  @Test
  fun betweenStartAndStop_wasStartedIsTrue() {
    val startStop = StartStopImpl<Unit, Throwable>()
    startStop.start()
    assertThat(startStop.state.value.isPostStart).isTrue()
  }

  @Test
  fun betweenStartAndStop_isStoppedIsFalse() {
    val startStop = StartStopImpl<Unit, Throwable>()
    startStop.start()
    assertThat(startStop.state.value.isPostStop).isFalse()
  }

  @Test
  fun afterStop_isStartedIsFalse() {
    val startStop = StartStopImpl<Unit, Throwable>()
    startStop.start()
    startStop.stop()
    // It is no longer Running
    assertThat(startStop.state.value == ExecutionState.Running).isFalse()
  }

  @Test
  fun afterStop_wasStartedIsTrue() {
    val startStop = StartStopImpl<Unit, Throwable>()
    startStop.start()
    startStop.stop()
    assertThat(startStop.state.value.isPostStart).isTrue()
  }

  @Test
  fun afterStop_isStoppedIsTrue() {
    val startStop = StartStopImpl<Unit, Throwable>()
    startStop.start()
    startStop.stop()
    assertThat(startStop.state.value.isPostStop).isTrue()
  }
}
