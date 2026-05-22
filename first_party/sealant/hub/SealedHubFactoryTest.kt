package com.jackbradshaw.sealant.hub

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.sealant.session.SealedSession
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Abstract tests that all [SealedHub.Factory] instances should pass. */
@RunWith(JUnit4::class)
abstract class SealedHubFactoryTest<T> {

  private val testScopeHandle = Job()

  protected val testScope by lazy { CoroutineScope(testDispatcher() + testScopeHandle) }

  @After
  fun cancelTestScope() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  @Test
  fun create_eachCall_providesNewInstance() =
      runBlocking<Unit> {
        val upstreamFlow = upstreamFlow()
        taskBarrier().awaitAllIdle()

        val instance1 = subject().create(upstreamFlow)
        val instance2 = subject().create(upstreamFlow)
        taskBarrier().awaitAllIdle()

        assertThat(instance1).isNotSameInstanceAs(instance2)
      }

  @Test
  fun create_returnedHub_forwardsUpstreamFlow() =
      runBlocking<Unit> {
        val flow = upstreamFlow()
        val hub = subject().create(flow)
        taskBarrier().awaitAllIdle()

        val session = hub.createSession()
        val collections = mutableListOf<T>()
        testScope.launch { session.flow.collect(collections::add) }
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
        val session1 = sealedSessionFactory().create<T, T>(upstreamFlow()) { it }
        val session2 = sealedSessionFactory().create<T, T>(upstreamFlow()) { it }
        taskBarrier().awaitAllIdle()

        val instance1 = subject().createWithAutomaticClosure(session1)
        val instance2 = subject().createWithAutomaticClosure(session2)
        taskBarrier().awaitAllIdle()

        assertThat(instance1).isNotSameInstanceAs(instance2)
      }

  @Test
  fun createWithAutomaticClosure_returnedHub_forwardsUpstreamFlow() =
      runBlocking<Unit> {
        val session = sealedSessionFactory().create<T, T>(upstreamFlow()) { it }
        val hub = subject().createWithAutomaticClosure(session)
        taskBarrier().awaitAllIdle()

        val hubSession = hub.createSession()
        val collections = mutableListOf<T>()
        testScope.launch { hubSession.flow.collect(collections::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collections).containsExactly(emission1, emission2).inOrder()
      }

  @Test
  fun createWithAutomaticClosure_upstreamFlowClosed_closesHub() =
      runBlocking<Unit> {
        val session = sealedSessionFactory().create<T, T>(upstreamFlow()) { it }
        val hub = subject().createWithAutomaticClosure(session)
        taskBarrier().awaitAllIdle()

        session.close()
        taskBarrier().awaitAllIdle()

        assertThat(hub.closureStatus.value).isEqualTo(ObservableClosable.Status.CLOSED)
      }

  /** The factory under test. The same instance must be returned on each call. */
  protected abstract suspend fun subject(): SealedHub.Factory

  /**
   * A flow that emits when [emitUpstream] is called. The same instance must be returned on each
   * call.
   */
  protected abstract suspend fun upstreamFlow(): Flow<T>

  /** A sealed session factory. The same instance must be returned on each call. */
  protected abstract suspend fun sealedSessionFactory(): SealedSession.Factory

  /** Creates a new, unique value. */
  protected abstract suspend fun createValue(): T

  /** Emits a value from [upstreamFlow]. */
  protected abstract suspend fun emitUpstream(value: T)

  /** A dispatcher linked to [taskBarrier]. */
  protected abstract fun testDispatcher(): CoroutineDispatcher

  /** A task barrier linked to [testDispatcher]. */
  protected abstract suspend fun taskBarrier(): TestingTaskBarrier
}
