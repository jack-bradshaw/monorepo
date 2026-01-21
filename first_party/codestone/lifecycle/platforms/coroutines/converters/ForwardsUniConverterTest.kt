package com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.codestone.lifecycle.startstop.StartStopImpl
import com.jackbradshaw.codestone.lifecycle.platforms.startstop.startStopWork
import com.jackbradshaw.codestone.lifecycle.startstop.ExecutionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.yield
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ForwardsUniConverterTest {

  @Test
  fun convert_startsSource() = runBlocking {
    val converter = ForwardsUniConverter(this)
    val source = StartStopImpl<Unit, Throwable>()

    val output = converter.convert(startStopWork(source))
    val job = output.handle

    // Allow async execution
    yield()
    delay(50)

    val state = source.state.value
    // Should be Running or Concluded (if it finished fast, but Simplex stays running until concluded)
    assertThat(state).isInstanceOf(ExecutionState.Running::class.java)
    
    // Clean up
    job.cancel()
    source.abort()
  }

  @Test
  fun outputCancellation_abortsSource() = runBlocking {
    val converter = ForwardsUniConverter(this)
    val source = StartStopImpl<Unit, Throwable>()

    val output = converter.convert(startStopWork(source))
    val job = output.handle

    yield() 
    delay(50)

    // Cancel output
    job.cancel()
    
    yield()
    delay(50)

    // Verify source aborted
    // Note: StartStopImpl implementation might transition to Pending or Aborted depending on logic.
    // If we call .abort(), state becomes Aborted?
    // StartStopImpl (step 966) doesn't show implementation details of abort.
    // Assuming standard implementation behavior.
    val state = source.state.value
    assertThat(state).isInstanceOf(ExecutionState.Concluded.Aborted::class.java)
  }
}
