package com.jackbradshaw.kale.ksprunner

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.testing.BasicProvider
import com.jackbradshaw.kale.testing.ClassCollectingProvider
import com.jackbradshaw.kale.testing.CodeGeneratingProvider
import com.jackbradshaw.kale.testing.ExceptionThrowingProvider
import com.jackbradshaw.kale.testing.OptionsCollectingProvider
import com.jackbradshaw.kale.testing.TestSources.BROKEN_KOTLIN_SOURCE
import com.jackbradshaw.kale.testing.TestSources.VALID_JAVA_SOURCE
import com.jackbradshaw.kale.testing.TestSources.VALID_KOTLIN_SOURCE
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Abstract tests that all [KspRunner] instances should pass.
 *
 * All tests use the default [Versions] for simplicity and no other versions are checked.
 */
abstract class KspRunnerTest {

  @Test
  fun run_noProviders_doesNotFail() =
      runBlocking<Unit> {
        val result =
            subject()
                .runKsp(
                    sources = setOf(VALID_KOTLIN_SOURCE),
                    providers = emptySet(),
                    options = DEFAULT_OPTIONS)

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
      }

  @Test
  fun run_noSources_executesProcessor() =
      runBlocking<Unit> {
        val basicProvider = BasicProvider()
        val result =
            subject()
                .runKsp(
                    sources = emptySet(),
                    providers = setOf(basicProvider),
                    options = DEFAULT_OPTIONS)

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
        assertThat(basicProvider.processor.didRunProcess).isTrue()
      }

  @Test
  fun run_noOptions_executesProcessOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()
        val result =
            subject()
                .runKsp(
                    setOf(VALID_KOTLIN_SOURCE), providers = setOf(provider), options = emptyMap())

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames).contains("ValidKotlin")
      }

  @Test
  fun run_withSingleProvider_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()
        val result =
            subject().runKsp(sources = setOf(VALID_KOTLIN_SOURCE), providers = setOf(provider))

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
        assertThat(provider.processor.didRunProcess).isTrue()
        assertThat(provider.processor.collectedClassNames).contains("ValidKotlin")
      }

  @Test
  fun run_wtihMultipleProviders_executesAllProcessorsOnSources() =
      runBlocking<Unit> {
        val provider1 = ClassCollectingProvider()
        val provider2 = ClassCollectingProvider()

        val result =
            subject()
                .runKsp(
                    sources = setOf(VALID_KOTLIN_SOURCE), providers = setOf(provider1, provider2))

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
        assertThat(provider1.processor.didRunProcess).isTrue()
        assertThat(provider1.processor.collectedClassNames).containsExactly("ValidKotlin")
        assertThat(provider2.processor.didRunProcess).isTrue()
        assertThat(provider2.processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidKotlinSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result = subject().runKsp(setOf(VALID_KOTLIN_SOURCE), providers = setOf(provider))

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidJavaSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result = subject().runKsp(setOf(VALID_JAVA_SOURCE), providers = setOf(provider))

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidJava")
      }

  @Test
  fun run_withValidMixedSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject()
                .runKsp(setOf(VALID_JAVA_SOURCE, VALID_KOTLIN_SOURCE), providers = setOf(provider))

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames)
            .containsExactly("ValidJava", "ValidKotlin")
      }

  @Test
  fun run_withBrokenSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result = subject().runKsp(setOf(BROKEN_KOTLIN_SOURCE), providers = setOf(provider))

        assertThat(provider.processor.didRunProcess).isTrue()
        assertThat(provider.processor.collectedClassNames).containsExactly("BrokenKotlin")
      }

  @Test
  fun run_withOptions_makesAvailableToProvider() =
      runBlocking<Unit> {
        val provider = OptionsCollectingProvider()
        val result =
            subject()
                .runKsp(
                    sources = setOf(VALID_KOTLIN_SOURCE),
                    providers = setOf(provider),
                    options = mapOf("testKey" to "testValue"))

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
        assertThat(provider.processor.didRunProcess).isTrue()
        assertThat(provider.processor.receivedOptions).containsExactly("testKey", "testValue")
      }

  @Test
  fun run_processorWritesFiles_populatesKspArtefacts() =
      runBlocking<Unit> {
        val result =
            subject()
                .runKsp(
                    sources = setOf(VALID_KOTLIN_SOURCE),
                    providers = setOf(CodeGeneratingProvider()))

        assertThat(result).isInstanceOf(KspRunner.Result.Success::class.java)
        val expectedSource =
            JvmSource(
                packageName = "",
                fileName = "GeneratedFile",
                extension = "kt",
                contents = "package test\nclass GeneratedClass\n")
        assertThat(result.artefacts.kotlinSources.first()).isEqualTo(expectedSource)
      }

  @Test
  fun run_withFailingProvider_suppliesException() =
      runBlocking<Unit> {
        val provider = ExceptionThrowingProvider()
        val result = subject().runKsp(setOf(VALID_KOTLIN_SOURCE), providers = setOf(provider))

        assertThat(result).isInstanceOf(KspRunner.Result.Failure::class.java)
        val failure = result as KspRunner.Result.Failure
        assertThat(failure.error).isInstanceOf(RuntimeException::class.java)
        assertThat(failure.error?.message).isEqualTo("foo")
        assertThat(provider.processor.didRunProcess).isTrue()
      }

  @Test
  fun run_singleProviderVariant_withFailingProvider_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result = subject().runKsp(setOf(BROKEN_KOTLIN_SOURCE), providers = provider)

        assertThat(provider.processor.didRunProcess).isTrue()
        assertThat(provider.processor.collectedClassNames).containsExactly("BrokenKotlin")
      }

  /** Gets the subject under test. The same instance must be returned each call. */
  abstract fun subject(): KspRunner

  companion object {
    /** Default options for use in tests where options are not a primary concern. */
    val DEFAULT_OPTIONS = mapOf("optionKey" to "optionValue")
  }
}
