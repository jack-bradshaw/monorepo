package com.jackbradshaw.sealant.hub

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Abstract tests that all [SealedHub] instances should pass. */
@RunWith(JUnit4::class)
abstract class SealedHubTest<T> {

  @Test
  fun beforeClose_createFlowRepeatedly_producesDiscreteSessions() =
      runBlocking<Unit> {
        val session1 = subject().createFlow()
        val session2 = subject().createFlow()

        assertThat(session1).isNotSameInstanceAs(session2)
      }

  @Test
  fun beforeClose_emissionFromUpstream_propagatesToAllSessions() =
      runBlocking<Unit> {
        val session1 = subject().createFlow()
        val session2 = subject().createFlow()

        val session1Collected = mutableListOf<T>()
        val session2Collected = mutableListOf<T>()

        testScope().launch { session1.flow.collect(session1Collected::add) }
        testScope().launch { session2.flow.collect(session2Collected::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()

        testScope().launch {
          emitUpstream(emission1)
          emitUpstream(emission2)
        }
        taskBarrier().awaitAllIdle()

        assertThat(session1Collected).containsExactly(emission1, emission2).inOrder()
        assertThat(session2Collected).containsExactly(emission1, emission2).inOrder()
      }

  @Test
  fun afterClose_createFlow_fails() =
      runBlocking<Unit> {
        subject().close()

        val error =
            org.junit.Assert.assertThrows(IllegalStateException::class.java) {
              runBlocking { subject().createFlow() }
            }
        assertThat(error.message)
            .isEqualTo("This hub is closed. Cannot open flows after closure.")
      }

  @Test
  fun onClose_allSessionsClose() =
      runBlocking<Unit> {
        val session1 = subject().createFlow()
        val session2 = subject().createFlow()

        subject().close()

        assertThat(session1.hasTerminalState.value).isTrue()
        assertThat(session1.hasTerminatedProcesses.value).isTrue()
        assertThat(session2.hasTerminalState.value).isTrue()
        assertThat(session2.hasTerminatedProcesses.value).isTrue()
      }

  @Test
  fun onSessionClosed_hubRemainsOpen() =
      runBlocking<Unit> {
        val session1 = subject().createFlow()

        session1.close()

        assertThat(subject().hasTerminalState.value).isFalse()
        assertThat(subject().hasTerminatedProcesses.value).isFalse()
      }

  @Test
  fun afterClose_sessionsDoNotReceiveEmissionsFromUpstream() =
      runBlocking<Unit> {
        val session = subject().createFlow()
        val sessionCollected = mutableListOf<T>()

        testScope().launch { session.flow.collect(sessionCollected::add) }
        taskBarrier().awaitAllIdle()

        subject().close()

        testScope().launch { emitUpstream(createValue()) }
        taskBarrier().awaitAllIdle()

        assertThat(sessionCollected).isEmpty()
      }

  protected abstract suspend fun subject(): SealedHub<T>

  protected abstract suspend fun createValue(): T

  protected abstract suspend fun emitUpstream(value: T)

  protected abstract suspend fun testScope(): CoroutineScope

  protected abstract suspend fun taskBarrier(): TestingTaskBarrier
}
