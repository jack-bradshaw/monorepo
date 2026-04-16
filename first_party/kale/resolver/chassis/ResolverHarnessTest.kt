package com.jackbradshaw.kale.resolver.chassis

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.kale.model.Versions
import com.jackbradshaw.kale.testing.TestSources.VALID_JAVA_SOURCE
import com.jackbradshaw.kale.testing.TestSources.VALID_KOTLIN_SOURCE
import kotlin.test.assertFailsWith
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

/**
 * Abstract tests that all [ResolverHarness] instances should pass.
 *
 * All tests use the default [Versions] for simplicity and no other versions are checked.
 */
abstract class ResolverHarnessTest {

  @After
  open fun tearDown() {
    runBlocking { subject().close() }
  }

  @Test
  fun withResolver_usedOnce_providesSymbols() =
      runBlocking<Unit> {
        setupSubject(setOf(VALID_KOTLIN_SOURCE))
        val harness = subject()

        var evaluatedName: String? = null
        harness.withResolver { resolver ->
          evaluatedName = resolver.getKSNameFromString("ValidKotlin")?.asString()
        }

        assertThat(evaluatedName).isEqualTo("ValidKotlin")
      }

  @Test
  fun withResolver_usedRepeatedly_providesSymbols() =
      runBlocking<Unit> {
        setupSubject(setOf(VALID_KOTLIN_SOURCE, VALID_JAVA_SOURCE))
        val harness = subject()

        var firstEvaluatedName: String? = null
        harness.withResolver { resolver ->
          firstEvaluatedName = resolver.getKSNameFromString("ValidKotlin")?.asString()
        }

        var secondEvaluatedName: String? = null
        harness.withResolver { resolver ->
          secondEvaluatedName = resolver.getKSNameFromString("ValidJava")?.asString()
        }

        assertThat(firstEvaluatedName).isEqualTo("ValidKotlin")
        assertThat(secondEvaluatedName).isEqualTo("ValidJava")
      }

  @Test
  fun withResolver_usedAfterClose_fails() =
      runBlocking<Unit> {
        setupSubject(setOf(VALID_KOTLIN_SOURCE))
        val harness = subject()
        harness.close()

        val exception = assertFailsWith<IllegalStateException> { harness.withResolver {} }
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo("This harness is closed, withResolver cannot be used.")
      }

  abstract suspend fun setupSubject(sources: Set<com.jackbradshaw.kale.model.Source>)

  abstract suspend fun subject(): ResolverChassis.ResolverHarness
}
