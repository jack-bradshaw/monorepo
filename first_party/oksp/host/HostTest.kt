package com.jackbradshaw.oksp.host

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source as KaleSource
import com.jackbradshaw.kale.provider.ProviderRunner
import com.jackbradshaw.kale.provider.ProviderRunnerComponent
import com.jackbradshaw.kale.provider.providerRunnerComponent
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.model.Source
import javax.inject.Inject
import javax.inject.Scope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

abstract class HostTest {

  companion object {
    val TEST_FILE = Source("Generated", "kt", "com.test", "class Generated")
  }

  val coroutines = realisticCoroutinesTestingComponent()
  @Inject lateinit var runner: ProviderRunner
  @Inject @Coroutines lateinit var barrier: TestingTaskBarrier

  @Before
  fun setup() {

    DaggerHostTest_TestComponent.builder()
        .realisticCoroutinesTestingComponent(coroutines)
        .providerRunnerComponent(providerRunnerComponent())
        .build()
        .inject(this)
  }

  val standardErrorStream = java.io.ByteArrayOutputStream()

  /**
   * Checks that a normal execution lifecycle results in exactly one application creation and one
   * application teardown.
   */
  @Test
  fun e2e__no_errors__creates_processes_and_destroys_app() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val application = object : TestApplicationBase(testScope) {}
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Success::class.java)
        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  /**
   * Checks that an unhandled exception thrown natively during KSP processing causes the pipeline to
   * fail safely and gracefully guarantees application teardown.
   */
  @Test
  fun e2e__error__in_processing__direct_fail_throwable__destroys_app() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val expectedError = IllegalStateException("processing error")
        val application =
            object : TestApplicationBase(testScope) {
              override suspend fun onRoundStart(component: Application.KspComponent) {
                component.kspService().fail(expectedError)
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)
        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  @Test
  fun e2e__error__in_processing__direct_fail_string__destroys_app() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val expectedError = IllegalStateException("processing error")
        val application =
            object : TestApplicationBase(testScope) {
              override suspend fun onRoundStart(component: Application.KspComponent) {
                component.kspService().fail(expectedError.message ?: "error")
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)
        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  /**
   * Checks that errors thrown inside withContext are caught by the service and returned to the
   * caller cleanly.
   */
  @Test
  fun e2e__error__in_processing__withContext_throws__recovers_and_completes_normally() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val expectedError = IllegalStateException("processing error")
        val application =
            object : TestApplicationBase(testScope) {
              override suspend fun onRoundStart(component: Application.KspComponent) {
                try {
                  component.kspService().withContext { throw expectedError }
                } catch (e: Throwable) {}
                component.kspService().completeRound()
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Success::class.java)
        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  /**
   * Checks that an unhandled exception thrown during Application.onCreate is caught, passed to the
   * pipeline as a fatal KSP native error, and still guarantees application teardown.
   */
  @Test
  fun e2e__error__in_onCreate__invokes_pipeline_fail_and_destroys_app() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val expectedError = IllegalStateException("onCreate error")
        val application =
            object : TestApplicationBase(testScope) {
              override suspend fun doOnCreate(component: Application.KspComponent) {
                throw expectedError
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        // allowProcessing() isn't strictly needed as onCreate will fail the pipeline
        // immediately upon launch, causing process() to throw before any rounds start.

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)
        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  /**
   * Checks that an unhandled exception thrown during Application.onDestroy is safely caught and
   * published to the KSP logger, preventing KSP native interceptor deadlock.
   */
  @Test
  fun e2e__error__in_onDestroy__publishes_to_ksp_logger() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val expectedError = IllegalStateException("onDestroy error")
        val application =
            object : TestApplicationBase(testScope) {
              override suspend fun doOnDestroy() {
                throw expectedError
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)
        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)

        val failure = finalResult as Result.Failure
        assertThat(failure.logs.any { it.toString().contains("onDestroy error") }).isTrue()
      }

  /**
   * Checks the worst case scenario: processing fails natively, AND teardown fails with an
   * exception.
   */
  @Test
  fun e2e__error__in_processing_and_onDestroy__publishes_to_ksp_logger() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val expectedProcessingError = IllegalStateException("processing error")
        val expectedDestroyError = IllegalStateException("onDestroy error")
        val application =
            object : TestApplicationBase(testScope) {
              override suspend fun onRoundStart(component: Application.KspComponent) {
                component.kspService().fail(expectedProcessingError)
              }

              override suspend fun doOnDestroy() {
                throw expectedDestroyError
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)
        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)

        val failure = finalResult as Result.Failure
        assertThat(failure.logs.any { it.toString().contains("onDestroy error") }).isTrue()
      }

  /**
   * Checks that if the application does not explicitly call allowTermination(), the KSP engine will
   * finish processing rounds but the runner pipeline will remain actively suspended.
   */
  @Test
  fun midway__terminationNotAllowed__finishesRoundsAndWaitsIndefinitely() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val application =
            object : TestApplicationBase(testScope) {
              var finalRoundNotified = CompletableDeferred<Unit>()

              override suspend fun onFinalRound(component: Application.KspComponent) {
                finalRoundNotified.complete(Unit)
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()
        barrier.awaitAllIdle()

        assertThat(result.isActive).isTrue()
        assertThat(application.onDestroyCalls).isEqualTo(0)

        application.kspComponent?.kspService()?.allowTermination()
        result.cancelAndJoin()
      }

  @Test
  fun e2e__error__in_processing__abortProcessing__destroys_app() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val application =
            object : TestApplicationBase(testScope) {
              override suspend fun onRoundStart(component: Application.KspComponent) {
                component.kspService().abortProcessing()
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)

        val failure = finalResult as Result.Failure
        assertThat(failure.error).isInstanceOf(kotlinx.coroutines.CancellationException::class.java)
        assertThat(failure.error!!.message).isEqualTo("KSP Aborted")

        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  @Test
  fun e2e__error__in_processing__abortProcessing_after_file_generated__destroys_app() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val application =
            object : TestApplicationBase(testScope) {
              var round = 0

              override suspend fun onRoundStart(component: Application.KspComponent) {
                round++
                if (round == 1) {
                  component.kspService().publish(TEST_FILE, emptyList())
                  component.kspService().completeRound()
                } else {
                  component.kspService().abortProcessing()
                }
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)

        val failure = finalResult as Result.Failure
        assertThat(failure.error).isInstanceOf(kotlinx.coroutines.CancellationException::class.java)
        assertThat(failure.error!!.message).isEqualTo("KSP Aborted")
        assertThat(failure.artifacts.kotlinSources).hasSize(1)

        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  @Test
  fun e2e__error__in_processing__direct_fail_throwable_after_file_generated__destroys_app() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val expectedError = IllegalStateException("processing error")
        val application =
            object : TestApplicationBase(testScope) {
              var round = 0

              override suspend fun onRoundStart(component: Application.KspComponent) {
                round++
                if (round == 1) {
                  component.kspService().publish(TEST_FILE, emptyList())
                  component.kspService().completeRound()
                } else {
                  component.kspService().fail(expectedError)
                }
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)

        val failure = finalResult as Result.Failure
        assertThat(failure.error).isInstanceOf(IllegalStateException::class.java)
        assertThat(failure.error!!.message).isEqualTo("processing error")
        assertThat(failure.artifacts.kotlinSources).hasSize(1)

        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  @Test
  fun e2e__error__in_processing__direct_fail_string_after_file_generated__destroys_app() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val expectedError = IllegalStateException("processing error")
        val application =
            object : TestApplicationBase(testScope) {
              var round = 0

              override suspend fun onRoundStart(component: Application.KspComponent) {
                round++
                if (round == 1) {
                  component.kspService().publish(TEST_FILE, emptyList())
                  component.kspService().completeRound()
                } else {
                  component.kspService().fail(expectedError.message ?: "error")
                }
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)

        val failure = finalResult as Result.Failure
        assertThat(failure.artifacts.kotlinSources).hasSize(1)

        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  @Test
  fun e2e__error__in_onDestroy_after_file_generated__publishes_to_ksp_logger() =
      runBlocking<Unit> {
        val testScope = CoroutineScope(coroutines.ioDispatcher())
        val expectedError = IllegalStateException("onDestroy error")
        val application =
            object : TestApplicationBase(testScope) {
              var round = 0

              override suspend fun onRoundStart(component: Application.KspComponent) {
                round++
                if (round == 1) {
                  component.kspService().publish(TEST_FILE, emptyList())
                  component.kspService().completeRound()
                } else {
                  component.kspService().completeRound()
                }
              }

              override suspend fun onFinalRound(component: Application.KspComponent) {
                component.kspService().allowTermination()
              }

              override suspend fun doOnDestroy() {
                throw expectedError
              }
            }
        setupSubject(application)

        val result =
            async(Dispatchers.Default) {
              runner.runProvider(
                  subject(),
                  sources
                      .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                      .toSet())
            }

        awaitAppCreation(application)
        application.allowProcessing()

        barrier.awaitAllIdle()
        val finalResult = result.await()
        assertThat(finalResult).isInstanceOf(Result.Failure::class.java)

        val failure = finalResult as Result.Failure
        assertThat(failure.logs.any { it.toString().contains("onDestroy error") }).isTrue()
        assertThat(failure.artifacts.kotlinSources).hasSize(1)

        assertThat(application.onCreateCalls.value).isEqualTo(1)
        assertThat(application.onDestroyCalls).isEqualTo(1)
      }

  open val sources: Set<Source> = setOf()

  abstract fun setupSubject(application: Application)

  abstract fun subject(): Host

  open suspend fun awaitAppCreation(application: TestApplicationBase) {
    application.onCreateCalls.first { it > 0 }
  }

  abstract inner class TestApplicationBase(protected val scope: CoroutineScope) : Application {

    val onCreateCalls = MutableStateFlow(0)
    var onDestroyCalls = 0

    var kspComponent: Application.KspComponent? = null

    override suspend fun onCreate(component: Application.KspComponent) {
      kspComponent = component
      onCreateCalls.value++

      doOnCreate(component)

      scope.launch(coroutines.ioDispatcher()) {
        component.kspService().onEachRoundStart().onEach { onRoundStart(component) }.collect()
      }

      scope.launch(coroutines.ioDispatcher()) {
        component.kspService().onFinalRoundComplete().onEach { onFinalRound(component) }.collect()
      }
    }

    override suspend fun onDestroy() {
      onDestroyCalls++
      doOnDestroy()
    }

    fun allowProcessing() {
      scope.launch(coroutines.ioDispatcher()) { kspComponent?.kspService()?.allowProcessing() }
    }

    open suspend fun doOnCreate(component: Application.KspComponent) {}

    open suspend fun doOnDestroy() {}

    open suspend fun onRoundStart(component: Application.KspComponent) {
      component.kspService().completeRound()
    }

    open suspend fun onFinalRound(component: Application.KspComponent) {
      component.kspService().allowTermination()
    }
  }

  @Scope annotation class TestScope

  @TestScope
  @dagger.Component(
      dependencies =
          [
              RealisticCoroutinesTestingComponent::class,
              ProviderRunnerComponent::class,
          ])
  interface TestComponent {
    fun inject(test: HostTest)
  }
}
