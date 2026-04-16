package com.jackbradshaw.kale.provider

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

/** All tests use the default [KspVersions] for simplicity and no other versions are checked. */
abstract class ProviderChassisTest {

  @Test
  fun run_noSources_executesProvider() =
      runBlocking<Unit> {
        val provider = BasicProvider()

        val result = subject().runProvider(provider = provider, sources = emptySet())

        assertThat(result).isInstanceOf(ProviderChassis.Result.Success::class.java)
        assertThat(provider.processor.didRunProcess).isTrue()
      }

  @Test
  fun run_noOptions_executesProcessOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject().runProvider(provider = provider, sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(ProviderChassis.Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidKotlinSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject().runProvider(provider = provider, sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(ProviderChassis.Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidKotlin")
      }

  @Test
  fun run_withValidJavaSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result = subject().runProvider(provider = provider, sources = setOf(VALID_JAVA_SOURCE))

        assertThat(result).isInstanceOf(ProviderChassis.Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames).containsExactly("ValidJava")
      }

  @Test
  fun run_withValidMixedSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject()
                .runProvider(
                    provider = provider, sources = setOf(VALID_JAVA_SOURCE, VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(ProviderChassis.Result.Success::class.java)
        assertThat(provider.processor.collectedClassNames)
            .containsExactly("ValidJava", "ValidKotlin")
      }

  @Test
  fun run_withBrokenSources_executesProcessorOnSources() =
      runBlocking<Unit> {
        val provider = ClassCollectingProvider()

        val result =
            subject().runProvider(provider = provider, sources = setOf(BROKEN_KOTLIN_SOURCE))

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
                .runProvider(
                    provider = provider,
                    sources = setOf(VALID_KOTLIN_SOURCE),
                    compilerOptions = options)

        assertThat(result).isInstanceOf(ProviderChassis.Result.Success::class.java)
        assertThat(provider.processor.didRunProcess).isTrue()
        assertThat(provider.processor.receivedOptions).containsExactly("testKey", "testValue")
      }

  @Test
  fun run_processorWritesFiles_populatesKspArtefacts() =
      runBlocking<Unit> {
        val provider = CodeGeneratingProvider()

        val result =
            subject().runProvider(provider = provider, sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(ProviderChassis.Result.Success::class.java)
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

        val result =
            subject().runProvider(provider = provider, sources = setOf(VALID_KOTLIN_SOURCE))

        assertThat(result).isInstanceOf(ProviderChassis.Result.Failure::class.java)
        val failure = result as ProviderChassis.Result.Failure
        assertThat(failure.error).isInstanceOf(RuntimeException::class.java)
        assertThat(failure.error!!.message).isEqualTo("foo")
        assertThat(provider.processor.didRunProcess).isTrue()
      }

  abstract fun subject(): ProviderChassis
}
