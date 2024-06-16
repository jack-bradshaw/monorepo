package io.jackbradshaw.queen.sustainment.uniconverter.coroutine

import com.google.common.truth.Truth.assertThat
import io.jackbradshaw.queen.sustainment.operations.KtCoroutineOperation
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.suspendCancellableCoroutine
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BackwardsUniConverterTest {
  private val converter = BackwardsUniConverter()

  @Test
  fun whenOutputOperationIsNotStarted_sourceOperationIsNotCreated() {
    var coroutineCreated = false
    val source =
        object : KtCoroutineOperation() {
          override fun work() = GlobalScope.launch { coroutineCreated = true }
        }
    val unused = converter.convert(source)
    assertThat(coroutineCreated).isFalse()
  }

  @Test
  fun whenOutputOperationIsStarted_sourceOperationIsCreated() = runBlocking {
    var coroutineCreated = false
    val source =
        object : KtCoroutineOperation() {
          override fun work() = GlobalScope.launch { coroutineCreated = true }
        }
    val output = converter.convert(source)
    output.work().start()
    delay(DELAY_MS)
    assertThat(coroutineCreated).isTrue()
  }

  @Test
  fun whenOutputOperationIsStopped_sourceOperationIsCancelled() = runBlocking {
    lateinit var job: Job
    val source =
        object : KtCoroutineOperation() {
          override fun work() =
              GlobalScope.launch { suspendCancellableCoroutine { /* run indefinitely */} }
                  .also { job = it }
        }
    val output = converter.convert(source)
    output.work().let {
      it.start()
      delay(DELAY_MS)
      it.stop()
      delay(DELAY_MS)
    }
    assertThat(job.isCancelled).isTrue()
  }

  @Test
  fun whenSourceOperationCompletes_outputOperationIsStopped() = runBlocking {
    lateinit var jobContinuation: Continuation<Unit>
    lateinit var job: Job
    val source =
        object : KtCoroutineOperation() {
          override fun work() =
              GlobalScope.launch { suspendCancellableCoroutine { jobContinuation = it } }
                  .also { job = it }
        }
    val output = converter.convert(source)
    val outputOperation = output.work()
    outputOperation.start()
    delay(DELAY_MS)
    jobContinuation.resume(Unit)
    delay(DELAY_MS)
    assertThat(outputOperation.isStopped()).isTrue()
  }

  @Test
  fun whenSourceOperationIsCancelled_outputOperationIsStopped() = runBlocking {
    lateinit var jobContinuation: CancellableContinuation<Unit>
    lateinit var job: Job
    val source =
        object : KtCoroutineOperation() {
          override fun work() =
              GlobalScope.launch { suspendCancellableCoroutine { jobContinuation = it } }
                  .also { job = it }
        }
    val output = converter.convert(source)
    val outputOperation = output.work()
    outputOperation.start()
    delay(DELAY_MS)
    jobContinuation.cancel()
    delay(DELAY_MS)
    assertThat(outputOperation.isStopped()).isTrue()
  }

  companion object {
    private const val DELAY_MS = 1000L
  }
}
