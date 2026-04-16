package com.jackbradshaw.kale.provider

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.kale.model.Log
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.model.Versions
import com.jackbradshaw.kale.testing.BasicProvider
import com.jackbradshaw.kale.testing.ClassCollectingProvider
import com.jackbradshaw.kale.testing.CodeGeneratingProvider
import com.jackbradshaw.kale.testing.ExceptionThrowingProvider
import com.jackbradshaw.kale.testing.LoggingProvider
import com.jackbradshaw.kale.testing.OptionsCollectingProvider
import com.jackbradshaw.kale.testing.TestSources.BROKEN_KOTLIN_SOURCE
import com.jackbradshaw.kale.testing.TestSources.VALID_JAVA_SOURCE
import com.jackbradshaw.kale.testing.TestSources.VALID_KOTLIN_SOURCE
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Abstract tests that all [ProviderRunner] instances should pass.
 *
 * All tests use the default [Versions] for simplicity and no other versions are checked.
 */
abstract class ProviderRunnerTest {

  @Test
  fun run_noProviders_doesNotFail() =
      runBlocking<Unit> {
        val result =
            subject().runProviders(sources = setOf(VALID_KOTLIN_SOURCE), providers = emptySet())

        assertThat(result).isInstanceOf(Result.Success::class.java)
      }

  @Test
  fun run_noSources_executesProvider() =
      runBlocking<Unit> {
        val provider = BasicProvider()

        val result = subject().runProvider(provider = provider, sources = emptySet())

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(provider.processor.didRunProcess).isTrue()
      }

  @Test
  fun run_noOptions_executesProcessOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject()
                .runProvider(
                    provider = provider, sources = setOf(VALID_KOTLIN_SOURCE), options = emptyMap())

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withSingleProvider_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject()
                .runProviders(providers = setOf(provider), sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(provider.processor.didRunProcess).isTrue()
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withMultipleProviders_executesAllProcessorsOnSources() =
      runBlocking<Unit> {
        val provider1 = ClassCollectingProvider()
        val provider2 = ClassCollectingProvider()

        val result =
            subject()
                .runProviders(
                    providers = setOf(provider1, provider2), sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(provider1.processor.didRunProcess).isTrue()
        assertThat(provider1.processor.collectedClassNames).containsExactly("ValidKotlin")
        assertThat(provider2.processor.didRunProcess).isTrue()
        assertThat(provider2.processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidKotlinSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject()
                .runProviders(providers = setOf(provider), sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidJavaSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject().runProviders(providers = setOf(provider), sources = setOf(VALID_JAVA_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidJava")
      }

  @Test
  fun run_withValidMixedSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject()
                .runProviders(
                    providers = setOf(provider),
                    sources = setOf(VALID_JAVA_SOURCE, VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames)
            .containsExactly("ValidJava", "ValidKotlin")
      }

  @Test
  fun run_withBrokenSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject()
                .runProviders(providers = setOf(provider), sources = setOf(BROKEN_KOTLIN_SOURCE))

        assertThat(provider.processor.didRunProcess).isTrue()
        assertThat(provider.processor.collectedClassNames).containsExactly("BrokenKotlin")
      }

  @Test
  fun run_withOptions_makesAvailableToProvider() =
      runBlocking<Unit> {
        val provider = OptionsCollectingProvider()
        val options = mapOf("testKey" to "testValue")

        val result =
            subject()
                .runProviders(
                    providers = setOf(provider),
                    sources = setOf(VALID_KOTLIN_SOURCE),
                    options = options)

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(provider.processor.didRunProcess).isTrue()
        assertThat(provider.options).containsExactly("testKey", "testValue")
      }

  @Test
  fun run_processorWritesFiles_populatesKspArtifacts() =
      runBlocking<Unit> {
        val provider = CodeGeneratingProvider()

        val result =
            subject()
                .runProviders(providers = setOf(provider), sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Success::class.java)
        assertThat(provider.processor.didRunProcess).isTrue()

        val expectedKotlinSource =
            Source(
                packageName = "test",
                fileName = "GeneratedFile",
                extension = "kt",
                contents = "package test\nclass GeneratedClass\n")
        assertThat(result.artifacts.kotlinSources.first()).isEqualTo(expectedKotlinSource)

        val expectedJavaSource =
            Source(
                packageName = "test",
                fileName = "GeneratedJavaFile",
                extension = "java",
                contents = "package test;\nclass GeneratedJavaClass {}\n")
        assertThat(result.artifacts.javaSources.first()).isEqualTo(expectedJavaSource)

        val expectedResource =
            com.jackbradshaw.kale.model.Resource(
                directoryPath = "test",
                fileName = "GeneratedResource",
                extension = "txt",
                contents = "Generated text".encodeToByteArray().toList())
        assertThat(result.artifacts.resources.first()).isEqualTo(expectedResource)
      }

  @Test
  fun run_withFailingProvider_suppliesException() =
      runBlocking<Unit> {
        val provider = ExceptionThrowingProvider()

        val result =
            subject()
                .runProviders(providers = setOf(provider), sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(Result.Failure::class.java)
        val failure = result as Result.Failure
        assertThat(failure.error).isInstanceOf(RuntimeException::class.java)
        assertThat(failure.error!!.message).isEqualTo("foo")
        assertThat(provider.processor.didRunProcess).isTrue()
      }

  @Test
  fun run_withLogging_providesLogsInResult() =
      runBlocking<Unit> {
        val provider = LoggingProvider()
        val result =
            subject()
                .runProviders(providers = setOf(provider), sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(provider.processor.didRunProcess).isTrue()
        val logs = result.logs.filterNot { it is Log.Unspecified && it.message.startsWith("round") }

        assertThat(logs).hasSize(5)
        assertThat(logs[0]).isInstanceOf(Log.Unspecified::class.java)
        assertThat((logs[0] as Log.Unspecified).message).isEqualTo("test logging")

        assertThat(logs[1]).isInstanceOf(Log.Info::class.java)
        assertThat((logs[1] as Log.Info).message).isEqualTo("test info")

        assertThat(logs[2]).isInstanceOf(Log.Warning::class.java)
        assertThat((logs[2] as Log.Warning).message).isEqualTo("test warn")

        assertThat(logs[3]).isInstanceOf(Log.Error::class.java)
        assertThat((logs[3] as Log.Error).message).isEqualTo("test error")

        assertThat(logs[4]).isInstanceOf(Log.Exception::class.java)
        assertThat((logs[4] as Log.Exception).error).isInstanceOf(RuntimeException::class.java)
        assertThat((logs[4] as Log.Exception).error.message).isEqualTo("test exception")
      }

  @Test
  fun run_singleProviderVariant_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject().runProvider(provider = provider, sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(provider.processor.didRunProcess).isTrue()
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  abstract fun subject(): ProviderRunner
}
