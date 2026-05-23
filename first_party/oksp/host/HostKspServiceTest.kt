package com.jackbradshaw.oksp.host

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.chronosphere.testingtaskbarrier.testingTaskBarrierComponent
import com.jackbradshaw.concurrency.quinn.QuinnComponent
import com.jackbradshaw.concurrency.quinn.testing.DaggerTestingQuinnComponent
import com.jackbradshaw.closet.resourcemanager.set.resourceSetComponent
import com.jackbradshaw.coroutines.CoroutinesComponent
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.kale.model.Log
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source as KaleSource
import com.jackbradshaw.kale.provider.ProviderRunner
import com.jackbradshaw.kale.provider.ProviderRunnerComponent
import com.jackbradshaw.kale.provider.providerRunnerComponent
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.ApplicationAdapter
import com.jackbradshaw.oksp.application.ApplicationComponent
import com.jackbradshaw.oksp.application.passed.DaggerPassedApplicationComponent
import com.jackbradshaw.oksp.model.LogLevel
import com.jackbradshaw.oksp.model.Source
import com.jackbradshaw.oksp.service.KspService
import com.jackbradshaw.oksp.service.KspServiceTest
import dagger.Binds
import dagger.Component
import dagger.Module
import javax.inject.Inject
import javax.inject.Scope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class HostKspServiceTest : KspServiceTest() {

  @Inject @Coroutines lateinit var coroutinesTaskBarrier: TestingTaskBarrier

  @Inject @Io lateinit var testDispatcher: CoroutineDispatcher

  @Inject lateinit var providerRunner: ProviderRunner

  @Inject lateinit var host: Host

  private lateinit var combinedTaskBarrier: TestingTaskBarrier

  private lateinit var underTest: KspService

  private lateinit var kspRun: Job

  private var kspResult: Result? = null

  @After
  fun teardown() = runBlocking {
    if (::underTest.isInitialized) {
      underTest.allowTermination()
      underTest.abortProcessing()
    }
    if (::kspRun.isInitialized) kspRun.cancel()
  }

  override suspend fun setupSubject(sources: Set<Source>) {
    val extractedService = CompletableDeferred<KspService>()
    val app =
        object : ApplicationAdapter() {
          override suspend fun onCreate(component: Application.KspComponent) {
            extractedService.complete(component.kspService())
          }
        }

    val taskBarrier = testingTaskBarrierComponent()
    val coroutines = realisticCoroutinesTestingComponent(taskBarrier)
    val quinn = DaggerTestingQuinnComponent.builder()
        .testingTaskBarrierComponent(taskBarrier)
        .resourceSetComponent(com.jackbradshaw.closet.resourcemanager.set.resourceSetComponent(coroutines, com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent()))
        .standardObservableClosableComponent(com.jackbradshaw.closet.observable.standard.standardObservableClosableComponent())
        .build()

    DaggerHostKspServiceTest_TestComponent.builder()
        .coroutines(coroutines)
        .runner(providerRunnerComponent())
        .quinn(quinn)
        .applicationComponent(DaggerPassedApplicationComponent.builder().binding(app).build())
        .sealant(com.jackbradshaw.sealant.sealantComponent())
        .build()
        .inject(this)

    combinedTaskBarrier =
        testingTaskBarrierComponent()
            .testingTaskBarrierFactory()
            .create(setOf(coroutinesTaskBarrier, quinn.taskBarrier()))

    // Does not use injected corotuines becuase this is effectively a blocking job that never
    // suspends
    kspRun =
        CoroutineScope(Dispatchers.IO).launch {
          try {
            kspResult =
                providerRunner.runProvider(
                    host,
                    sources
                        .map { KaleSource(it.fileName, it.extension, it.packageName, it.contents) }
                        .toSet())
          } catch (e: Throwable) {
            e.printStackTrace()
          }
        }

    underTest = extractedService.await()
  }

  override fun subject(): KspService = underTest

  override suspend fun finishExtraneousProcessing() {
    kspRun.join()
  }

  override fun taskBarrier(): TestingTaskBarrier = combinedTaskBarrier

  override fun testDispatcher(): CoroutineDispatcher = testDispatcher

  override fun getLogs(): List<Pair<LogLevel?, String>> =
      kspResult?.logs?.map {
        when (it) {
          is Log.Info -> Pair(LogLevel.INFO, it.message)
          is Log.Warning -> Pair(LogLevel.WARNING, it.message)
          is Log.Error -> Pair(null, it.message)
          is Log.Unspecified -> Pair(null, it.message)
          is Log.Exception -> Pair(null, it.error.message ?: it.error.toString())
        }
      } ?: emptyList()

  override fun getGeneratedSources(): Set<Source> =
      kspResult?.artifacts?.let { artifacts ->
        (artifacts.kotlinSources + artifacts.javaSources)
            .map {
              Source(
                  fileName = it.fileName,
                  extension = it.extension,
                  packageName = it.packageName,
                  contents = it.contents)
            }
            .toSet()
      } ?: emptySet()

  override fun getError(): Throwable? {
    val error =
        kspResult?.logs?.filterIsInstance<Log.Exception>()?.firstOrNull()?.error
            ?: (kspResult as? Result.Failure)?.error
    if (error is java.util.concurrent.ExecutionException) {
      return error.cause
    }
    return error
  }

  override fun isSuccessful(): Boolean {
    if (kspResult !is Result.Success) {}
    return kspResult is Result.Success
  }

  @Scope annotation class TestScope

  @TestScope
  @Component(
      dependencies =
          [
              RealisticCoroutinesTestingComponent::class,
              ProviderRunnerComponent::class,
              QuinnComponent::class,
              ApplicationComponent::class,
              com.jackbradshaw.sealant.SealantComponent::class,
          ],
      modules = [TestComponent.InnerModule::class])
  interface TestComponent {
    fun inject(target: HostKspServiceTest)

    @Module
    interface InnerModule {
      @Binds fun bind(impl: HostImpl): Host

      @Binds fun bindCoroutines(impl: RealisticCoroutinesTestingComponent): CoroutinesComponent
    }

    @Component.Builder
    interface Builder {
      fun coroutines(component: RealisticCoroutinesTestingComponent): Builder

      fun runner(component: ProviderRunnerComponent): Builder

      fun quinn(component: QuinnComponent): Builder

      fun applicationComponent(component: ApplicationComponent): Builder
      fun sealant(component: com.jackbradshaw.sealant.SealantComponent): Builder

      fun build(): TestComponent
    }
  }
}
