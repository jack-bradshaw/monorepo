package com.jackbradshaw.concurrency.quinn.testing.hub

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import java.util.concurrent.CountDownLatch
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

abstract class IdleableQuinnHubTest {

  @Test
  fun isIdle_nothingProvisioned_returnsTrue() = runBlocking {
    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_oneProvisioned_provisionedIdle_returnsTrue() = runBlocking {
    subject().createQuinn<String>()

    assertThat(subject().isIdle()).isTrue()
  }

  @Test
  fun isIdle_oneProvisioned_provisionedNotIdle_returnsFalse() = runBlocking {
    val quinn = subject().createQuinn<String>()

    testScope().launch { quinn.execute("test") }
    taskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = CountDownLatch(1)
    testScope().launch {
      quinn.queueAtBack {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    assertThat(subject().isIdle()).isFalse()

    // Allows task to complete to avoid resource leaks.
    taskBlocker.countDown()
  }

  @Test
  fun isIdle_multipleProvisioned_oneProvisionedNotIdle_returnsFalse() = runBlocking {
    val quinn1 = subject().createQuinn<String>()
    val quinn2 = subject().createQuinn<String>()

    testScope().launch { quinn1.execute("test") }
    testScope().launch { quinn2.execute("test") }
    taskBarrier().awaitAllIdle()

    val taskStarted = CompletableDeferred<Unit>()
    val taskBlocker = CountDownLatch(1)
    testScope().launch {
      quinn1.queueAtBack {
        taskStarted.complete(Unit)
        taskBlocker.await()
      }
    }
    taskStarted.await()

    assertThat(subject().isIdle()).isFalse()

    // Allows task to complete to avoid resource leaks.
    taskBlocker.countDown()
  }

  @Test
  fun isIdle_multipleProvisioned_allProvisionedIdle_returnsTrue() = runBlocking {
    val quinn1 = subject().createQuinn<String>()
    val quinn2 = subject().createQuinn<String>()

    testScope().launch { quinn1.execute("test") }
    testScope().launch { quinn2.execute("test") }
    taskBarrier().awaitAllIdle()

    val firstTask =
        testScope().launch {
          quinn1.queueAtBack {
            // No-op to complete immediately.
          }
        }
    firstTask.join()

    val secondTask =
        testScope().launch {
          quinn2.queueAtBack {
            // No-op to complete immediately.
          }
        }
    secondTask.join()

    assertThat(subject().isIdle()).isTrue()
  }

  abstract fun subject(): IdleableQuinnHub

  abstract fun testScope(): CoroutineScope

  abstract fun taskBarrier(): TestingTaskBarrier
}
