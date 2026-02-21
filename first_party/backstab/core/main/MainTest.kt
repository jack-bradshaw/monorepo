package com.jackbradshaw.backstab.core.main

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.model.SourceHeader
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Test

/** Abstract test that all instances of [Main] should pass. */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class MainTest {

  @Test
  fun start_calledOnce_doesNotFail() {
    subject().start()
  }

  @Test
  fun start_calledTwice_throwsIllegalStateException() {
    val main = subject()
    main.start()
    val exception = assertThrows(IllegalStateException::class.java) { main.start() }
    assertThat(exception.message).isEqualTo("Main is already started, cannot start again.")
  }

  @Test
  fun stop_calledOnceBeforeStart_doesNotFail() {
    subject().stop()
  }

  @Test
  fun stop_calledOnceAfterStart_doesNotFail() {
    val main = subject()
    main.start()
    main.stop()
  }

  @Test
  fun stop_calledRepeatedlyBeforeStart_doesNotFail() {
    val main = subject()
    main.stop()
    main.stop()
    main.stop()
  }

  @Test
  fun stop_calledRepeatedlyAfterStart_doesNotFail() {
    val main = subject()
    main.start()
    main.stop()
    main.stop()
    main.stop()
  }

  @Test
  fun process_successfulMigration_publishesNewSources() {
    runBlocking {
      val target = createTarget("TestTarget")
      val expectedModule = createModule("TestTarget_BackstabModule")

      subject().start()
      publishTarget(target)
      awaitIdle()

      val modules = getPublishedModules(target)
      assertThat(modules).containsExactly(expectedModule)
    }
  }

  /**
   * Verifies that generator exceptions are caught and published as errors instead of failing the
   * host process. This is critical for preventing dangerous side effects in a compilation
   * environment, such as cache corruption, deadlocks, or unexplained failures.
   */
  @Test
  fun process_generatorError_publishesError() {
    runBlocking {
      val target = createTarget("TestTarget")
      val expectedException = RuntimeException("Test Error")

      injectGeneratorError(target, expectedException)
      subject().start()
      publishTarget(target)
      awaitIdle()

      val error = getPublishedError(target)
      assertThat(error).isEqualTo(expectedException)
    }
  }

  /**
   * Gets the subject under test.
   *
   * Must return the same object on each call (within a single test run).
   */
  protected abstract fun subject(): Main

  /** Publishes a [target] to the sources observed by the [subject]. */
  protected abstract suspend fun publishTarget(target: BackstabTarget)

  /** Injects an error into the generator for the given [target]. */
  protected abstract fun injectGeneratorError(target: BackstabTarget, throwable: Throwable)

  /** Gets the published modules for the given [target], or null if none have been published. */
  protected abstract fun getPublishedModules(target: BackstabTarget): List<BackstabModule>?

  /** Gets the published error for the given [target], or null if none has been published. */
  protected abstract fun getPublishedError(target: BackstabTarget): Throwable?

  /** Awaits all pending asynchronous tasks initiated by the [subject] or the test. */
  protected abstract suspend fun awaitIdle()

  /** Creates a [BackstabTarget] for use in tests. */
  protected fun createTarget(name: String) =
      BackstabTarget(
          header = SourceHeader("com.example", name, "kt"),
          component = BackstabTarget.Component("com.example", listOf(name)),
          instantiator = BackstabTarget.ComponentInstantiator.CreateFunction)

  /** Creates a [BackstabModule] for use in tests. */
  protected fun createModule(name: String) =
      BackstabModule(
          header = SourceHeader("com.example", name, "kt"), contents = "package com.example")
}

// DO NOT SUBMIT test needs careful review
