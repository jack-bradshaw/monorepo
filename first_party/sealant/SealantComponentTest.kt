package com.jackbradshaw.sealant

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

/**
 * Tests that all [SealantComponent] instances should pass.
 *
 * Effectively an integration test for the whole sealant system.
 */
@RunWith(JUnit4::class)
abstract class SealantComponentTest {

  private val testScopeHandle = Job()

  private val testScope: CoroutineScope by lazy {
    CoroutineScope(testScopeHandle + testDispatcher())
  }

  @After
  fun tearDown() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  /**
   * Verifies that closing the root hub propagates closure down the entire tree.
   *
   * Toplogy of the tree:
   * ```text
   *                [ source1 ]
   *                     │
   *        ┌────────────┼────────────┐
   *        ▼            ▼            ▼
   *    (sess1_1)    (sess1_2)    (sess1_3)
   *        │            │
   *        │            │
   *        │            ▼
   *        │         [ hub3 ]
   *        │            │
   *        ▼            ▼
   *     [ hub2 ]     (sess3_1)
   *        │
   *    ┌───┴───┐
   *    ▼       ▼
   * (sess2_1)(sess2_2)
   * ```
   */
  @Test
  fun deepAutomaticClosure() =
      runBlocking<Unit> {
        val source1 = subject().sealedSourceFactory().create<String>()
        val sess1_1 = source1.createSession()
        val sess1_2 = source1.createSession()
        val sess1_3 = source1.createSession()

        val hub2 = subject().sealedHubFactory().createWithAutomaticClosure(sess1_1)
        val sess2_1 = hub2.createSession()
        val sess2_2 = hub2.createSession()

        val hub3 = subject().sealedHubFactory().createWithAutomaticClosure(sess1_2)
        val sess3_1 = hub3.createSession()

        source1.close()
        barrier().awaitAllIdle()

        assertThat(source1.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(sess1_1.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(sess1_2.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(sess1_3.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(hub2.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(sess2_1.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(sess2_2.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(hub3.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(sess3_1.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
      }

  /**
   * Verifies that closing the root hub propagates closure down the entire tree until an unlinked
   * node is found.
   *
   * ```text
   *   [ source1 ] ────────────── (Root)
   *       │
   *  ( session1 )
   *       │
   *       │ (Automatic Closure Link)
   *       ▼
   *   [ hub2 ] ────────────── (Linked)
   *       │
   *  ( session2 )
   *       │
   * ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ ─ < Closure Isolation Gap >
   *       │
   *       │ (Data Only: session2.flow)
   *       ▼
   *   [ hub3 ] ────────────── (Unlinked Root)
   *       │
   *  ( session3 )
   *       │
   *       │ (Automatic Closure Link)
   *       ▼
   *   [ hub4 ] ────────────── (Linked to Unlinked Root)
   * ```
   */
  @Test
  fun partialAutomaticClosure() =
      runBlocking<Unit> {
        val source1 = subject().sealedSourceFactory().create<String>()
        val session1 = source1.createSession()

        val hub2 = subject().sealedHubFactory().createWithAutomaticClosure(session1)
        val session2 = hub2.createSession()

        val hub3 = subject().sealedHubFactory().create(session2.flow)
        val session3 = hub3.createSession()

        val hub4 = subject().sealedHubFactory().createWithAutomaticClosure(session3)

        source1.close()
        barrier().awaitAllIdle()

        assertThat(source1.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)
        assertThat(hub2.closureStatus.value).isNotEqualTo(ObservableClosable.Status.OPEN)

        assertThat(hub3.closureStatus.value).isEqualTo(ObservableClosable.Status.OPEN)
        assertThat(hub4.closureStatus.value).isEqualTo(ObservableClosable.Status.OPEN)

        // Close Unlinked Root to prevent resource leaks.
        hub3.close()
      }

  /**
   * Builds the following tree and verifies that data fans out correctly to all active terminal
   * sessions.
   *
   * ```text
   *                [ source1 ]
   *                     │
   *        ┌────────────┼────────────┐
   *        ▼            ▼            ▼
   *    (sess1_1)    (sess1_2)    (sess1_3)
   *        │            │            │
   *        │            │            ▼
   *        │            │         [ out1 ]
   *        │            │
   *        │            ▼
   *        │         [ hub3 ]
   *        │            │
   *        ▼            ▼
   *     [ hub2 ]     (sess3_1)
   *        │            │
   *    ┌───┴───┐        ▼
   *    ▼       ▼     [ out2 ]
   * (sess2_1)(sess2_2)
   *    │       │
   *    ▼       ▼
   * [ out3 ] [ out4 ]
   * ```
   */
  @Test
  fun dataPropagatesDeeply() =
      runBlocking<Unit> {
        val source1 = subject().sealedSourceFactory().create<String>()
        val sess1_1 = source1.createSession()
        val sess1_2 = source1.createSession()
        val sess1_3 = source1.createSession()

        val hub2 = subject().sealedHubFactory().createWithAutomaticClosure(sess1_1)
        val sess2_1 = hub2.createSession()
        val sess2_2 = hub2.createSession()

        val hub3 = subject().sealedHubFactory().createWithAutomaticClosure(sess1_2)
        val sess3_1 = hub3.createSession()

        val out1 = mutableListOf<String>()
        val out2 = mutableListOf<String>()
        val out3 = mutableListOf<String>()
        val out4 = mutableListOf<String>()

        testScope.launch { sess1_3.flow.collect { out1.add(it) } }
        testScope.launch { sess3_1.flow.collect { out2.add(it) } }
        testScope.launch { sess2_1.flow.collect { out3.add(it) } }
        testScope.launch { sess2_2.flow.collect { out4.add(it) } }
        barrier().awaitAllIdle()

        source1.emit("A")
        source1.emit("B")
        barrier().awaitAllIdle()

        assertThat(out1).containsExactly("A", "B").inOrder()
        assertThat(out2).containsExactly("A", "B").inOrder()
        assertThat(out3).containsExactly("A", "B").inOrder()
        assertThat(out4).containsExactly("A", "B").inOrder()
      }

  protected abstract suspend fun subject(): SealantComponent

  protected abstract suspend fun barrier(): TestingTaskBarrier

  protected abstract fun testDispatcher(): CoroutineDispatcher
}
