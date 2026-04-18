package com.jackbradshaw.oksp.entrypoint

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.provider.ProviderRunner
import com.jackbradshaw.kale.provider.ProviderRunnerComponent
import com.jackbradshaw.kale.provider.providerRunnerComponent
import com.jackbradshaw.oksp.application.Application
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

abstract class EntryPointTest {

  val coroutines = realisticCoroutinesTestingComponent()
  @Inject lateinit var runner: ProviderRunner
  @Inject @Coroutines lateinit var barrier: TestingTaskBarrier

  @Before
  fun setup() {

    DaggerEntryPointTest_TestComponent.builder()
        .realisticCoroutinesTestingComponent(coroutines)
        .providerRunnerComponent(providerRunnerComponent())
        .build()
        .inject(this)
  }

  @Test
  fun run_launchesApplication_waitsForCompleteSignals() = runBlocking {
    val testScope = CoroutineScope(coroutines.ioDispatcher())
    val application = TestApplication(testScope)
    setupSubject(application)
    val entryPoint = subject()

    val runnerScope = CoroutineScope(coroutines.cpuDispatcher() + Job())
    val job = runnerScope.launch { runner.runProvider(entryPoint, sources) }

    awaitAppCreation(application)

    assertThat(application.onCreateCalls).isEqualTo(1)
    assertThat(application.onDestroyCalls).isEqualTo(0)

    application.performProcessing()
    job.join()
  }

  @Test
  fun run_afterCompleteSignal_finishesApplication() = runBlocking {
    val testScope = CoroutineScope(coroutines.ioDispatcher())
    val application = TestApplication(testScope)
    setupSubject(application)
    val entryPoint = subject()

    val runnerScope = CoroutineScope(coroutines.cpuDispatcher() + Job())
    val job = runnerScope.launch { runner.runProvider(entryPoint, sources) }

    awaitAppCreation(application)

    application.performProcessing()
    job.join()

    assertThat(application.onCreateCalls).isEqualTo(1)
    assertThat(application.onDestroyCalls).isEqualTo(1)
    job.cancel()
  }

  abstract fun setupSubject(application: Application)

  abstract fun subject(): EntryPoint

  open suspend fun awaitAppCreation(application: TestApplication) {
    kotlinx.coroutines.withTimeout(5000L) {
      while (application.onCreateCalls == 0) {
        kotlinx.coroutines.delay(10)
      }
    }
  }

  inner class TestApplication(private val scope: kotlinx.coroutines.CoroutineScope) : Application {

    val processingEnabled = MutableStateFlow<Boolean>(false)

    var onCreateCalls = 0
    var onDestroyCalls = 0

    override suspend fun onCreate(component: Application.ContextComponent) {
      onCreateCalls++

      scope.launch(coroutines.ioDispatcher()) {
        processingEnabled.filter { it }.first()
        component
            .processingService()
            .onRoundStart()
            .onEach { component.processingService().completeRound() }
            .collect()
      }
    }

    override suspend fun onDestroy() {
      onDestroyCalls++
    }

    fun performProcessing() {
      processingEnabled.value = true
    }
  }

  companion object {
    private val sources =
        setOf(
            com.jackbradshaw.kale.model.Source(
                packageName = "com.foo",
                fileName = "test",
                extension = "kt",
                contents = "class Test"))
  }

  @Scope @Retention(AnnotationRetention.RUNTIME) annotation class EntryPointTestScope

  @EntryPointTestScope
  @Component(
      dependencies = [RealisticCoroutinesTestingComponent::class, ProviderRunnerComponent::class])
  interface TestComponent {
    fun inject(test: EntryPointTest)
  }
}
