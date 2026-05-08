package com.jackbradshaw.sealant.pipe

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.jackbradshaw.sealant.pipe.Pipe


/** Abstract tests that all [Pipe] instances should pass. */
@RunWith(JUnit4::class)
import com.jackbradshaw.sealant.connectable.ConnectableTest

abstract class PipeTest<T, P : Pipe<T>> : ConnectableTest<P>() {

  @Test
  fun beforeClose_nothingAttached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> { assertThat(subject().isLocallyConnected.value).isFalse() }

  @Test
  fun beforeClose_transformerAttachedDirectly_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.filter { true } }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun beforeClose_transformerAttachedTransitively_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.map { it }.filter { true } }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun beforeClose_bufferAttachedDirectly_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.buffer() }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun beforeClose_bufferAttachedTransitively_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.map { it }.buffer() }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun beforeClose_consumerAttachedDirectly_isLocallyConnectedIsTrue() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.collect() }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isTrue()
      }

  @Test
  fun beforeClose_consumerAttachedTransitively_isLocallyConnectedIsTrue() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.map { it }.collect() }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isTrue()
      }

  @Test
  fun beforeClose_transformerAndBufferAttachedTransitively_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        val a = subject().flow.map { it }

        testScope().launch { a.filter { true } }
        testScope().launch { a.buffer() }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun beforeClose_transformerAndConsumerAttachedTransitively_isLocallyConnectedIsTrue() =
      runBlocking<Unit> {
        val a = subject().flow.map { it }

        testScope().launch { a.filter { true } }
        testScope().launch { a.collect() }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isTrue()
      }

  @Test
  fun beforeClose_bufferAndConsumerAttachedTransitively_isLocallyConnectedIsTrue() =
      runBlocking<Unit> {
        val a = subject().flow.map { it }

        testScope().launch { a.buffer() }
        testScope().launch { a.collect() }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isTrue()
      }

  @Test
  fun beforeClose_transformerAttachedThenDetached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        val job = testScope().launch { subject().flow.filter { true } }
        taskBarrier().awaitAllIdle()

        job.cancel()
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun beforeClose_bufferAttachedThenDetached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        val job = testScope().launch { subject().flow.buffer() }
        taskBarrier().awaitAllIdle()

        job.cancel()
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun beforeClose_consumerAttachedThenDetached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        val job = testScope().launch { subject().flow.collect() }
        taskBarrier().awaitAllIdle()

        job.cancel()
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun onClose_transformerAttachedBeforeCloseRemainsAttached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.filter { true } }
        taskBarrier().awaitAllIdle()

        subject().close()
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun onClose_bufferAttachedBeforeCloseRemainsAttached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.buffer() }
        taskBarrier().awaitAllIdle()

        subject().close()
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun onClose_consumerAttachedBeforeCloseRemainsAttached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.collect() }
        taskBarrier().awaitAllIdle()

        subject().close()
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun afterClose_transformerAttachedBeforeCloseThenDetached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        val job = testScope().launch { subject().flow.filter { true } }
        taskBarrier().awaitAllIdle()
        subject().close()
        taskBarrier().awaitAllIdle()

        job.cancel()
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun afterClose_bufferAttachedBeforeCloseThenDetached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        val job = testScope().launch { subject().flow.buffer() }
        taskBarrier().awaitAllIdle()
        subject().close()
        taskBarrier().awaitAllIdle()

        job.cancel()
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun afterClose_consumerAttachedBeforeCloseThenDetached_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        val job = testScope().launch { subject().flow.collect() }
        taskBarrier().awaitAllIdle()
        subject().close()
        taskBarrier().awaitAllIdle()

        job.cancel()
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun afterClose_transformerAttachedAfterClose_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        subject().close()
        taskBarrier().awaitAllIdle()

        testScope().launch { subject().flow.filter { true } }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun afterClose_bufferAttachedAfterClose_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        subject().close()
        taskBarrier().awaitAllIdle()

        testScope().launch { subject().flow.buffer() }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun afterClose_consumerAttachedAfterClose_isLocallyConnectedIsFalse() =
      runBlocking<Unit> {
        subject().close()
        taskBarrier().awaitAllIdle()

        testScope().launch { subject().flow.collect() }
        taskBarrier().awaitAllIdle()

        assertThat(subject().isLocallyConnected.value).isFalse()
      }

  @Test
  fun beforeClose_awaitConnection_suspendsBeforeConnection() =
      runBlocking<Unit> {
        var resumed = false
        val job =
            testScope().launch {
              subject().awaitTransitivelyConnected()
              resumed = true
            }
        taskBarrier().awaitAllIdle()
        assertThat(resumed).isFalse()
      }

  @Test
  fun beforeClose_awaitConnection_resumesOnConnected() =
      runBlocking<Unit> {
        var resumed = false
        val job =
            testScope().launch {
              subject().awaitTransitivelyConnected()
              resumed = true
            }
        taskBarrier().awaitAllIdle()

        testScope().launch { subject().flow.collect() }
        taskBarrier().awaitAllIdle()

        assertThat(resumed).isTrue()
      }

  @Test
  fun beforeClose_flowCollectedWithoutPreviousCollection_doesNotFail() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.collect() }
        taskBarrier().awaitAllIdle()

        // If here, no failures occurred, test passed.
      }

  @Test
  fun beforeClose_flowCollectedWhileAlreadyCollected_fails() =
      runBlocking<Unit> {
        testScope().launch { subject().flow.collect() }
        taskBarrier().awaitAllIdle()

        var exception: Throwable? = null
        try {
          subject().flow.collect()
        } catch (e: Exception) {
          exception = e
        }

        assertThat(exception).isInstanceOf(IllegalStateException::class.java)
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo(
                "Pipe flows can only be collected by a single downstream consumer. They cannot be collected repeatedly, even if the previous collector has disconnected.")
      }

  @Test
  fun beforeClose_flowCollectedAfterPreviousCollectionEnds_fails() =
      runBlocking<Unit> {
        val job = testScope().launch { subject().flow.collect() }
        taskBarrier().awaitAllIdle()
        job.cancelAndJoin()
        taskBarrier().awaitAllIdle()

        var exception: Throwable? = null
        try {
          subject().flow.collect()
        } catch (e: Exception) {
          exception = e
        }

        assertThat(exception).isInstanceOf(IllegalStateException::class.java)
        assertThat(exception)
            .hasMessageThat()
            .isEqualTo(
                "Pipe flows can only be collected by a single downstream consumer. They cannot be collected repeatedly, even if the previous collector has disconnected.")
      }

  @Test
  fun singleUpstreamEmission_allDownstreamConnectablesReceiveIt() =
      runBlocking<Unit> {
        val collections = mutableListOf<T>()
        testScope().launch { subject().flow.collect(collections::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        emitUpstream(emission1)
        taskBarrier().awaitAllIdle()

        assertThat(collections).containsExactly(emission1)
      }

  @Test
  fun multipleUpstreamEmissions_allDownstreamConnectablesReceiveThemInOrder() =
      runBlocking<Unit> {
        val collections = mutableListOf<T>()
        testScope().launch { subject().flow.collect(collections::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collections).containsExactly(emission1, emission2).inOrder()
      }

  @Test
  fun beforeClose_upstreamEmits_noConsumers_doesNotFail() =
      runBlocking<Unit> {
        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        // If here, no failures occurred, test passed.
      }

  @Test
  fun beforeClose_upstreamEmits_lateConsumers_consumersDoNotReceiveValue() =
      runBlocking<Unit> {
        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        val collections = mutableListOf<T>()
        testScope().launch { subject().flow.collect(collections::add) }
        taskBarrier().awaitAllIdle()

        assertThat(collections).isEmpty()
      }

  @Test
  fun afterClose_upstreamEmits_withConsumers_consumersDoNotReceiveValue() =
      runBlocking<Unit> {
        val collections = mutableListOf<T>()
        testScope().launch { subject().flow.collect(collections::add) }
        taskBarrier().awaitAllIdle()
        subject().close()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collections).isEmpty()
      }

  @Test
  fun afterClose_upstreamEmits_noConsumers_doesNotFail() =
      runBlocking<Unit> {
        subject().close()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        // If here, no failures occurred, test passed.
      }

  @Test
  fun afterClose_upstreamEmits_lateConsumers_consumersDoNotReceiveValue() =
      runBlocking<Unit> {
        subject().close()
        val collections = mutableListOf<T>()
        testScope().launch { subject().flow.collect(collections::add) }
        taskBarrier().awaitAllIdle()

        val emission1 = createValue()
        val emission2 = createValue()
        emitUpstream(emission1)
        emitUpstream(emission2)
        taskBarrier().awaitAllIdle()

        assertThat(collections).isEmpty()
      }

  protected abstract fun subject(): P

  protected abstract fun testScope(): CoroutineScope

  protected abstract suspend fun emitUpstream(value: T)

  protected abstract suspend fun createValue(): T

  protected abstract suspend fun taskBarrier(): TestingTaskBarrier
}
