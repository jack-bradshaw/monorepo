package com.jackbradshaw.kale.processor

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Versions
import com.jackbradshaw.kale.testing.BasicProcessor
import com.jackbradshaw.kale.testing.ClassCollectingProcessor
import com.jackbradshaw.kale.testing.ExceptionThrowingProcessor
import com.jackbradshaw.kale.testing.TestSources.BROKEN_KOTLIN_SOURCE
import com.jackbradshaw.kale.testing.TestSources.VALID_JAVA_SOURCE
import com.jackbradshaw.kale.testing.TestSources.VALID_KOTLIN_SOURCE
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Abstract tests that all [ProcessorRunner] instances should pass.
 *
 * All tests use the default [Versions] for simplicity and no other versions are checked.
 */
abstract class ProcessorRunnerTest {

  @Test
  fun run_noProcessors_doesNotFail() =
      runBlocking<Unit> {
        val result =
            subject().runProcessors(sources = setOf(VALID_KOTLIN_SOURCE), processors = emptySet())

        assertThat(result).isInstanceOf(Result.Success::class.java)
      }

  @Test
  fun run_noSources_executesProcessor() =
      runBlocking<Unit> {
        val processor = BasicProcessor()

        val result = subject().runProcessor(processor = processor, sources = emptySet())

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(processor.didRunProcess).isTrue()
      }

  @Test
  fun run_noOptions_executesProcessOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject()
                .runProcessor(
                    processor = processor,
                    sources = setOf(VALID_KOTLIN_SOURCE),
                    options = emptyMap())

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withSingleProcessor_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject()
                .runProcessors(processors = setOf(processor), sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(processor.didRunProcess).isTrue()
        assertThat(processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withMultipleProcessors_executesAllProcessorsOnSources() =
      runBlocking<Unit> {
        val processor1 = ClassCollectingProcessor()
        val processor2 = ClassCollectingProcessor()

        val result =
            subject()
                .runProcessors(
                    processors = setOf(processor1, processor2),
                    sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(processor1.didRunProcess).isTrue()
        assertThat(processor1.collectedClassNames).containsExactly("ValidKotlin")
        assertThat(processor2.didRunProcess).isTrue()
        assertThat(processor2.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidKotlinSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject()
                .runProcessors(processors = setOf(processor), sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidJavaSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject()
                .runProcessors(processors = setOf(processor), sources = setOf(VALID_JAVA_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(processor.collectedClassNames).containsExactly("ValidJava")
      }

  @Test
  fun run_withValidMixedSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject()
                .runProcessors(
                    processors = setOf(processor),
                    sources = setOf(VALID_JAVA_SOURCE, VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(processor.collectedClassNames).containsExactly("ValidJava", "ValidKotlin")
      }

  @Test
  fun run_withBrokenSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject()
                .runProcessors(processors = setOf(processor), sources = setOf(BROKEN_KOTLIN_SOURCE))

        assertThat(processor.didRunProcess).isTrue()
        assertThat(processor.collectedClassNames).containsExactly("BrokenKotlin")
      }

  @Test
  fun run_withFailingProcessor_suppliesException() =
      runBlocking<Unit> {
        val processor = ExceptionThrowingProcessor()

        val result =
            subject()
                .runProcessors(processors = setOf(processor), sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Failure::class.java)
        val failure = result as Result.Failure
        assertThat(failure.error).isInstanceOf(RuntimeException::class.java)
        assertThat(failure.error!!.message).isEqualTo("foo")
        assertThat(processor.didRunProcess).isTrue()
      }

  @Test
  fun run_singleProcessorVariant_executesProcessorOnSources() =
      runBlocking<Unit> {
        val processor = ClassCollectingProcessor()

        val result =
            subject().runProcessor(processor = processor, sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(processor.didRunProcess).isTrue()
        assertThat(processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  abstract fun subject(): ProcessorRunner
}
