package com.jackbradshaw.kale.processor

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.kale.testing.BasicProcessor
import com.jackbradshaw.kale.testing.ClassCollectingProcessor
import com.jackbradshaw.kale.testing.ExceptionThrowingProcessor
import com.jackbradshaw.kale.testing.TestSources.BROKEN_KOTLIN_SOURCE
import com.jackbradshaw.kale.testing.TestSources.VALID_JAVA_SOURCE
import com.jackbradshaw.kale.testing.TestSources.VALID_KOTLIN_SOURCE
import kotlinx.coroutines.runBlocking
import org.junit.Test

/** All tests use the default [KspVersions] for simplicity and no other versions are checked. */
abstract class ProcessorChassisTest {

  @Test
  fun run_noSources_executesProcessor() =
      runBlocking<Unit> {
        val processor = BasicProcessor()

        val result = subject().runProcoessor(processor = processor, sources = emptySet())

        assertThat(result).isInstanceOf(ProcessorChassis.Result.Success::class.java)
        assertThat(processor.didRunProcess).isTrue()
      }

  @Test
  fun run_noOptions_executesProcessOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject().runProcoessor(processor = processor, sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(ProcessorChassis.Result.Success::class.java)
        assertThat(processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidKotlinSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject().runProcoessor(processor = processor, sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(ProcessorChassis.Result.Success::class.java)
        assertThat(processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidJavaSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject().runProcoessor(processor = processor, sources = setOf(VALID_JAVA_SOURCE))

        assertThat(result).isInstanceOf(ProcessorChassis.Result.Success::class.java)
        assertThat(processor.collectedClassNames).containsExactly("ValidJava")
      }

  @Test
  fun run_withValidMixedSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject()
                .runProcoessor(
                    processor = processor, sources = setOf(VALID_JAVA_SOURCE, VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(ProcessorChassis.Result.Success::class.java)
        assertThat(processor.collectedClassNames).containsExactly("ValidJava", "ValidKotlin")
      }

  @Test
  fun run_withBrokenSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject().runProcoessor(processor = processor, sources = setOf(BROKEN_KOTLIN_SOURCE))

        assertThat(processor.didRunProcess).isTrue()
        assertThat(processor.collectedClassNames).containsExactly("BrokenKotlin")
      }

  @Test
  fun run_withFailingProcessor_suppliesException() =
      runBlocking<Unit> {
        val processor = ExceptionThrowingProcessor()

        val result =
            subject().runProcoessor(processor = processor, sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(ProcessorChassis.Result.Failure::class.java)
        val failure = result as ProcessorChassis.Result.Failure
        assertThat(failure.error).isInstanceOf(RuntimeException::class.java)
        assertThat(failure.error?.message).isEqualTo("foo")
        assertThat(processor.didRunProcess).isTrue()
      }

  abstract fun subject(): ProcessorChassis

  companion object {}
}
