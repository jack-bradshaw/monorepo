package com.jackbradshaw.sealant.hub

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CompletableDeferred
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Abstract tests that all [Hub] instances should pass. */
@RunWith(JUnit4::class)
import com.jackbradshaw.sealant.connectable.ConnectableTest

abstract class HubTest<T, H : Hub<T>> : ConnectableTest<H>() {

  @Test
  fun beforeClose_createPipeRepeatedly_producesDiscreteSessions() =
      runBlocking<Unit> {
        val session1 = subject().createPipe()
        val session2 = subject().createPipe()

        assertThat(session1).isNotSameInstanceAs(session2)
      }

  @Test
  fun singleUpstreamEmission_allDownstreamConnectablesReceiveIt() =
      runBlocking<Unit> {
        val session1 = subject().createPipe()
        val session2 = subject().createPipe()

        val session1Collected = mutableListOf<T>()
        val session2Collected = mutableListOf<T>()

        testScope().launch { session1.flow.collect(session1Collected::add) }
        testScope().launch { session2.flow.collect(session2Collected::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()

        testScope().launch {
          emitUpstream(emission1)
        }
        taskBarrier().awaitAllIdle()

        assertThat(session1Collected).containsExactly(emission1)
        assertThat(session2Collected).containsExactly(emission1)
      }

  @Test
  fun multipleUpstreamEmissions_allDownstreamConnectablesReceiveThemInOrder() =
      runBlocking<Unit> {
        val session1 = subject().createPipe()
        val session2 = subject().createPipe()

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
  fun afterClose_createPipe_fails() =
      runBlocking<Unit> {
        subject().close()

        val error =
            org.junit.Assert.assertThrows(IllegalStateException::class.java) {
              runBlocking { subject().createPipe() }
            }
        assertThat(error.message)
            .isEqualTo("This hub is closed. Cannot open flows after closure.")
      }

  @Test
  fun onClose_allSessionsClose() =
      runBlocking<Unit> {
        val session1 = subject().createPipe()
        val session2 = subject().createPipe()

        subject().close()

        assertThat(session1.hasTerminalState.value).isTrue()
        assertThat(session1.hasTerminatedProcesses.value).isTrue()
        assertThat(session2.hasTerminalState.value).isTrue()
        assertThat(session2.hasTerminatedProcesses.value).isTrue()
      }

  @Test
  fun onSessionClosed_hubRemainsOpen() =
      runBlocking<Unit> {
        val session1 = subject().createPipe()

        session1.close()

        assertThat(subject().hasTerminalState.value).isFalse()
        assertThat(subject().hasTerminatedProcesses.value).isFalse()
      }

  @Test
  fun afterClose_sessionsDoNotReceiveEmissionsFromUpstream() =
      runBlocking<Unit> {
        val session = subject().createPipe()
        val sessionCollected = mutableListOf<T>()

        testScope().launch { session.flow.collect(sessionCollected::add) }
        taskBarrier().awaitAllIdle()

        subject().close()

        testScope().launch { emitUpstream(createValue()) }
        taskBarrier().awaitAllIdle()

        assertThat(sessionCollected).isEmpty()
      }

  @Test
  fun beforeClose_hubIsUncompressible_slowConsumerSuspendsUpstream() =
      runBlocking<Unit> {
        val fastPipe = subject().createPipe()
        val slowPipe = subject().createPipe()

        val fastCollected = mutableListOf<T>()
        val slowCollected = mutableListOf<T>()

        val slowConsumerReady = CompletableDeferred<Unit>()
        val slowConsumerProceed = CompletableDeferred<Unit>()

        testScope().launch { fastPipe.flow.collect(fastCollected::add) }
        testScope().launch {
            slowPipe.flow.collect {
                slowCollected.add(it)
                slowConsumerReady.complete(Unit)
                slowConsumerProceed.await()
            }
        }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()

        var upstreamEmittedSecondValue = false
        testScope().launch {
          emitUpstream(emission1)
          emitUpstream(emission2)
          upstreamEmittedSecondValue = true
        }
        
        slowConsumerReady.await()
        taskBarrier().awaitAllIdle()

        assertThat(fastCollected).containsExactly(emission1)
        assertThat(upstreamEmittedSecondValue).isFalse()

        slowConsumerProceed.complete(Unit)
        taskBarrier().awaitAllIdle()

        assertThat(upstreamEmittedSecondValue).isTrue()
        assertThat(fastCollected).containsExactly(emission1, emission2).inOrder()
        assertThat(slowCollected).containsExactly(emission1, emission2).inOrder()
      }

  protected abstract suspend fun subject(): H

  protected abstract suspend fun createValue(): T

  protected abstract suspend fun emitUpstream(value: T)

  protected abstract suspend fun testScope(): CoroutineScope

  protected abstract suspend fun taskBarrier(): TestingTaskBarrier
}
