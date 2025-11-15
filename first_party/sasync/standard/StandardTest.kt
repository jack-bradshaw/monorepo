package com.jackbradshaw.sasync.standard

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.coroutines.testing.DaggerTestCoroutines
import com.jackbradshaw.coroutines.testing.TestCoroutines
import com.jackbradshaw.sasync.inbound.inbound
import com.jackbradshaw.sasync.outbound.outbound
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
    val coroutines = DaggerTestCoroutines.create()
    val standard = createStandard(coroutines, input = inputStream)

    val collected = mutableListOf<Byte>()
    coroutines.launcher().launchEagerly {
      standard.standardInput().observeFlattened().take(TEST_DATA.size).toList(collected)
    }
    coroutines.testScope().testScheduler.advanceUntilIdle()

    assertThat(collected).containsExactlyElementsIn(TEST_DATA.toList()).inOrder()
  }

  @Test
  fun linksStandardOutput(): Unit = runBlocking {
    val outputStream = ByteArrayOutputStream()
    val coroutines = DaggerTestCoroutines.create()
    val standard =
        createStandard(
            coroutines,
            output = outputStream,
        )

    val collected = mutableListOf<Byte>()
    coroutines.launcher().launchEagerly {
      standard.standardInput().observeFlattened().take(TEST_DATA.size).toList(collected)
    }
    standard.standardOutput().publishBytes(TEST_DATA)
    coroutines.testScope().testScheduler.advanceUntilIdle()

    assertThat(outputStream.toByteArray().toList())
        .containsExactlyElementsIn(TEST_DATA.toList())
        .inOrder()
  }

  @Test
  fun linksStandardError(): Unit = runBlocking {
    val errorStream = ByteArrayOutputStream()
    val coroutines = DaggerTestCoroutines.create()
    val standard = createStandard(coroutines, error = errorStream)

    standard.standardError().publishBytes(TEST_DATA)
    coroutines.testScope().testScheduler.advanceUntilIdle()

    assertThat(errorStream.toByteArray().toList())
        .containsExactlyElementsIn(TEST_DATA.toList())
        .inOrder()
  }

  private fun createStandard(
      coroutines: TestCoroutines,
      input: ByteArrayInputStream = ByteArrayInputStream(ByteArray(0)),
      output: ByteArrayOutputStream = ByteArrayOutputStream(),
      error: ByteArrayOutputStream = ByteArrayOutputStream(),
  ): Standard =
      standard(
          inbound = inbound(coroutines = coroutines),
          outbound = outbound(coroutines = coroutines),
          input = input,
          output = output,
          error = error,
      )

  companion object {
    /** Arbitrary bytes for use in tests. */
    private val TEST_DATA = byteArrayOf(1, 22, 13, -4)
  }
}
