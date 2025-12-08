package com.jackbradshaw.sasync.inbound.transport

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.universal.count.Count
import com.jackbradshaw.universal.count.CountKt
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runCurrent
import org.junit.Test

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
abstract class InboundTransportTest {

  @Test
  fun observeBuffered_emptyQueue_nothingEmitted(): Unit = runBlocking {
    setupSubject()
    var collected = launchBufferedCollection()

    advanceThroughNextBuffer()

    assertThat(collected).hasSize(0)
  }

  @Test
  fun observeBuffered_queueSmallerThanBuffer_queueEmitted(): Unit = runBlocking {
    setupSubject()
    var collected = launchBufferedCollection()
    queue(1)

    advanceThroughNextBuffer()

    assertThat(collected).hasSize(1)
    assertThat(collected[0]).isEqualTo(byteArrayOf(1))
  }

  @Test
  fun observeBuffered_queueMatchesBuffer_queueEmitted(): Unit = runBlocking {
    setupSubject()
    var collected = launchBufferedCollection()
    queue(1)
    queue(40)

    advanceThroughNextBuffer()

    assertThat(collected).hasSize(1)
    assertThat(collected[0]).isEqualTo(byteArrayOf(1, 40))
  }

  @Test
  fun observeBuffered_queueLargerThanBuffer_queueEmitted(): Unit = runBlocking {
    setupSubject()
    var collected = launchBufferedCollection()
    queue(1)
    queue(40)
    queue(4)

    advanceThroughNextBuffer()
    advanceThroughNextBuffer()

    assertThat(collected).hasSize(2)
    assertThat(collected[0]).isEqualTo(byteArrayOf(1, 40))
    assertThat(collected[1]).isEqualTo(byteArrayOf(4))
  }

  @Test
  fun observeBuffered_queuedConcurrently_allEmitted(): Unit = runBlocking {
    setupSubject()
    var collected = launchBufferedCollection()

    val values = listOf<Byte>(1, 6, 9, 1, 7)
    testScope().launch(UnconfinedTestDispatcher(testScope().testScheduler)) {
      values.forEach { launch { queue(it) } }
    }

    advanceThroughNextBuffer()
    advanceThroughNextBuffer()
    advanceThroughNextBuffer()

    assertThat(collected).hasSize(3)
    assertThat(collected[0]).isEqualTo(byteArrayOf(1, 6))
    assertThat(collected[1]).isEqualTo(byteArrayOf(9, 1))
    assertThat(collected[2]).isEqualTo(byteArrayOf(7))
  }

  @Test
  fun observeFlattened_emptyQueue_nothingEmitted(): Unit = runBlocking {
    setupSubject()
    var collected = launchFlattenedCollection()

    advanceThroughNextBuffer()

    assertThat(collected).isEmpty()
  }

  @Test
  fun observeFlattened_queueSmallerThanBuffer_queueEmitted(): Unit = runBlocking {
    setupSubject()
    var collected = launchFlattenedCollection()
    queue(1)

    advanceThroughNextBuffer()

    assertThat(collected).containsExactly(1.toByte())
  }

  @Test
  fun observeFlattened_queueMatchesBuffer_queueEmitted(): Unit = runBlocking {
    setupSubject()
    var collected = launchFlattenedCollection()

    queue(1)
    queue(40)
    advanceThroughNextBuffer()

    assertThat(collected).containsExactly(1.toByte(), 40.toByte())
  }

  @Test
  fun observeFlattened_queueLargerThanBuffer_queueEmitted(): Unit = runBlocking {
    setupSubject()
    var collected = launchFlattenedCollection()

    queue(1)
    queue(40)
    queue(4)
    advanceThroughNextBuffer()
    advanceThroughNextBuffer()

    assertThat(collected).containsExactly(1.toByte(), 40.toByte(), 4.toByte())
  }

  /** Prepares [subject] with [bufferSize]. */
  abstract fun setupSubject(bufferSize: Count.Bounded = DEFAULT_BUFFER_SIZE)

  /** Gets the subject under test. The same instance must be returned each call. */
  abstract fun subject(): InboundTransport

  /** Queues [byte] for emission from the source connected to [subject]. */
  abstract fun queue(byte: Byte)

  /** Advances until one buffer has been processed by [subject]. */
  abstract fun advanceThroughNextBuffer()

  /** Gets the coroutine scope used to run tests. The same instance must be returned each call. */
  abstract fun testScope(): TestScope

  open fun waitUntilIdle() {
    testScope().testScheduler.advanceUntilIdle()
  }

  /**
   * Begins collecting from [subject] on a background thread (bound to [testScope]) and returns a
   * list containing the collected values. The list is updated when new emissions occur.
   */
  private suspend fun launchBufferedCollection(): List<ByteArray> {
    val collected = mutableListOf<ByteArray>()
    testScope().launch(UnconfinedTestDispatcher(testScope().testScheduler)) {
      subject().observeBuffered().toList(collected)
    }
    testScope().runCurrent()
    return collected
  }

  private suspend fun launchFlattenedCollection(): List<Byte> {
    val collected = mutableListOf<Byte>()
    testScope().launch(UnconfinedTestDispatcher(testScope().testScheduler)) {
      subject().observeFlattened().toList(collected)
    }
    testScope().runCurrent()
    return collected
  }

  companion object {
    val DEFAULT_BUFFER_SIZE = CountKt.bounded { value = 2 }
  }
}
