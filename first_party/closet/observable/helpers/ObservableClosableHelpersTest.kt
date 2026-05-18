package com.jackbradshaw.closet.observable.helpers

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableComponent
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableFactory
import com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent
import com.jackbradshaw.closet.rule.autoCloseRuleComponent
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Inject
import kotlin.test.assertFailsWith
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ObservableClosableHelpersTest {

  @get:Rule val autoCloseRule = autoCloseRuleComponent().autoCloseRule()

  @Inject @Io lateinit var coroutineDispatcher: CoroutineDispatcher

  @Inject @Coroutines lateinit var testingTaskBarrier: TestingTaskBarrier

  @Inject lateinit var standardFactory: StandardObservableClosableFactory

  /**
   * Collected across tests to ensure gates can be released during tear down (to prevent deadlocks
   * and test timeouts).
   */
  private val closureGates = mutableListOf<CompletableDeferred<Unit>>()

  private val scopeHandle = Job()

  private val scope by lazy { CoroutineScope(coroutineDispatcher + scopeHandle) }

  @Before
  fun setup() {
    DaggerObservableClosableHelpersTest_TestComponent.builder()
        .realisticCoroutinesTestingComponent(realisticCoroutinesTestingComponent())
        .standardObservableClosableComponent(standardObservableClosableComponent())
        .build()
        .inject(this)
  }

  @After
  fun tearDown() {
    runBlocking {
      closureGates.forEach { it.complete(Unit) }
      scopeHandle.cancelAndJoin()
    }
  }

  @Test
  fun awaitClosed_calledOnceWhileOpen_suspends() =
      runBlocking<Unit> {
        val closable = NoOpClosable()

        val passedAwait = awaitClosedAsync(closable)

        assertThat(passedAwait.isCompleted).isFalse()
      }

  @Test
  fun awaitClosed_calledOnceWhileOpen_resumesOnClose() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        val passedAwait = awaitClosedAsync(closable)

        closeAndIdle(closable)

        assertThat(passedAwait.isCompleted).isTrue()
      }

  @Test
  fun awaitClosed_calledRepeatedlyWhileOpen_allSuspend() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        val passedAwaits = List(REPEAT_COUNT) { awaitClosedAsync(closable) }

        assertThat(passedAwaits.count { it.isCompleted }).isEqualTo(0)
      }

  @Test
  fun awaitClosed_calledRepeatedlyWhileOpen_allResumeOnClose() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        val passedAwaits = List(REPEAT_COUNT) { awaitClosedAsync(closable) }

        closeAndIdle(closable)

        assertThat(passedAwaits.count { it.isCompleted }).isEqualTo(REPEAT_COUNT)
      }

  @Test
  fun awaitClosed_calledOnceWhileClosing_suspends() =
      runBlocking<Unit> {
        val closable = PausableClosable()
        closeAndIdle(closable)
        val passedAwait = awaitClosedAsync(closable)

        assertThat(passedAwait.isCompleted).isFalse()
      }

  @Test
  fun awaitClosed_calledOnceWhileClosing_resumesOnClose() =
      runBlocking<Unit> {
        val closable = PausableClosable()
        closeAndIdle(closable)
        val passedAwait = awaitClosedAsync(closable)

        closable.resumeCloseAndIdle()

        assertThat(passedAwait.isCompleted).isTrue()
      }

  @Test
  fun awaitClosed_calledRepeatedlyWhileClosing_allSuspend() =
      runBlocking<Unit> {
        val closable = PausableClosable()
        closeAndIdle(closable)
        val passedAwaits = List(REPEAT_COUNT) { awaitClosedAsync(closable) }

        assertThat(passedAwaits.count { it.isCompleted }).isEqualTo(0)
      }

  @Test
  fun awaitClosed_calledRepeatedlyWhileClosing_allResumeOnClose() =
      runBlocking<Unit> {
        val closable = PausableClosable()
        closeAndIdle(closable)
        val passedAwaits = List(REPEAT_COUNT) { awaitClosedAsync(closable) }

        closable.resumeCloseAndIdle()

        assertThat(passedAwaits.count { it.isCompleted }).isEqualTo(REPEAT_COUNT)
      }

  @Test
  fun awaitClosed_calledOnceAfterClosed_resumesImmediately() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        closeAndIdle(closable)
        val passedAwait = awaitClosedAsync(closable)

        assertThat(passedAwait.isCompleted).isTrue()
      }

  @Test
  fun awaitClosed_calledRepeatedlyAfterClosed_allResumeImmediately() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        closeAndIdle(closable)
        val passedAwaits = List(REPEAT_COUNT) { awaitClosedAsync(closable) }

        assertThat(passedAwaits.count { it.isCompleted }).isEqualTo(REPEAT_COUNT)
      }

  @Test
  fun awaitClosing_calledOnceWhileOpen_suspends() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        val passedAwait = awaitClosingAsync(closable)

        assertThat(passedAwait.isCompleted).isFalse()
      }

  @Test
  fun awaitClosing_calledOnceWhileOpen_resumesOnClosing() =
      runBlocking<Unit> {
        val closable = PausableClosable()
        val passedAwait = awaitClosingAsync(closable)

        closeAndIdle(closable)

        assertThat(passedAwait.isCompleted).isTrue()
      }

  @Test
  fun awaitClosing_calledRepeatedlyWhileOpen_allSuspend() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        val passedAwaits = List(REPEAT_COUNT) { awaitClosingAsync(closable) }

        assertThat(passedAwaits.count { it.isCompleted }).isEqualTo(0)
      }

  @Test
  fun awaitClosing_calledRepeatedlyWhileOpen_allResumeOnClosing() =
      runBlocking<Unit> {
        val closable = PausableClosable()
        val passedAwaits = List(REPEAT_COUNT) { awaitClosingAsync(closable) }

        closeAndIdle(closable)

        assertThat(passedAwaits.count { it.isCompleted }).isEqualTo(REPEAT_COUNT)
      }

  @Test
  fun awaitClosing_calledOnceWhileClosing_resumesImmediately() =
      runBlocking<Unit> {
        val closable = PausableClosable()
        closeAndIdle(closable)
        val passedAwait = awaitClosingAsync(closable)

        assertThat(passedAwait.isCompleted).isTrue()
      }

  @Test
  fun awaitClosing_calledRepeatedlyWhileClosing_allResumeImmediately() =
      runBlocking<Unit> {
        val closable = PausableClosable()
        closeAndIdle(closable)
        val passedAwaits = List(REPEAT_COUNT) { awaitClosingAsync(closable) }

        assertThat(passedAwaits.count { it.isCompleted }).isEqualTo(REPEAT_COUNT)
      }

  @Test
  fun awaitClosing_calledOnceAfterClosed_resumesImmediately() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        closeAndIdle(closable)
        val passedAwait = awaitClosingAsync(closable)

        assertThat(passedAwait.isCompleted).isTrue()
      }

  @Test
  fun awaitClosing_calledRepeatedlyAfterClosed_allResumeImmediately() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        closeAndIdle(closable)
        val passedAwaits = List(REPEAT_COUNT) { awaitClosingAsync(closable) }

        assertThat(passedAwaits.count { it.isCompleted }).isEqualTo(REPEAT_COUNT)
      }

  @Test
  fun checkOpen_calledOnceWhileOpen_returnsWithoutError() =
      runBlocking<Unit> {
        val closable = NoOpClosable()

        closable.checkOpen()
      }

  @Test
  fun checkOpen_calledOnceWhileClosing_throws() =
      runBlocking<Unit> {
        val closable = PausableClosable()
        closeAndIdle(closable)

        val exception = assertFailsWith<IllegalStateException> { closable.checkOpen() }

        assertThat(exception).hasMessageThat().isEqualTo("This resource is not open.")
      }

  @Test
  fun checkOpen_calledOnceWhileClosed_throws() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        closeAndIdle(closable)

        val exception = assertFailsWith<IllegalStateException> { closable.checkOpen() }

        assertThat(exception).hasMessageThat().isEqualTo("This resource is not open.")
      }

  @Test
  fun checkOpen_calledOnceWithCustomMessage_throwsWithCustomMessage() =
      runBlocking<Unit> {
        val closable = NoOpClosable()
        closeAndIdle(closable)

        val exception = assertFailsWith<IllegalStateException> { closable.checkOpen("foo") }

        assertThat(exception).hasMessageThat().isEqualTo("foo")
      }

  /**
   * Launches an async task to monitor `awaitClosed` on [closable] and returns a [Deferred] that
   * completes when waiting completes.
   */
  private fun awaitClosedAsync(closable: ObservableClosable): Deferred<Unit> {
    val passedAwait = CompletableDeferred<Unit>()
    scope.launch {
      closable.awaitClosed()
      passedAwait.complete(Unit)
    }
    testingTaskBarrier.awaitAllIdle()
    return passedAwait
  }

  /**
   * Launches an async task to monitor `awaitClosing` on [closable] and returns a [Deferred] that
   * completes when waiting completes.
   */
  private fun awaitClosingAsync(closable: ObservableClosable): Deferred<Unit> {
    val passedAwait = CompletableDeferred<Unit>()
    scope.launch {
      closable.awaitClosing()
      passedAwait.complete(Unit)
    }
    testingTaskBarrier.awaitAllIdle()
    return passedAwait
  }

  private fun closeAndIdle(closable: ObservableClosable) {
    scope.launch { closable.close() }
    testingTaskBarrier.awaitAllIdle()
  }

  @Component(
      dependencies =
          [RealisticCoroutinesTestingComponent::class, StandardObservableClosableComponent::class])
  interface TestComponent {
    fun inject(target: ObservableClosableHelpersTest)
  }

  /** An [ObservableClosable] that does nothing during closure. */
  inner class NoOpClosable : ObservableClosable {

    private val standard = runBlocking { standardFactory.createStandardClosable() }

    init {
      autoCloseRule.register(this)
    }

    override val closureStatus = standard.closureStatus

    override suspend fun close() = standard.close()
  }

  /**
   * An [ObservableClosable] that suspends closure until [resumeCloseAndIdle] is called, but
   * otherwise does nothing during closure.
   */
  inner class PausableClosable : ObservableClosable {

    private val standard = runBlocking { standardFactory.createStandardClosable { gate.await() } }

    private val gate = CompletableDeferred<Unit>()

    init {
      autoCloseRule.register(this)
      closureGates.add(gate)
    }

    override val closureStatus = standard.closureStatus

    override suspend fun close() = standard.close()

    /** Unblocks close and waits for idle */
    fun resumeCloseAndIdle() {
      gate.complete(Unit)
      testingTaskBarrier.awaitAllIdle()
    }
  }

  companion object {
    /** Repetition count used in tests that require an action to be performed repeatedly. */
    private const val REPEAT_COUNT = 5
  }
}
