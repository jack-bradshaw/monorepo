package com.jackbradshaw.codestone.lifecycle.platforms.futures.converters

import com.google.common.truth.Truth.assertThat
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import com.jackbradshaw.codestone.lifecycle.startstop.ExecutionState
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Work
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.reflect.typeOf
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BackwardsUniConverterTest {
  private val executor = Executors.newSingleThreadExecutor()
  private val converter = BackwardsUniConverter(executor)

  @After
  fun tearDown() {
    executor.shutdown()
    executor.awaitTermination(1, TimeUnit.SECONDS)
  }

  @Test
  fun convert_returnsStartedOperation() {
    val future = SettableFuture.create<Unit>()
    val source = object : Work<ListenableFuture<Unit>> {
      override val handle = future
      override val workType = typeOf<ListenableFuture<Unit>>()
    }

    val output = converter.convert(source).handle
    assertThat(output.isStarted()).isTrue()
    
    future.set(Unit)
  }

  @Test
  fun stopOutput_cancelsFuture() {
    val future = SettableFuture.create<Unit>()
    val source = object : Work<ListenableFuture<Unit>> {
      override val handle = future
      override val workType = typeOf<ListenableFuture<Unit>>()
    }

    val output = converter.convert(source).handle
    output.abort()

    Thread.sleep(50)
    assertThat(future.isCancelled).isTrue()
  }

  @Test
  fun futureCompletion_stopsOutput() {
    val future = SettableFuture.create<Unit>()
    val source = object : Work<ListenableFuture<Unit>> {
      override val handle = future
      override val workType = typeOf<ListenableFuture<Unit>>()
    }

    val output = converter.convert(source).handle

    future.set(Unit)
    Thread.sleep(50)

    assertThat(output.isStopped()).isTrue()
  }

  @Test
  fun futureCancellation_stopsOutput() {
    val future = SettableFuture.create<Unit>()
    val source = object : Work<ListenableFuture<Unit>> {
      override val handle = future
      override val workType = typeOf<ListenableFuture<Unit>>()
    }

    val output = converter.convert(source).handle

    future.cancel(true)
    Thread.sleep(50)

    assertThat(output.isStopped()).isTrue()
  }
}

private fun StartStop<*, *>.isStarted() = state.value == ExecutionState.Running
private fun StartStop<*, *>.isStopped() = state.value.isPostStop
