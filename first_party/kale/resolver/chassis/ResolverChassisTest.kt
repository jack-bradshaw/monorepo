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
 * Abstract tests that all [ResolverChassis] instances should pass.
 *
 * All tests use the default [Versions] for simplicity and no other versions are checked.
 */
abstract class ResolverChassisTest {

  @After
  fun tearDown() {
    runBlocking { subject().close() }
  }

  @Test
  fun closeChassis_withoutOpenResources_doesNotFail() =
      runBlocking<Unit> {
        val chassis = subject()

        chassis.close()

        // Should not throw any exceptions
      }

  @Test
  fun closeChassis_withOpenResources_closesAllResources() =
      runBlocking<Unit> {
        val chassis = subject()

        val harnessA = chassis.open(VALID_KOTLIN_SOURCE)
        val harnessB = chassis.open(VALID_JAVA_SOURCE)

        chassis.close()

        assertThat(harnessA.hasTerminalState.value).isTrue()
        assertThat(harnessA.hasTerminatedProcesses.value).isTrue()
        assertThat(harnessB.hasTerminalState.value).isTrue()
        assertThat(harnessB.hasTerminatedProcesses.value).isTrue()
      }

  @Test
  fun open_whileChassisOpen_returnsHarness() =
      runBlocking<Unit> {
        val chassis = subject()

        val harness = chassis.open(emptySet())

        assertThat(harness).isNotNull()
      }

  @Test
  fun open_whileChassisClosed_fails() =
      runBlocking<Unit> {
        val chassis = subject()

        chassis.close()

        val exception = assertFailsWith<IllegalStateException> { chassis.open(emptySet()) }
        assertThat(exception).hasMessageThat().isEqualTo("ResourceManager is closed.")
      }

  @Test
  fun harnessClosed_chassisRemainsOpen() =
      runBlocking<Unit> {
        val chassis = subject()
        val harness = chassis.open(emptySet())

        harness.close()

        assertThat(chassis.hasTerminalState.value).isFalse()
        assertThat(chassis.hasTerminatedProcesses.value).isFalse()
      }

  abstract fun subject(): ResolverChassis
}
