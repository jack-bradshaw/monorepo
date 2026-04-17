package com.jackbradshaw.oksp.entrypoint

import com.google.common.truth.Truth.assertThat
import com.jackbradshaw.coroutines.testing.DaggerTestCoroutines
import com.jackbradshaw.coroutines.testing.TestCoroutines
import com.jackbradshaw.coroutines.testing.launcher.Launcher
import com.jackbradshaw.kale.ksprunner.JvmSource
import com.jackbradshaw.kale.ksprunner.KspRunnerComponent
import com.jackbradshaw.kale.provider.ProviderChassis
import com.jackbradshaw.kale.provider.ProviderChassisComponent
import com.jackbradshaw.kale.provider.providerChassisComponent
import com.jackbradshaw.oksp.application.Application
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import org.junit.Before
import org.junit.Test

abstract class EntryPointTest {

  @Inject lateinit var chassis: ProviderChassis
  @Inject lateinit var launcher: Launcher
  @Inject lateinit var testScope: TestScope

  @Before
  fun setup() {
    val coroutines = DaggerTestCoroutines.create()
    val compiler = kspRunnerComponent(coroutines)
    DaggerEntryPointTest_TestComponent.builder()
        .testCoroutines(coroutines)
        .kspRunnerComponent(compiler)
        .providerChassisComponent(providerChassisComponent(compiler))
        .build()
        .inject(this)
  }

  @Test
  fun run_launchesApplication_waitsForCompleteSignals() = runBlocking {
    val application = TestApplication()
    setupSubject(application)
    val entryPoint = subject()

    launcher.launchEagerly { chassis.run(sources, entryPoint) }

    awaitIdle()

    assertThat(application.onCreateCalls).isEqualTo(1)
    assertThat(application.onDestroyCalls).isEqualTo(0)
  }

  @Test
  fun run_afterCompleteSignal_finishesApplication() = runBlocking {
    val application = TestApplication()
    setupSubject(application)
    val entryPoint = subject()

    launcher.launchEagerly { chassis.run(sources, entryPoint) }

    awaitIdle()

    application.finish()

    assertThat(application.onCreateCalls).isEqualTo(1)
    assertThat(application.onDestroyCalls).isEqualTo(1)
  }

  abstract fun setupSubject(application: Application)

  abstract fun subject(): EntryPoint

  open fun awaitIdle() {
    testScope.advanceUntilIdle()
  }

  inner class TestApplication : Application {

    val processingEnabled = MutableStateFlow<Boolean>(false)

    var onCreateCalls = 0
    var onDestroyCalls = 0

    override suspend fun onCreate(component: Application.ContextComponent) {
      onCreateCalls++

      testScope.launch {
        processingEnabled.filter { it }.first()
        component
            .processingService()
            .observeRoundStartEvents()
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
            JvmSource(
                packageName = "com.foo",
                fileName = "test",
                extension = "kt",
                contents = "class Test"))
  }

  @Scope @Retention(AnnotationRetention.RUNTIME) annotation class EntryPointTestScope

  @EntryPointTestScope
  @Component(
      dependencies =
          [TestCoroutines::class, KspRunnerComponent::class, ProviderChassisComponent::class])
  interface TestComponent {
    fun inject(test: EntryPointTest)
  }
}
