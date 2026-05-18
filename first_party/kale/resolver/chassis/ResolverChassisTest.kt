package com.jackbradshaw.kale.resolver.chassis

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.closet.observable.ObservableClosable.Status
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

        assertThat(subject().closureStatus.value).isEqualTo(Status.CLOSED)
        assertThat(harnessA.closureStatus.value).isEqualTo(Status.CLOSED)
        assertThat(harnessB.closureStatus.value).isEqualTo(Status.CLOSED)
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
        assertThat(exception).hasMessageThat().isEqualTo("This resource is not open.")
      }

  @Test
  fun harnessClosed_chassisRemainsOpen() =
      runBlocking<Unit> {
        val chassis = subject()
        val harness = chassis.open(emptySet())

        harness.close()

        assertThat(chassis.closureStatus.value).isEqualTo(Status.OPEN)
      }

  abstract fun subject(): ResolverChassis
}
