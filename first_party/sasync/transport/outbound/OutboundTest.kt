package com.jackbradshaw.sasync.transport.outbound

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.model.count.CountKt.bounded
import com.jackbradshaw.model.count.CountKt.unbounded
import com.jackbradshaw.model.count.count
import com.jackbradshaw.sasync.transport.outbound.config.Config
import com.jackbradshaw.sasync.transport.outbound.config.config
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import org.junit.Test

abstract class OutboundTest {

  /** The config to use in [subject]. Must be populated before any calls to [subject]. */
  lateinit var config: Config

  @Test
  fun publishInt_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().publishInt(1)

    waitForIdle()

    assertThat(received()).isEqualTo(byteArrayOf(1))
  }

  @Test
  fun publishIntLine_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().publishIntLine(1)

    waitForIdle()

    assertThat(received()).isEqualTo(byteArrayOf(1) + EOL)
  }

  fun publishString_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().publishString("1")

    waitForIdle()

    assertThat(received()).isEqualTo(byteArrayOf(49))
  }

  @Test
  fun publishStringLine_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().publishStringLine("1")

    waitForIdle()

    assertThat(received()).isEqualTo(byteArrayOf(49) + EOL)
  }

  @Test
  fun channel_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().asChannel().send(byteArrayOf(1))

    waitForIdle()

    assertThat(received()).isEqualTo(byteArrayOf(1))
  }

  @Test
  fun publishBytes_primitiveVariant_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().publishBytes(TEST_VALUES_BYTE_ARRAY)

    waitForIdle()

    assertThat(received()).isEqualTo(TEST_VALUES_BYTE_ARRAY)
  }

  @Test
  fun publishBytes_primitiveVariantLine_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().publishBytesLine(TEST_VALUES_BYTE_ARRAY)

    waitForIdle()

    assertThat(received()).isEqualTo(TEST_VALUES_BYTE_ARRAY + EOL)
  }

  @Test
  fun publishBytes_boxedVariant_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().publishBytes(TEST_VALUES_TYPED_ARRAY)

    waitForIdle()

    assertThat(received()).isEqualTo(TEST_VALUES_BYTE_ARRAY)
  }

  @Test
  fun publishBytes_boxedVariantLine_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().publishBytesLine(TEST_VALUES_TYPED_ARRAY)

    waitForIdle()

    assertThat(received()).isEqualTo(TEST_VALUES_BYTE_ARRAY + EOL)
  }

  @Test
  fun publishLineEnding_writesToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    subject().publishLineEnding()

    waitForIdle()

    assertThat(received()).isEqualTo(EOL)
  }

  @Test
  fun publish_sequentially_allWrittenToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    TEST_VALUES_INTS.forEach { subject().publishInt(it) }

    waitForIdle()

    assertThat(received()).isEqualTo(TEST_VALUES_BYTE_ARRAY)
  }

  @Test
  fun publish_concurrently_allWrittenToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_UNBOUNDED)

    TEST_VALUES_INTS.forEach { testScope().launch { subject().publishInt(it) } }

    waitForIdle()

    assertThat(received()).isEqualTo(TEST_VALUES_BYTE_ARRAY)
  }

  @Test
  fun publish_countExceedsQueueSize_allWrittenToDestination(): Unit = runBlocking {
    setup(config = QUEUE_SIZE_BOUNDED)

    TEST_VALUES_INTS.forEach { testScope().launch { subject().publishInt(it) } }

    waitForIdle()

    assertThat(received()).isEqualTo(TEST_VALUES_BYTE_ARRAY)
  }

  /** Prepares [subject] using [config]. */
  abstract fun setup(config: Config)

  /** Gets the subject under test. The same instance must be returned each call. */
  abstract fun subject(): Outbound

  /** Gets the values received at the destination since the last call to [received]. */
  abstract fun received(): ByteArray

  /** Gets the coroutine scope used to run tests. The same instance must be returned each call. */
  abstract fun testScope(): TestScope

  /** Advances until [testScope] is idle. */
  suspend fun waitForIdle() {
    testScope().testScheduler.advanceUntilIdle()
  }

  companion object {
    private val QUEUE_SIZE_UNBOUNDED = config { queueSize = count { unbounded = unbounded {} } }
    private val QUEUE_SIZE_BOUNDED = config {
      queueSize = count { bounded = bounded { value = 2 } }
    }

    /** The system-specific EOL character(s). */
    private val EOL = System.lineSeparator().toByteArray()

    /**
     * Values for use in tests. Chosen to include repetition and have a count exceeding
     * [QUEUE_SIZE_BOUNDED].
     */
    private val TEST_VALUES_INTS = listOf<Int>(1, 20, 6, 1, 5)

    /** [TEST_VALUES_INTS] as a list of bytes. */
    private val TEST_VALUES_BYTES = listOf<Byte>(1, 20, 6, 1, 5)
    /** [TEST_VALUES_INTS] as a primitive array of bytes. */
    private val TEST_VALUES_BYTE_ARRAY = byteArrayOf(1, 20, 6, 1, 5)

    /** [TEST_VALUES_INTS] as a types array of bytes. */
    private val TEST_VALUES_TYPED_ARRAY = arrayOf<Byte>(1, 20, 6, 1, 5)
  }
}
