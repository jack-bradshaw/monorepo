package com.jackbradshaw.sealant.hub

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.observable.ObservableClosable
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Abstract tests that all [SealedHub] instances should pass. */
@RunWith(JUnit4::class)
abstract class SealedHubTest<T> {

  private val testScopeHandle = Job()

  private val testScope: CoroutineScope by lazy {
    CoroutineScope(testScopeHandle + testDispatcher())
  }

  @After
  fun tearDown() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  @Test
  fun createSessionRepeatedly_beforeClose_producesDiscreteSessions() =
      runBlocking<Unit> {
        val session1 = subject().createSession()
        val session2 = subject().createSession()

        assertThat(session1).isNotSameInstanceAs(session2)
      }

  @Test
  fun upstreamEmits_beforeClose_noSessions_doesNotFail() =
      runBlocking<Unit> {
        emitUpstream(createValue())
        taskBarrier().awaitAllIdle()
      }

  @Test
  fun upstreamEmits_beforeClose_withOneSession_notCollecting_emitOnce_doesNotFail() =
      runBlocking<Unit> {
        val session = subject().createSession()
        taskBarrier().awaitAllIdle()

        emitUpstream(createValue())
        taskBarrier().awaitAllIdle()
      }

  @Test
  fun upstreamEmits_beforeClose_withOneSession_collecting_emitOnce_sessionReceivesValue() =
      runBlocking<Unit> {
        val session = subject().createSession()
        taskBarrier().awaitAllIdle()

        val collected = mutableListOf<T>()
        testScope.launch { session.flow.collect(collected::add) }
        taskBarrier().awaitAllIdle()

        val emission = createValue()
        emitUpstream(emission)
        taskBarrier().awaitAllIdle()

        assertThat(collected).containsExactly(emission).inOrder()
      }

  @Test
  fun upstreamEmits_beforeClose_withOneSession_emitRepeatedly_sessionReceivesBothValue() =
      runBlocking<Unit> {
        val session = subject().createSession()
        taskBarrier().awaitAllIdle()

        val collected = mutableListOf<T>()
        testScope.launch { session.flow.collect(collected::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collected).containsExactly(emission1, emission2).inOrder()
      }

  @Test
  fun upstreamEmits_beforeClose_withTwoSessions_neitherCollecting_emitOnce_doesNotFail() =
      runBlocking<Unit> {
        val session1 = subject().createSession()
        val session2 = subject().createSession()
        taskBarrier().awaitAllIdle()

        emitUpstream(createValue())
        taskBarrier().awaitAllIdle()
      }

  @Test
  fun upstreamEmits_beforeClose_withTwoSessions_oneCollecting_emitOnce_collectingReceivesValue() =
      runBlocking<Unit> {
        val session1 = subject().createSession()
        val session2 = subject().createSession()
        taskBarrier().awaitAllIdle()

        val collected = mutableListOf<T>()
        testScope.launch { session1.flow.collect(collected::add) }
        taskBarrier().awaitAllIdle()

        val emission = createValue()
        emitUpstream(emission)
        taskBarrier().awaitAllIdle()

        assertThat(collected).containsExactly(emission).inOrder()
      }

  @Test
  fun upstreamEmits_beforeClose_withTwoSessions_oneCollecting_emitRepeatedly_collectingReceivesValue() =
      runBlocking<Unit> {
        val session1 = subject().createSession()
        val session2 = subject().createSession()
        taskBarrier().awaitAllIdle()

        val collected = mutableListOf<T>()
        testScope.launch { session1.flow.collect(collected::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collected).containsExactly(emission1, emission2).inOrder()
      }

  @Test
  fun upstreamEmits_beforeClose_withTwoSessions_bothCollecting_emitOnce_bothSessionsReceiveValue() =
      runBlocking<Unit> {
        val session1 = subject().createSession()
        val session2 = subject().createSession()
        taskBarrier().awaitAllIdle()

        val collected1 = mutableListOf<T>()
        val collected2 = mutableListOf<T>()
        testScope.launch { session1.flow.collect(collected1::add) }
        testScope.launch { session2.flow.collect(collected2::add) }
        taskBarrier().awaitAllIdle()

        val emission = createValue()
        emitUpstream(emission)
        taskBarrier().awaitAllIdle()

        assertThat(collected1).containsExactly(emission).inOrder()
        assertThat(collected2).containsExactly(emission).inOrder()
      }

  @Test
  fun upstreamEmits_beforeClose_withTwoSessions_bothCollecting_emitRepeatedly_bothSessionsReceiveValue() =
      runBlocking<Unit> {
        val session1 = subject().createSession()
        val session2 = subject().createSession()
        taskBarrier().awaitAllIdle()

        val collected1 = mutableListOf<T>()
        val collected2 = mutableListOf<T>()
        testScope.launch { session1.flow.collect(collected1::add) }
        testScope.launch { session2.flow.collect(collected2::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collected1).containsExactly(emission1, emission2).inOrder()
        assertThat(collected2).containsExactly(emission1, emission2).inOrder()
      }

  @Test
  fun onClose_allSessionsClose() =
      runBlocking<Unit> {
        val session1 = subject().createSession()
        val session2 = subject().createSession()

        subject().close()

        assertThat(session1.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(session1.closureStatus.value).isEqualTo(ObservableClosable.Status.CLOSED)
        assertThat(session2.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(session2.closureStatus.value).isEqualTo(ObservableClosable.Status.CLOSED)
      }

  @Test
  fun onSessionClosed_hubRemainsOpen() =
      runBlocking<Unit> {
        val session1 = subject().createSession()

        session1.close()

        assertThat(subject().closureStatus.value).isEqualTo(ObservableClosable.Status.OPEN)
        assertThat(subject().closureStatus.value).isNotEqualTo(ObservableClosable.Status.CLOSED)
      }

  @Test
  fun createSession_afterClose_fails() =
      runBlocking<Unit> {
        subject().close()

        val error =
            org.junit.Assert.assertThrows(IllegalStateException::class.java) {
              runBlocking { subject().createSession() }
            }
        assertThat(error.message)
            .isEqualTo("This hub is closed. Cannot open sessions after closure.")
      }

  @Test
  fun upstreamEmits_afterClose_sessionDoesNotReceiveValue() =
      runBlocking<Unit> {
        val session = subject().createSession()
        val sessionCollected = mutableListOf<T>()

        testScope.launch { session.flow.collect(sessionCollected::add) }
        taskBarrier().awaitAllIdle()

        subject().close()

        testScope.launch { emitUpstream(createValue()) }
        taskBarrier().awaitAllIdle()

        assertThat(sessionCollected).isEmpty()
      }

  /** The hub under test. */
  protected abstract suspend fun subject(): SealedHub<T>

  /** Creates a new, unique value to be emitted by the upstream flow. */
  protected abstract suspend fun createValue(): T

  /** Emits a value from the upstream flow. */
  protected abstract suspend fun emitUpstream(value: T)

  /** The coroutine scope used for testing. */
  protected abstract fun testDispatcher(): CoroutineDispatcher

  /** A task barrier used to synchronize with internal coroutines. */
  protected abstract suspend fun taskBarrier(): TestingTaskBarrier
}
