package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CountDownLatch
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

/** Abstract tests that all [IdleableQuinn] instances should pass. */
abstract class IdleableQuinnTest {

  private val testScopeHandle = Job()

  protected val testScope by lazy { CoroutineScope(testDispatcher() + testScopeHandle) }

  /** All latches created during testing. Collected for automatic closure. */
  private val latches = ConcurrentHashMap.newKeySet<CountDownLatch>()

  @After
  fun tearDown() {
    // Releases any tasks that were manually paused to prevent resource leaks.
    latches.forEach { it.countDown() }

    runBlocking {
      subject().close()
      testScopeHandle.cancelAndJoin()
    }
  }

  @Test
  fun isIdle_beforeExecuting_noTasksSubmitted_returnsTrue() = runBlocking {
    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withOneQueuedAtBack_returnsTrue() = runBlocking {
    testScope.launch { subject().queueAtBack {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withOneTryQueuedAtBack_returnsTrue() = runBlocking {
    testScope.launch { subject().tryQueueAtBack {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withOneQueuedAtFront_returnsTrue() = runBlocking {
    testScope.launch { subject().queueAtFront {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withOneTryQueuedAtFront_returnsTrue() = runBlocking {
    testScope.launch { subject().tryQueueAtFront {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withMultipleQueued_returnsTrue() = runBlocking {
    testScope.launch { subject().queueAtBack {} }
    testScope.launch { subject().queueAtBack {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileExecuting_noTasksSubmitted_returnsTrue() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileExecuting_withOneQueuedAtBack_returnsFalse() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    testScope.launch {
      subject().queueAtBack {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    assertThat(subject().isIdle()).isFalse()
  }

  @Test
  fun isIdle_whileExecuting_withOneTryQueuedAtBack_returnsFalse() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    testScope.launch {
      subject().tryQueueAtBack {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    assertThat(subject().isIdle()).isFalse()
  }

  @Test
  fun isIdle_whileExecuting_withOneQueuedAtFront_returnsFalse() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    testScope.launch {
      subject().queueAtFront {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    assertThat(subject().isIdle()).isFalse()
  }

  @Test
  fun isIdle_whileExecuting_withOneTryQueuedAtFront_returnsFalse() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    testScope.launch {
      subject().tryQueueAtFront {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    assertThat(subject().isIdle()).isFalse()
  }

  @Test
  fun isIdle_whileExecuting_withMultipleQueued_noneFinished_returnsFalse() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    val taskStarted1 = CompletableDeferred<Unit>()
    val taskBlocker1 = newLatch()
    val taskBlocker2 = newLatch()
    testScope.launch {
      subject().queueAtBack {
        taskStarted1.complete(Unit)
        taskBlocker1.await()
      }
      subject().queueAtBack { taskBlocker2.await() }
    }
    taskStarted1.await()

    assertThat(subject().isIdle()).isFalse()
  }

  @Test
  fun isIdle_whileExecuting_withMultipleQueued_oneFinishedOneRunning_returnsFalse() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    val taskStarted2 = CompletableDeferred<Unit>()
    val taskBlocker2 = newLatch()
    testScope.launch {
      subject().queueAtBack {}

      subject().queueAtBack {
        taskStarted2.complete(Unit)
        taskBlocker2.await()
      }
    }
    taskStarted2.await()

    assertThat(subject().isIdle()).isFalse()
  }

  @Test
  fun isIdle_whileExecuting_withMultipleQueued_allFinished_returnsTrue() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    val firstTask = testScope.launch { subject().queueAtBack {} }

    firstTask.join()

    val secondTask = testScope.launch { subject().queueAtBack {} }

    secondTask.join()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_noTasksSubmitted_returnsTrue() = runBlocking {
    val executeJob = testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withOneQueuedAtBack_returnsTrue() = runBlocking {
    val executeJob = testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    testScope.launch { subject().queueAtBack {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withOneTryQueuedAtBack_returnsTrue() = runBlocking {
    val executeJob = testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    testScope.launch { subject().tryQueueAtBack {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withOneQueuedAtFront_returnsTrue() = runBlocking {
    val executeJob = testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    testScope.launch { subject().queueAtFront {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withOneTryQueuedAtFront_returnsTrue() = runBlocking {
    val executeJob = testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    testScope.launch { subject().tryQueueAtFront {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withMultipleQueued_returnsTrue() = runBlocking {
    val executeJob = testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    testScope.launch { subject().queueAtBack {} }
    testScope.launch { subject().queueAtBack {} }
    taskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileClosingAndFinishingLastTask_returnsFalse() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    val taskBlocker = newLatch()
    val taskStarted = CompletableDeferred<Unit>()
    testScope.launch {
      subject().queueAtBack {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    val closeJob = testScope.launch { subject().close() }

    delay(DELAY_DURATION_MS)

    assertThat(subject().isIdle()).isFalse()
  }

  @Test
  fun isIdle_afterClosingAndFinishingLastTask_returnsTrue() = runBlocking {
    testScope.launch { subject().execute("test-resource") }
    taskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    testScope.launch {
      subject().queueAtBack {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    val closeJob = testScope.launch { subject().close() }

    taskBlocker.countDown()
    closeJob.join()

    assertThat(subject().isIdle()).isTrue()
  }

  abstract fun subject(): IdleableQuinn<String>

  abstract fun testDispatcher(): CoroutineDispatcher

  abstract fun taskBarrier(): TestingTaskBarrier

  /** Provides a new [CountDownLatch] with a count of `1`. The latch is registered in [latches]. */
  private fun newLatch(): CountDownLatch = CountDownLatch(1).also { latches.add(it) }

  companion object {
    /**
     * The duration to delay when waiting for closure to reach a suspending point.
     *
     * This is necessary because `close` is a blocking call, so any coroutine interactions it does
     * must be wrapped with `runBlocking, which inherently prevents use of the task barrier.
     *
     * TODO(jack-bradshaw): Create a suspendable closure interface so close can be non-blocking.
     */
    private const val DELAY_DURATION_MS = 50L
  }
}
