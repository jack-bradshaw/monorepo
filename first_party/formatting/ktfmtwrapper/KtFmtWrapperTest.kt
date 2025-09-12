package com.jackbradshaw.formatting.ktfmtwrapper

import com.google.common.truth.Truth.assertThat
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import org.junit.After
import org.junit.Before
import org.junit.Test

class KtFmtWrapperTest {

  private lateinit var kotlinSource: File

  @Before
  fun setup() {
    kotlinSource =
        File("Main.kt").also {
          it.createNewFile()
          it.writeText(SOURCE_UNFORMATTED)
        }
  }

  @After
  fun tearDown() {
    kotlinSource.delete()
  }

  @Test
  fun whenRun_doesNotPrintIncrementalUpdates() {
    val normalErrorStream = System.err
    val testErrorStream = ByteArrayOutputStream()
    System.setErr(PrintStream(testErrorStream))

    // Use try/finally to ensure system error stream is always returned to normal.
    try {
      formatKotlinSource()
    } finally {
      System.setErr(normalErrorStream)
    }

    verifyIncrementalUpdatesNotPrinted(testErrorStream)
    testErrorStream.close()
  }

  @Test
  fun whenRun_formatsCode() {
    formatKotlinSource()
    verifyKotlinSourceFormatted()
  }

  /** Runs the formatter on [kotlinSource]. */
  private fun formatKotlinSource() {
    KtFmtWrapper.main(arrayOf(kotlinSource.absolutePath))
  }

  /** Verifies no incremental updates were written to [outputStream]. */
  private fun verifyIncrementalUpdatesNotPrinted(outputStream: ByteArrayOutputStream) {
    assertThat(outputStream.toString()).isEmpty()
  }

  /** Verifies the contents of [kotlinSource] have been autoformatted by ktfmt. */
  private fun verifyKotlinSourceFormatted() {
    assertThat(kotlinSource.readText()).isEqualTo(SOURCE_FORMATTED)
  }

  companion object {
    /** Kotlin code that has not been formatted yet. */
    private const val SOURCE_UNFORMATTED = "fun main() {      }"

    /** Kotlin code equivalent to [SOURCE_UNFORMATTED] but formatted according to ktfmt. */
    private const val SOURCE_FORMATTED = "fun main() {}\n"
  }
}
