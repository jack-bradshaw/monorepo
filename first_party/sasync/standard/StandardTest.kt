package com.jackbradshaw.sasync.standard

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.coroutines.testing.TestCoroutinesComponent
import com.jackbradshaw.coroutines.testing.testCoroutinesComponent
import com.jackbradshaw.sasync.inbound.config.Config as InboundConfig
import com.jackbradshaw.sasync.inbound.inboundComponent
import com.jackbradshaw.sasync.outbound.config.Config as OutboundConfig
import com.jackbradshaw.sasync.outbound.outboundComponent
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.collections.mutableListOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StandardTest {

  @Test
  fun linksStandardInput(): Unit = runBlocking {
    val inputStream = ByteArrayInputStream(TEST_DATA)
    val coroutines = testCoroutinesComponent()
    val standard = createStandard(coroutines, input = inputStream)

    val collected = mutableListOf<Byte>()
    coroutines.launcher().launchEagerly {
      standard
          .standardInputInboundTransport()
          .observeFlattened()
          .take(TEST_DATA.size)
          .toList(collected)
    }
    coroutines.testScope().testScheduler.advanceUntilIdle()

    assertThat(collected).containsExactlyElementsIn(TEST_DATA.toList()).inOrder()
  }

  @Test
  fun linksStandardOutput(): Unit = runBlocking {
    val outputStream = ByteArrayOutputStream()
    val coroutines = testCoroutinesComponent()
    val standard =
        createStandard(
            coroutines,
            output = outputStream,
        )

    val collected = mutableListOf<Byte>()
    coroutines.launcher().launchEagerly {
      standard
          .standardInputInboundTransport()
          .observeFlattened()
          .take(TEST_DATA.size)
          .toList(collected)
    }
    standard.standardOutputOutboundTransport().publishBytes(TEST_DATA)
    coroutines.testScope().testScheduler.advanceUntilIdle()

    assertThat(outputStream.toByteArray().toList())
        .containsExactlyElementsIn(TEST_DATA.toList())
        .inOrder()
  }

  @Test
  fun linksStandardError(): Unit = runBlocking {
    val errorStream = ByteArrayOutputStream()
    val coroutines = testCoroutinesComponent()
    val standard = createStandard(coroutines, error = errorStream)

    standard.standardErrorOutboundTransport().publishBytes(TEST_DATA)
    coroutines.testScope().testScheduler.advanceUntilIdle()

    assertThat(errorStream.toByteArray().toList())
        .containsExactlyElementsIn(TEST_DATA.toList())
        .inOrder()
  }

  private fun createStandard(
      coroutines: TestCoroutinesComponent,
      input: ByteArrayInputStream = ByteArrayInputStream(ByteArray(0)),
      output: ByteArrayOutputStream = ByteArrayOutputStream(),
      error: ByteArrayOutputStream = ByteArrayOutputStream(),
  ): StandardComponent {
    val inboundConfig =
        InboundConfig.newBuilder()
            .setRefreshRate(
                com.jackbradshaw.universal.frequency.Frequency.newBuilder()
                    .setBounded(
                        com.jackbradshaw.universal.frequency.Frequency.Bounded.newBuilder()
                            .setHertz(60.0)))
            .setBufferSize(
                com.jackbradshaw.universal.count.Count.Bounded.newBuilder().setValue(1024))
            .build()

    val outboundConfig =
        OutboundConfig.newBuilder()
            .setQueueSize(
                com.jackbradshaw.universal.count.Count.newBuilder()
                    .setBounded(
                        com.jackbradshaw.universal.count.Count.Bounded.newBuilder().setValue(1024)))
            .build()

    return standardComponent(
        inbound = inboundComponent(inboundConfig, coroutines = coroutines),
        outbound = outboundComponent(outboundConfig, coroutines = coroutines),
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
