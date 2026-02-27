package com.jackbradshaw.backstab.core.main

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.oksp.model.SourceFile
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows
import org.junit.Test

/** Abstract test that all instances of [Main] should pass. */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class MainTest {

  @Test
  fun process_successfulMigration_publishesNewSources() {
    runBlocking {
      val target = createTarget("TestTarget")
      val expectedModule = createModule("TestTarget_BackstabModule")

      runSubject()
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
      runSubject()
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

  protected abstract suspend fun runSubject()

  /** Creates a [BackstabTarget] for use in tests. */
  protected fun createTarget(name: String) =
      BackstabTarget(
          header = SourceFile("com.example", name, "kt"),
          component = BackstabTarget.Component("com.example", listOf(name)),
          instantiator = BackstabTarget.ComponentInstantiator.CreateFunction)

  /** Creates a [BackstabModule] for use in tests. */
  protected fun createModule(name: String) =
      BackstabModule(
          sourceFile = SourceFile("com.example", name, "kt", "package com.example"))
}

// DO NOT SUBMIT test needs careful review
