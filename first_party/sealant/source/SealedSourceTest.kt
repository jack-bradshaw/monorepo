package com.jackbradshaw.sealant.source

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import java.lang.IllegalStateException
import kotlin.test.assertFailsWith
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test

abstract class SealedSourceTest<T> {

  private val testScopeHandle = Job()

  private val testScope: CoroutineScope by lazy {
    CoroutineScope(testScopeHandle + testDispatcher())
  }

  @After
  fun tearDown() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  @Test
  fun emit_noSessions_doesNotFail() = runBlocking {
    subject().emit(createEmittableValue())
    taskBarrier().awaitAllIdle()
  }

  @Test
  fun emit_withOneSession_notCollecting_emitOnce_doesNotFail() = runBlocking {
    val session = subject().createSession()
    taskBarrier().awaitAllIdle()

    subject().emit(createEmittableValue())
  }

  @Test
  fun emit_withOneSession_collecting_emitOnce_sessionReceivesValue() = runBlocking {
    val session = subject().createSession()
    taskBarrier().awaitAllIdle()

    val collected = mutableListOf<T>()
    val collection = testScope.launch { session.flow.collect(collected::add) }
    taskBarrier().awaitAllIdle()

    val emission = createEmittableValue()
    subject().emit(emission)
    taskBarrier().awaitAllIdle()

    assertThat(collected).containsExactly(emission).inOrder()
  }

  @Test
  fun emit_withOneSession_emitRepeatedly_sessionReceivesBothValue() = runBlocking {
    val session = subject().createSession()
    taskBarrier().awaitAllIdle()

    val collected = mutableListOf<T>()
    val collection = testScope.launch { session.flow.collect(collected::add) }
    taskBarrier().awaitAllIdle()

    val emission1 = createEmittableValue()
    val emission2 = createEmittableValue()
    subject().emit(emission1)
    subject().emit(emission2)
    taskBarrier().awaitAllIdle()

    assertThat(collected).containsExactly(emission1, emission2).inOrder()
  }

  @Test
  fun emit_withTwoSessions_neitherCollecting_emitOnce_doesNotFail() = runBlocking {
    val session1 = subject().createSession()
    val session2 = subject().createSession()
    taskBarrier().awaitAllIdle()

    subject().emit(createEmittableValue())
  }

  @Test
  fun emit_withTwoSessions_oneCollecting_emitOnce_collectingReceivesValue() = runBlocking {
    val session1 = subject().createSession()
    val session2 = subject().createSession()
    taskBarrier().awaitAllIdle()

    val collected = mutableListOf<T>()
    val collection = testScope.launch { session1.flow.collect(collected::add) }
    taskBarrier().awaitAllIdle()

    val emission = createEmittableValue()
    subject().emit(emission)
    taskBarrier().awaitAllIdle()

    assertThat(collected).containsExactly(emission).inOrder()
  }

  @Test
  fun emit_withTwoSessions_oneCollecting_emitRepeatedly_collectingReceivesValue() = runBlocking {
    val session1 = subject().createSession()
    val session2 = subject().createSession()
    taskBarrier().awaitAllIdle()

    val collected = mutableListOf<T>()
    val collection = testScope.launch { session1.flow.collect(collected::add) }
    taskBarrier().awaitAllIdle()

    val emission1 = createEmittableValue()
    val emission2 = createEmittableValue()
    subject().emit(emission1)
    subject().emit(emission2)
    taskBarrier().awaitAllIdle()

    assertThat(collected).containsExactly(emission1, emission2).inOrder()
  }

  @Test
  fun emit_withTwoSessions_bothCollecting_emitOnce_bothSessionsReceiveValue() = runBlocking {
    val session1 = subject().createSession()
    val session2 = subject().createSession()
    taskBarrier().awaitAllIdle()

    val collected1 = mutableListOf<T>()
    val collection1 = testScope.launch { session1.flow.collect(collected1::add) }
    val collected2 = mutableListOf<T>()
    val collection2 = testScope.launch { session2.flow.collect(collected2::add) }
    taskBarrier().awaitAllIdle()

    val emission = createEmittableValue()
    subject().emit(emission)
    taskBarrier().awaitAllIdle()

    assertThat(collected1).containsExactly(emission).inOrder()
    assertThat(collected2).containsExactly(emission).inOrder()
  }

  @Test
  fun emit_withTwoSessions_bothCollecting_emitRepeatedly_bothSessionsReceiveBothValue() =
      runBlocking {
        val session1 = subject().createSession()
        val session2 = subject().createSession()
        taskBarrier().awaitAllIdle()

        val collected1 = mutableListOf<T>()
        val collection1 = testScope.launch { session1.flow.collect(collected1::add) }
        val collected2 = mutableListOf<T>()
        val collection2 = testScope.launch { session2.flow.collect(collected2::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createEmittableValue()
        val emission2 = createEmittableValue()
        subject().emit(emission1)
        subject().emit(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collected1).containsExactly(emission1, emission2).inOrder()
        assertThat(collected2).containsExactly(emission1, emission2).inOrder()
      }

  @Test
  fun afterClose_emitFails() = runBlocking {
    val session = subject().createSession()
    val sessionCollected = mutableListOf<T>()

    val collection = testScope.launch { session.flow.collect(sessionCollected::add) }
    taskBarrier().awaitAllIdle()

    subject().close()

    val error = assertFailsWith<IllegalStateException> { subject().emit(createEmittableValue()) }
    assertThat(error.message).isEqualTo("This resource is not open.")
  }

  @Test
  fun afterClose_sessionsDoNotReceiveEmissions() = runBlocking {
    val session = subject().createSession()
    val sessionCollected = mutableListOf<T>()

    val collection = testScope.launch { session.flow.collect(sessionCollected::add) }
    taskBarrier().awaitAllIdle()

    subject().close()

    try {
      subject().emit(createEmittableValue())
    } catch (e: IllegalStateException) {
      // expected
    }
    taskBarrier().awaitAllIdle()

    assertThat(sessionCollected).isEmpty()
  }

  protected abstract fun subject(): SealedSource<T>

  protected abstract fun createEmittableValue(): T

  protected abstract fun taskBarrier(): TestingTaskBarrier

  protected abstract fun testDispatcher(): CoroutineDispatcher
}
