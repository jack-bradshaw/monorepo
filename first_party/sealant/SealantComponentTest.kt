package com.jackbradshaw.sealant

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Tests that all [SealantComponent] instances should pass. */
@RunWith(JUnit4::class)
abstract class SealantComponentTest {

  /**
   * Verifies that closing the root hub propagates closure down the entire tree.
   *
   * Toplogy of the tree:
   * ```text
   *        [topOfPipe: MutableSharedFlow]
   *                     │
   *                     ▼
   *                [ hub1 ]
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
        val topOfPipe = MutableSharedFlow<String>()

        val hub1 = subject().sealedHubFactory().create(topOfPipe)
        val sess1_1 = hub1.createFlow()
        val sess1_2 = hub1.createFlow()
        val sess1_3 = hub1.createFlow()

        val hub2 = subject().sealedHubFactory().createWithAutomaticClosure(sess1_1)
        val sess2_1 = hub2.createFlow()
        val sess2_2 = hub2.createFlow()

        val hub3 = subject().sealedHubFactory().createWithAutomaticClosure(sess1_2)
        val sess3_1 = hub3.createFlow()

        hub1.close()
        barrier().awaitAllIdle()

        assertThat(hub1.hasTerminalState.value).isTrue()
        assertThat(sess1_1.hasTerminalState.value).isTrue()
        assertThat(sess1_2.hasTerminalState.value).isTrue()
        assertThat(sess1_3.hasTerminalState.value).isTrue()
        assertThat(hub2.hasTerminalState.value).isTrue()
        assertThat(sess2_1.hasTerminalState.value).isTrue()
        assertThat(sess2_2.hasTerminalState.value).isTrue()
        assertThat(hub3.hasTerminalState.value).isTrue()
        assertThat(sess3_1.hasTerminalState.value).isTrue()
      }

  /**
   * Verifies that closing the root hub propagates closure down the entire tree until an unlinked
   * node is found.
   *
   * ```text
   *  [topOfPipe]
   *       │
   *       ▼
   *   [ hub1 ] ────────────── (Root)
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
        val topOfPipe = MutableSharedFlow<String>()

        val hub1 = subject().sealedHubFactory().create(topOfPipe)
        val session1 = hub1.createFlow()

        val hub2 = subject().sealedHubFactory().createWithAutomaticClosure(session1)
        val session2 = hub2.createFlow()

        val hub3 = subject().sealedHubFactory().create(session2.flow)
        val session3 = hub3.createFlow()

        val hub4 = subject().sealedHubFactory().createWithAutomaticClosure(session3)

        hub1.close()
        barrier().awaitAllIdle()

        assertThat(hub1.hasTerminalState.value).isTrue()
        assertThat(hub2.hasTerminalState.value).isTrue()

        assertThat(hub3.hasTerminalState.value).isFalse()
        assertThat(hub4.hasTerminalState.value).isFalse()

        // Close Unlinked Root to prevent resource leaks.
        hub3.close()
      }

  /**
   * Builds the following tree and verifies that data fans out correctly to all active terminal
   * sessions.
   *
   * ```text
   *        [topOfPipe.emit("A", "B")]
   *                     │
   *                     ▼
   *                [ hub1 ]
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
        val topOfPipe = MutableSharedFlow<String>()

        val hub1 = subject().sealedHubFactory().create(topOfPipe)
        val sess1_1 = hub1.createFlow()
        val sess1_2 = hub1.createFlow()
        val sess1_3 = hub1.createFlow()

        val hub2 = subject().sealedHubFactory().createWithAutomaticClosure(sess1_1)
        val sess2_1 = hub2.createFlow()
        val sess2_2 = hub2.createFlow()

        val hub3 = subject().sealedHubFactory().createWithAutomaticClosure(sess1_2)
        val sess3_1 = hub3.createFlow()

        val out1 = mutableListOf<String>()
        val out2 = mutableListOf<String>()
        val out3 = mutableListOf<String>()
        val out4 = mutableListOf<String>()

        testScope().launch { sess1_3.flow.collect { out1.add(it) } }
        testScope().launch { sess3_1.flow.collect { out2.add(it) } }
        testScope().launch { sess2_1.flow.collect { out3.add(it) } }
        testScope().launch { sess2_2.flow.collect { out4.add(it) } }
        barrier().awaitAllIdle()

        topOfPipe.emit("A")
        topOfPipe.emit("B")
        barrier().awaitAllIdle()

        assertThat(out1).containsExactly("A", "B").inOrder()
        assertThat(out2).containsExactly("A", "B").inOrder()
        assertThat(out3).containsExactly("A", "B").inOrder()
        assertThat(out4).containsExactly("A", "B").inOrder()
      }

  protected abstract suspend fun subject(): SealantComponent

  protected abstract suspend fun barrier(): TestingTaskBarrier

  protected abstract suspend fun testScope(): CoroutineScope
}
