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
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

/**
 * Abstract tests that all [IdleableQuinn] instances should pass.
 *
 * This test requires two separate coroutine scopes and task barriers because they require two
 * independent pairs of execution/idling. This occurs because the system running `execute` never
 * reaches an idle state.
 */
abstract class IdleableQuinnTest {

  private val scope1Handle = Job()

  protected val scope1 by lazy { CoroutineScope(scope1Dispatcher() + scope1Handle) }

  private val scope2Handle = Job()

  protected val scope2 by lazy { CoroutineScope(scope2Dispatcher() + scope2Handle) }

  /** All latches created during testing. Collected for automatic closure. */
  private val latches = ConcurrentHashMap.newKeySet<CountDownLatch>()

  @After
  fun tearDown() {
    // Releases any tasks that were manually paused to prevent resource leaks.
    latches.forEach { it.countDown() }

    runBlocking {
      subject().close()
      scope1Handle.cancelAndJoin()
      scope2Handle.cancelAndJoin()
    }
  }

  @Test
  fun isIdle_beforeExecuting_noTasksSubmitted_returnsTrue() = runBlocking {
    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withOneQueuedAtBack_returnsTrue() = runBlocking {
    scope1.launch { subject().queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withOneTryQueuedAtBack_returnsTrue() = runBlocking {
    scope1.launch { subject().tryQueueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withOneQueuedAtFront_returnsTrue() = runBlocking {
    scope1.launch { subject().queueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withOneTryQueuedAtFront_returnsTrue() = runBlocking {
    scope1.launch { subject().tryQueueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_beforeExecuting_withMultipleQueued_returnsTrue() = runBlocking {
    scope1.launch { subject().queueAtBack {} }
    scope1.launch { subject().queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileExecuting_noTasksSubmitted_returnsTrue() = runBlocking {
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileExecuting_withOneQueuedAtBack_returnsFalse() = runBlocking {
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    scope1.launch {
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
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    scope1.launch {
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
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    scope1.launch {
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
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    scope1.launch {
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
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    val taskStarted1 = CompletableDeferred<Unit>()
    val taskBlocker1 = newLatch()
    val taskBlocker2 = newLatch()
    scope1.launch {
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
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    val taskStarted2 = CompletableDeferred<Unit>()
    val taskBlocker2 = newLatch()
    scope1.launch {
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
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    val firstTask = scope1.launch { subject().queueAtBack {} }

    firstTask.join()

    val secondTask = scope1.launch { subject().queueAtBack {} }

    secondTask.join()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_noTasksSubmitted_returnsTrue() = runBlocking {
    val executeJob = scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withOneQueuedAtBack_returnsTrue() = runBlocking {
    val executeJob = scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    scope1.launch { subject().queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withOneTryQueuedAtBack_returnsTrue() = runBlocking {
    val executeJob = scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    scope1.launch { subject().tryQueueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withOneQueuedAtFront_returnsTrue() = runBlocking {
    val executeJob = scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    scope1.launch { subject().queueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withOneTryQueuedAtFront_returnsTrue() = runBlocking {
    val executeJob = scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    scope1.launch { subject().tryQueueAtFront {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_afterExecuting_withMultipleQueued_returnsTrue() = runBlocking {
    val executeJob = scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    executeJob.cancelAndJoin()

    scope1.launch { subject().queueAtBack {} }
    scope1.launch { subject().queueAtBack {} }
    scope1TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_whileClosingAndFinishingLastTask_returnsFalse() = runBlocking {
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    val taskBlocker = newLatch()
    val taskStarted = CompletableDeferred<Unit>()
    scope1.launch {
      subject().queueAtBack {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    val closeJob = scope2.launch { subject().close() }
    scope2TaskBarrier().awaitAllIdle()

    assertThat(subject().isIdle()).isFalse()
  }

  @Test
  fun isIdle_afterClosingAndFinishingLastTask_returnsTrue() = runBlocking {
    scope1.launch { subject().execute("test-resource") }
    scope1TaskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = newLatch()
    scope1.launch {
      subject().queueAtBack {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    val closeJob = scope2.launch { subject().close() }

    taskBlocker.countDown()
    closeJob.join()

    assertThat(subject().isIdle()).isTrue()
  }

  /** Returns the subject under test. Must return the same instance each time. */
  abstract fun subject(): IdleableQuinn<String>

  /** Returns a dispatcher for use in tests. Must be distinct from [scope2Dispatcher]. */
  abstract fun scope1Dispatcher(): CoroutineDispatcher

  /**
   * Returns a task barrier that gates [scope1Dispatcher]. Must be distinct from
   * [scope2TaskBarrier].
   */
  abstract fun scope1TaskBarrier(): TestingTaskBarrier

  /** Returns a dispatcher for use in tests. Must be distinct from [scope1Dispatcher]. */
  abstract fun scope2Dispatcher(): CoroutineDispatcher

  /**
   * Returns a task barrier that gates [scope2Dispatcher]. Must be distinct from
   * [scope1TaskBarrier].
   */
  abstract fun scope2TaskBarrier(): TestingTaskBarrier

  /** Provides a new [CountDownLatch] with a count of `1`. The latch is registered in [latches]. */
  private fun newLatch(): CountDownLatch = CountDownLatch(1).also { latches.add(it) }
}
