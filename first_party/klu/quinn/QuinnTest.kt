package com.jackbradshaw.quinn

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.CoroutineDispatcher
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.google.common.truth.Truth.assertThat

/** Abstract tests that all [Quinn] intances should pass.
 * 
 * These tests cancel their jobs after the final assertions. This is necessary because `runBlocking`
 * will suspend until all child coroutines have finished, so the lingering jobs in the tests will
 * cause test timeouts if not cancelled. In some cases this is actually unnecesary, but the practice
 * is applied to all tests for consistency.
 */
@RunWith(JUnit4::class)
abstract class QuinnTest<T> {

  @After
  fun tearDown() {
    runBlocking { subject().close() }
  }

  @Test
  fun submit_suspendsBeforeProcessing() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob = launch(cpuDispatcher()) { 
      quinn.submit { processed.add("first") } 
    }
    taskBarrier().awaitAllIdle()
    
    assertThat(submitJob.isActive).isTrue()

    submitJob.cancel()
  }

  @Test
  fun submit_resumesAfterProcessing() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob = launch(cpuDispatcher()) { 
      quinn.submit { processed.add("first") } 
    }
    taskBarrier().awaitAllIdle()
    
    val drainJob = launch(cpuDispatcher()) {
      quinn.drain(createResource())
    }
    taskBarrier().awaitAllIdle()
    
    assertThat(submitJob.isCompleted).isTrue()

    drainJob.cancelAndJoin()
  }

  @Test
  fun drain_suspendsIndefinitely() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob1 = launch(cpuDispatcher()) { 
      quinn.submit { processed.add("first") } 
    }
    taskBarrier().awaitAllIdle()
    
    val drainJob = launch(cpuDispatcher()) {
      quinn.drain(createResource())
    }
    taskBarrier().awaitAllIdle()
    
    val submitJob2 = launch(cpuDispatcher()) { 
      quinn.submit { processed.add("second") } 
    }
    taskBarrier().awaitAllIdle()
    
    // Check processed first to ensure drain job is active even after all values were processed
    assertThat(processed).containsExactly("first", "second")
    assertThat(drainJob.isActive).isTrue()

    submitJob1.cancelAndJoin()
    drainJob.cancelAndJoin()
    submitJob2.cancelAndJoin()
  }

  @Test
  fun multipleDrains_sequentially_invokesAllBlocksInSubmissionOrder() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val submitJob1 = launch(cpuDispatcher()) { quinn.submit { processed.add(it) } }
    val drainJob1 = launch(cpuDispatcher()) { quinn.drain(resource1) }
    taskBarrier().awaitAllIdle()
    drainJob1.cancelAndJoin()

    val resource2 = createResource()
    val submitJob2 = launch(cpuDispatcher()) { quinn.submit { processed.add(it) } }
    val drainJob2 = launch(cpuDispatcher()) { quinn.drain(resource2) }
    taskBarrier().awaitAllIdle()
    drainJob2.cancelAndJoin()

    assertThat(processed).containsExactly(resource1, resource2).inOrder()
    
    submitJob1.cancelAndJoin()
    submitJob2.cancelAndJoin()
  }

  @Test
  fun multipleDrains_sequentially_firstDrainResourceUsed() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val submitJob1 = launch(cpuDispatcher()) { quinn.submit { processed.add(it) } }
    val drainJob1 = launch(cpuDispatcher()) { quinn.drain(resource1) }
    taskBarrier().awaitAllIdle()
    drainJob1.cancelAndJoin()

    val resource2 = createResource()
    val submitJob2 = launch(cpuDispatcher()) { quinn.submit { processed.add(it) } }
    val drainJob2 = launch(cpuDispatcher()) { quinn.drain(resource2) }
    taskBarrier().awaitAllIdle()
    drainJob2.cancelAndJoin()

    assertThat(processed).containsExactly(resource1, resource2).inOrder()
    
    submitJob1.cancelAndJoin()
    submitJob2.cancelAndJoin()
  }

  @Test
  fun multipleDrains_concurrently_invokesAllBlocksInSubmissionOrder() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val drainJob1 = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    taskBarrier().awaitAllIdle()
    
    val drainJob2 = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    taskBarrier().awaitAllIdle()

    val submitJob = launch(cpuDispatcher()) { 
      quinn.submit { processed.add("first") } 
      quinn.submit { processed.add("second") } 
    }
    taskBarrier().awaitAllIdle()
    
    drainJob1.cancelAndJoin()
    drainJob2.cancelAndJoin()

    assertThat(processed).containsExactly("first", "second").inOrder()
    
    submitJob.cancelAndJoin()
  }

  @Test
  fun multipleDrains_concurrently_firstDrainResourceUsed() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val drainJob1 = launch(cpuDispatcher()) { quinn.drain(resource1) }
    taskBarrier().awaitAllIdle()
    
    val resource2 = createResource()
    val drainJob2 = launch(cpuDispatcher()) { quinn.drain(resource2) }
    taskBarrier().awaitAllIdle()

    val submitJob = launch(cpuDispatcher()) { 
      quinn.submit { resource -> processed.add(resource) } 
      quinn.submit { resource -> processed.add(resource) } 
    }
    taskBarrier().awaitAllIdle()
    
    drainJob1.cancelAndJoin()
    drainJob2.cancelAndJoin()

    assertThat(processed).containsExactly(resource1, resource1).inOrder()
    
    submitJob.cancelAndJoin()
  }

  @Test
  fun multipleDrains_concurrently_resourceFromSecondDrainUsedAfterFirstCancelled() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val resource1 = createResource()
    val drainJob1 = launch(cpuDispatcher()) { quinn.drain(resource1) }
    taskBarrier().awaitAllIdle()
    
    val resource2 = createResource()
    val drainJob2 = launch(cpuDispatcher()) { quinn.drain(resource2) }
    taskBarrier().awaitAllIdle()

    val submitJob1 = launch(cpuDispatcher()) { 
      quinn.submit { resource -> processed.add(resource) } 
    }
    taskBarrier().awaitAllIdle()

    drainJob1.cancelAndJoin()
    taskBarrier().awaitAllIdle()

    val submitJob2 = launch(cpuDispatcher()) { 
      quinn.submit { resource -> processed.add(resource) } 
    }
    taskBarrier().awaitAllIdle()

    assertThat(processed).containsExactly(resource1, resource2).inOrder()
    
    submitJob1.cancelAndJoin()
    submitJob2.cancelAndJoin()
    drainJob2.cancelAndJoin()
  }

  @Test
  fun multipleDrains_concurrently_allDrainsSuspend() = runBlocking {
    val quinn = subject()

    val drainJob1 = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    taskBarrier().awaitAllIdle()
    
    val drainJob2 = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    taskBarrier().awaitAllIdle()

    assertThat(drainJob2.isActive).isTrue()

    drainJob1.cancel()
    drainJob2.cancel()
  }

  @Test
  fun submitThenDrain_invokesAllBlocksInSubmissionOrder() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val submitJob = launch(cpuDispatcher()) { 
      quinn.submit { processed.add("first") } 
      quinn.submit { processed.add("second") } 
    }
    taskBarrier().awaitAllIdle()

    val drainJob = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    taskBarrier().awaitAllIdle()
    
    drainJob.cancelAndJoin()

    assertThat(processed).containsExactly("first", "second").inOrder()
    
    submitJob.cancelAndJoin()
  }

  @Test
  fun drainThenSubmit_invokesAllBlocksInSubmissionOrder() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()

    val drainJob = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    val submitJob = launch(cpuDispatcher()) { 
      quinn.submit { processed.add("first") } 
      quinn.submit { processed.add("second") } 
    }
    taskBarrier().awaitAllIdle()
    drainJob.cancelAndJoin()

    assertThat(processed).containsExactly("first", "second").inOrder()
    
    submitJob.cancelAndJoin()
  }

  @Test
  fun concurrent_drain_eachBlockEvaluatedOnce() = runBlocking {
    val quinn = subject()
    val evaluations = java.util.concurrent.atomic.AtomicInteger(0)

    val drainJob1 = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    val drainJob2 = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    taskBarrier().awaitAllIdle()

    val submitJob = launch(cpuDispatcher()) { quinn.submit { evaluations.incrementAndGet() } }
    taskBarrier().awaitAllIdle()

    quinn.close()
    drainJob1.join()
    drainJob2.join()
    submitJob.join()

    assertThat(evaluations.get()).isEqualTo(1)
  }

  @Test
  fun afterAllBlocksInvoked_drainRemainsSuspended() = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<T>()

    val drainJob = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    taskBarrier().awaitAllIdle()
    
    val submitJob = launch(cpuDispatcher()) { 
      quinn.submit { processed.add(it) } 
      quinn.submit { processed.add(it) } 
    }
    taskBarrier().awaitAllIdle()

    assertThat(drainJob.isActive).isTrue()

    drainJob.cancel()
    submitJob.cancelAndJoin()
  }

  @Test
  fun close_premptsProcessingPendingBlocks(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()
    val pauseHandle = TestingPauseHandle()

    val drainJob = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    
    val submitJob1 = launch(cpuDispatcher()) { 
      quinn.submit {
        pauseHandle.pause()
        processed.add("first")
      } 
    }
    
    pauseHandle.waitUntilPaused()
    
    val submitJob2 = launch(cpuDispatcher()) { 
      quinn.submit { processed.add("second") } 
    }
    
    val closeJob = launch(cpuDispatcher()) { quinn.close() }
    
    pauseHandle.resume()

    submitJob1.join()
    submitJob2.join()
    closeJob.join()
    drainJob.join()

    assertThat(processed).containsExactly("first")
  }

  @Test
  fun close_allowsActiveBlocksToComplete(): Unit = runBlocking {
    val quinn = subject()
    val processed = mutableListOf<String>()
    val pauseHandle = TestingPauseHandle()

    val drainJob = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    
    val submitJob = launch(cpuDispatcher()) { 
      quinn.submit {
        pauseHandle.pause()
        processed.add("first")
      } 
    }
    
    pauseHandle.waitUntilPaused()
    
    val closeJob = launch(cpuDispatcher()) { quinn.close() }
    
    pauseHandle.resume()

    submitJob.join()
    closeJob.join()
    drainJob.join()

    assertThat(processed).containsExactly("first")
  }


  @Test
  fun afterClose_submit_fails() = runBlocking {
    val quinn = subject()
    quinn.close()
    
    val tryResult = quinn.trySubmit { }
    
    var didFail = false
    try {
      quinn.submit { }
    } catch (e: IllegalStateException) {
      didFail = true
    }
    
    assertThat(tryResult).isFalse()
    assertThat(didFail).isTrue()
  }

  @Test
  fun afterClose_drain_doesNotFail() = runBlocking {
    val quinn = subject()

    quinn.close()
    
    quinn.drain(createResource())
  }

  @Test
  fun afterClose_trySubmit_returnsFalse() = runBlocking {
    val quinn = subject()
    quinn.close()
    
    val tryResult = quinn.trySubmit { }
    
    assertThat(tryResult).isFalse()
  }

  @Test
  fun trySubmit_returnsTrueWhenActive() = runBlocking {
    val quinn = subject()
    
    var success = false
    val submitJob = launch(cpuDispatcher()) { 
      success = quinn.trySubmit { } 
    }
    taskBarrier().awaitAllIdle()
    
    val drainJob = launch(cpuDispatcher()) { quinn.drain(createResource()) }
    taskBarrier().awaitAllIdle()
    
    assertThat(success).isTrue()
    
    submitJob.cancelAndJoin()
    drainJob.cancelAndJoin()
  }

  abstract fun subject(): Quinn<T>

  abstract fun cpuDispatcher(): CoroutineDispatcher

  abstract fun taskBarrier(): TestingTaskBarrier

  /** Creates a new resource to be supplied to Quinn. An equals-identify unique value must be
   * returned on each call. Does not require thread safety.
   */
  abstract fun createResource(): T

  // TODO(jack@jack-bradshaw.com): Extract to a separate package for reusability.
  /**
   * Handle to precisely and deterministically pause and resume a coroutine within a test.
   * 
   * Example:
   * 
   * ```kotlin
   * val pauseHandle = TestingPauseHandle()
   * 
   * val executionJob = launch(cpuDispatcher()) { 
   *   pauseHandle.pause()
   *   // Execution is deferred at this point...
   * }
   * 
   * pauseHandle.waitUntilPaused()
   * // It is now guaranteed the coroutine is suspended actively mid-evaluation.
   * // External synchronization or external event invocations can happen here safely.
   * 
   * pauseHandle.resume()
   * // Execution is unrestrained and concludes.
   * ```
   * 
   * This is useful when tests need to start work then suspend it until a specific condition has
   * been met, which is a common scenario when testing the edge cases of a concurrent system, as
   * such scenarios are where race conditions and complex multi-threading issues emerge.
   */
  protected class TestingPauseHandle {
    /** Whether execution has started */
    private val pauseStarted = kotlinx.coroutines.CompletableDeferred<Unit>()
    private val pauseCompleted = kotlinx.coroutines.CompletableDeferred<Unit>()

    /** Pauses the calling synchronous block until [resume] is called. */
    fun pause() {
      pauseStarted.complete(Unit)
      runBlocking { pauseCompleted.await() }
    }

    /** Suspends until [pause] has been invoked. */
    suspend fun waitUntilPaused() {
      pauseStarted.await()
    }

    /** Unblocks the paused block. */
    fun resume() {
      pauseCompleted.complete(Unit)
    }
  }
}
