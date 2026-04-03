package com.jackbradshaw.chronosphere.testingtaskbarrier

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.idleable.Idleable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

/** Abstract tests that all [TestingTaskBarrier]s should pass. */
abstract class TestingTaskBarrierTest {

  @Test
  fun awaitAllIdle_withNoIdleables_returnsImmediately() = runBlocking {
    setupSubject(emptyList())

    val barrier = subject()

    barrier.awaitAllIdle()
  }

  @Test
  fun awaitAllIdle_withIdleIdleables_returnsImmediately() = runBlocking {
    val idleables = List(5) { ControllableIdleable() }
    setupSubject(idleables)
    val barrier = subject()

    idleables.forEach { it.idle = true }

    barrier.awaitAllIdle()
  }

  @Test
  fun awaitAllIdle_withActiveIdleable_blocksUntilIdle() = runBlocking {
    val idleables = List(2) { ControllableIdleable() }
    setupSubject(idleables)
    val barrier = subject()

    val slowIdleable = idleables[1]

    slowIdleable.idle = false

    var barrierFinished = false
    val task =
        launch(Dispatchers.Default) {
          barrier.awaitAllIdle()
          barrierFinished = true
        }

    slowIdleable.idle = true
    task.join()

    assertThat(barrierFinished).isTrue()
  }

  /** Sets up the [subject]. Must be called exactly once per test. */
  abstract fun setupSubject(idleables: List<Idleable>)

  /** Provides the subject under test. Must return the same object on each call. */
  abstract fun subject(): TestingTaskBarrier

  /** An Idleable for use in tests. Idle state can be changed by setting [idle]. */
  class ControllableIdleable : Idleable {
    @Volatile var idle = true

    override fun isIdle() = idle
  }
}
