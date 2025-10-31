package com.jackbradshaw.sasync

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.coroutines.testing.DaggerTestCoroutines
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
class SasyncTest {

  @Test
  fun linksStandardInput(): Unit = runBlocking {
    val inputStream = ByteArrayInputStream(TEST_DATA)
    val coroutines = DaggerTestCoroutines.create()
    val sasync = sasync(standardInput = inputStream, coroutines = coroutines)

    val collected = mutableListOf<Byte>()
    coroutines.launcher().launchEagerly {
      sasync.standardInput().observeFlattened().take(TEST_DATA.size).toList(collected)
    }
    coroutines.testScope().testScheduler.advanceUntilIdle()

    assertThat(collected).containsExactlyElementsIn(TEST_DATA.toList()).inOrder()
  }

  @Test
  fun linksStandardOutput(): Unit = runBlocking {
    val outputStream = ByteArrayOutputStream()
    val coroutines = DaggerTestCoroutines.create()
    val sasync = sasync(standardOutput = outputStream, coroutines = coroutines)

    val collected = mutableListOf<Byte>()
    coroutines.launcher().launchEagerly {
      sasync.standardInput().observeFlattened().take(TEST_DATA.size).toList(collected)
    }
    sasync.standardOutput().publishBytes(TEST_DATA)
    coroutines.testScope().testScheduler.advanceUntilIdle()

    assertThat(outputStream.toByteArray().toList())
        .containsExactlyElementsIn(TEST_DATA.toList())
        .inOrder()
  }

  @Test
  fun linksStandardError(): Unit = runBlocking {
    val errorStream = ByteArrayOutputStream()
    val coroutines = DaggerTestCoroutines.create()
    val sasync = sasync(standardError = errorStream, coroutines = coroutines)

    sasync.standardError().publishBytes(TEST_DATA)
    coroutines.testScope().testScheduler.advanceUntilIdle()

    assertThat(errorStream.toByteArray().toList())
        .containsExactlyElementsIn(TEST_DATA.toList())
        .inOrder()
  }

  companion object {
    /** Arbitrary bytes for use in tests. */
    private val TEST_DATA = byteArrayOf(1, 22, 13, -4)
  }
}
