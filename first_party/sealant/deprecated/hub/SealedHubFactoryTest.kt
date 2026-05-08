package com.jackbradshaw.sealant.hub

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.sealant.flow.SealedFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Abstract tests that all [SealedHub.Factory] instances should pass. */
@RunWith(JUnit4::class)
abstract class SealedHubFactoryTest<T> {

  @Test
  fun create_eachCall_providesNewInstance() =
      runBlocking<Unit> {
        val underlyingFlow = underlyingFlow()

        val instance1 = subject().create(underlyingFlow)
        val instance2 = subject().create(underlyingFlow)

        assertThat(instance1).isNotSameInstanceAs(instance2)
      }

  @Test
  fun create_returnedHub_linkedToUnderlyingFlow() =
      runBlocking<Unit> {
        val flow = underlyingFlow()
        val hub = subject().create(flow)

        val session = hub.createFlow()
        val collections = mutableListOf<T>()
        testScope().launch { session.flow.collect(collections::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collections).containsExactly(emission1, emission2).inOrder()
      }

  @Test
  fun createWithAutomaticClosure_eachCall_providesNewInstance() =
      runBlocking<Unit> {
        val session = underlyingSession()

        val instance1 = subject().createWithAutomaticClosure(session)
        val instance2 = subject().createWithAutomaticClosure(session)

        assertThat(instance1).isNotSameInstanceAs(instance2)
      }

  @Test
  fun createWithAutomaticClosure_returnedHub_linkedToUnderlyingFlow() =
      runBlocking<Unit> {
        val session = underlyingSession()
        val hub = subject().createWithAutomaticClosure(session)

        val hubSession = hub.createFlow()
        val collections = mutableListOf<T>()
        testScope().launch { hubSession.flow.collect(collections::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstreamSession(emission1)
        emitUpstreamSession(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collections).containsExactly(emission1, emission2).inOrder()
      }

  @Test
  fun createWithAutomaticClosure_closeUnderlying_closesHub() =
      runBlocking<Unit> {
        val session = underlyingSession()
        val hub = subject().createWithAutomaticClosure(session)

        session.close()
        taskBarrier().awaitAllIdle()

        assertThat(hub.hasTerminalState.value).isTrue()
        assertThat(hub.hasTerminatedProcesses.value).isTrue()
      }

  @Test
  fun createWithAutomaticClosure_closeHub_doesNotCloseUnderlying() =
      runBlocking<Unit> {
        val session = underlyingSession()
        val hub = subject().createWithAutomaticClosure(session)

        hub.close()
        taskBarrier().awaitAllIdle()

        assertThat(session.hasTerminalState.value).isFalse()
        assertThat(session.hasTerminatedProcesses.value).isFalse()
      }

  protected abstract suspend fun subject(): SealedHub.Factory

  protected abstract suspend fun underlyingFlow(): Flow<T>

  protected abstract suspend fun underlyingSession(): SealedFlow<T>

  protected abstract suspend fun createValue(): T

  protected abstract suspend fun emitUpstream(value: T)

  protected abstract suspend fun emitUpstreamSession(value: T)

  protected abstract suspend fun testScope(): CoroutineScope

  protected abstract suspend fun taskBarrier(): TestingTaskBarrier
}
