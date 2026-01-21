package com.jackbradshaw.codestone.lifecycle.platforms.futures.converters

import com.google.common.truth.Truth.assertThat
import com.google.common.util.concurrent.ListenableFuture
import com.jackbradshaw.codestone.lifecycle.platforms.startstop.startStopWork
import com.jackbradshaw.codestone.lifecycle.startstop.ExecutionState
import com.jackbradshaw.codestone.lifecycle.startstop.StartStopImpl
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ForwardsUniConverterTest {
  private val executor = Executors.newSingleThreadExecutor()

  @After
  fun tearDown() {
    executor.shutdown()
    executor.awaitTermination(1, TimeUnit.SECONDS)
  }

  @Test
  fun convert_returnsFuture() {
    val converter = ForwardsUniConverter(executor)
    val source = StartStopImpl<Unit, Throwable>()

    val output = converter.convert(startStopWork(source))
    val future = output.handle

    assertThat(future).isNotNull()
    assertThat(future.isDone).isFalse()

    future.cancel(true)
  }

  @Test
  fun outputCancellation_abortsSource() {
    val converter = ForwardsUniConverter(executor)
    val source = StartStopImpl<Unit, Throwable>()
    source.start()

    val output = converter.convert(startStopWork(source))
    val future = output.handle

    Thread.sleep(50)

    future.cancel(true)
    Thread.sleep(50)

    val state = source.state.value
    assertThat(state).isInstanceOf(ExecutionState.Concluded.Aborted::class.java)
  }
}

