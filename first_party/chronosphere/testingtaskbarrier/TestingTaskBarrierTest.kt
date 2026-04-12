package com.jackbradshaw.chronosphere.testingtaskbarrier

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.idleable.Idleable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Abstract tests that all [TestingTaskBarrier]s should pass.
 *
 * The `isIdle` function is not included in the abstract tests because the definition of idle allows
 * implementations to include arbitrary computation. Implementation tests should check the `isIdle`
 * functionality directly.
 */
abstract class TestingTaskBarrierTest {

  @Test
  fun awaitAllIdle_nothingGated_returnsImmediately() = runBlocking {
    setupSubject(emptySet())

    val barrier = subject()

    barrier.awaitAllIdle()
  }

  @Test
  fun awaitAllIdle_allGatedIdle_returnsImmediately() = runBlocking {
    val idleables = List(5) { ControllableIdleable() }
    setupSubject(idleables.toSet())
    val barrier = subject()

    // No action to perform, idleables start as idle = true

    barrier.awaitAllIdle()
  }

  @Test
  fun awaitAllIdle_oneGatedNonIdle_blocksUntilIdle() = runBlocking {
    val idleables = List(2) { ControllableIdleable() }
    setupSubject(idleables.toSet())
    val barrier = subject()

    // All others start as idle = true
    val slowIdleable = idleables[1]
    slowIdleable.idle = false

    var barrierFinished = false
    val blockedTask =
        launch(Dispatchers.Default) {
          barrier.awaitAllIdle()
          barrierFinished = true
        }

    slowIdleable.idle = true
    blockedTask.join()

    assertThat(barrierFinished).isTrue()
  }

  /**
   * Sets up the [subject].
   *
   * Will be called exactly once per test. The subject must gate [gating].
   */
  abstract fun setupSubject(gating: Set<Idleable>)

  /**
   * Provides the subject under test.
   *
   * Must return the same object on each call. Not safe to call before [setupSubject].
   */
  abstract fun subject(): TestingTaskBarrier

  /**
   * An Idleable for use in tests.
   *
   * The idle state can be read and set via [isIdle].
   */
  class ControllableIdleable : Idleable {
    @Volatile var idle = true

    override fun isIdle() = idle
  }
}
