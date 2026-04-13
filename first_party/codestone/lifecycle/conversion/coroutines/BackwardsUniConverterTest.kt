package com.jackbradshaw.codestone.lifecycle.uniconverter.coroutine

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.codestone.lifecycle.uniconverter.coroutine.BackwardsUniConverter
import kotlin.reflect.typeOf
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.startstop.ExecutionState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.concurrent.atomic.AtomicBoolean

@RunWith(JUnit4::class)
class BackwardsUniConverterTest {
  private val converter = BackwardsUniConverter(GlobalScope)

  @Test
  fun convert_returnsStartedOperation() = runBlocking {
    val job = GlobalScope.launch { delay(10000) }
    val source = object : Work<Job> {
      override val handle = job
      override val workType = typeOf<Job>()
    }
    
    val output = converter.convert(source).handle
    assertThat(output.isStarted()).isTrue()
    job.cancel()
  }

  @Test
  fun stopOutput_cancelsJob() = runBlocking {
    val job = GlobalScope.launch { delay(10000) }
    val source = object : Work<Job> {
      override val handle = job
      override val workType = typeOf<Job>()
    }
    
    val output = converter.convert(source).handle
    output.abort() // Should trigger onStop -> job.cancel()
    
    // allow cancellation to propagate
    var waits = 0
    while (job.isActive && waits < 10) {
      delay(10)
      waits++
    }
    
    assertThat(job.isCancelled || job.isCompleted).isTrue() 
    // isCancelled is true if cancelled. isCompleted is true if cancelled or finished.
    // If we verify default cancellation behavior.
  }

  @Test
  fun jobCompletion_stopsOutput() = runBlocking {
    val job = GlobalScope.launch { delay(10) }
    val source = object : Work<Job> {
      override val handle = job
      override val workType = typeOf<Job>()
    }
    
    val output = converter.convert(source).handle
    
    // Wait for job to complete
    job.join()
    
    // Adapter uses invokeOnCompletion to call stop(). Might be async.
    var waits = 0
    while (!output.isStopped() && waits < 10) {
      delay(10)
      waits++
    }
    
    assertThat(output.isStopped()).isTrue()
  }
}

private fun StartStop<*, *>.isStarted() = state.value == ExecutionState.Running
private fun StartStop<*, *>.isStopped() = state.value.isPostStop
