package com.jackbradshaw.sasync.standard

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.concurrency.testing.TestConcurrencyComponent
import com.jackbradshaw.concurrency.testing.testConcurrencyComponent
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.sasync.inbound.config.Config as InboundConfig
import com.jackbradshaw.sasync.inbound.inboundComponent
import com.jackbradshaw.sasync.outbound.config.Config as OutboundConfig
import com.jackbradshaw.sasync.outbound.outboundComponent
import com.jackbradshaw.universal.count.Count
import com.jackbradshaw.universal.frequency.Frequency
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.collections.mutableListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StandardTest {

  @Test
  fun linksStandardInput(): Unit = runBlocking {
    val inputStream = ByteArrayInputStream(TEST_DATA)

    val coroutines = realisticCoroutinesTestingComponent()
    val concurrency = testConcurrencyComponent()
    val standard = createStandardComponent(coroutines, concurrency, input = inputStream)

    val collected = mutableListOf<Byte>()
    CoroutineScope(coroutines.cpuContext()).launch {
      standard
          .standardInputInboundTransport()
          .observeFlattened()
          .take(TEST_DATA.size)
          .toList(collected)
    }
    coroutines.taskBarrier().awaitAllIdle()

    // Input is poll-based not push-based (backed by the pulsar), so emit pulses to drive polling.
    CoroutineScope(coroutines.cpuContext()).launch {
      repeat(TEST_DATA.size) { concurrency.testPulsar().emit() }
    }
    coroutines.taskBarrier().awaitAllIdle()

    assertThat(collected).containsExactlyElementsIn(TEST_DATA.toList()).inOrder()
  }

  @Test
  fun linksStandardOutput(): Unit = runBlocking {
    val outputStream = ByteArrayOutputStream()
    val coroutines = realisticCoroutinesTestingComponent()
    val concurrency = testConcurrencyComponent()
    val standard =
        createStandardComponent(
            coroutines,
            concurrency,
            output = outputStream,
        )

    standard.standardOutputOutboundTransport().publishBytes(TEST_DATA)
    coroutines.taskBarrier().awaitAllIdle()

    assertThat(outputStream.toByteArray().toList())
        .containsExactlyElementsIn(TEST_DATA.toList())
        .inOrder()
  }

  @Test
  fun linksStandardError(): Unit = runBlocking {
    val errorStream = ByteArrayOutputStream()
    val coroutines = realisticCoroutinesTestingComponent()
    val concurrency = testConcurrencyComponent()
    val standard = createStandardComponent(coroutines, concurrency, error = errorStream)

    standard.standardErrorOutboundTransport().publishBytes(TEST_DATA)
    coroutines.taskBarrier().awaitAllIdle()

    assertThat(errorStream.toByteArray().toList())
        .containsExactlyElementsIn(TEST_DATA.toList())
        .inOrder()
  }

  private fun createStandardComponent(
      coroutines: RealisticCoroutinesTestingComponent,
      concurrency: TestConcurrencyComponent,
      input: ByteArrayInputStream = ByteArrayInputStream(ByteArray(0)),
      output: ByteArrayOutputStream = ByteArrayOutputStream(),
      error: ByteArrayOutputStream = ByteArrayOutputStream(),
  ): StandardComponent {

    val inboundConfig =
        InboundConfig.newBuilder()
            .setRefreshRate(Frequency.newBuilder().setUnbounded(Frequency.Unbounded.newBuilder()))
            .setBufferSize(Count.Bounded.newBuilder().setValue(1024))
            .build()

    val outboundConfig =
        OutboundConfig.newBuilder()
            .setQueueSize(Count.newBuilder().setBounded(Count.Bounded.newBuilder().setValue(1024)))
            .build()

    return standardComponent(
        inbound =
            inboundComponent(inboundConfig, coroutines = coroutines, concurrency = concurrency),
        outbound =
            outboundComponent(outboundConfig, coroutines = coroutines, concurrency = concurrency),
        input = input,
        output = output,
        error = error,
    )
  }

  companion object {
    /** Arbitrary bytes for use in tests. */
    private val TEST_DATA = byteArrayOf(1, 22, 13, -4)
  }
}
