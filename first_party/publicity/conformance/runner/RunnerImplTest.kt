package com.jackbradshaw.publicity.conformance.runner

import com.jackbradshaw.concurrency.testing.TestConcurrencyComponent
import com.jackbradshaw.concurrency.testing.testConcurrencyComponent
import com.jackbradshaw.coroutines.testing.TestCoroutinesComponent
import com.jackbradshaw.coroutines.testing.testCoroutinesComponent
import com.jackbradshaw.publicity.conformance.model.Workspace
import com.jackbradshaw.publicity.conformance.packagechecker.PackageCheckerImplModule
import com.jackbradshaw.publicity.conformance.workspacechecker.WorkspaceCheckerImplModule
import com.jackbradshaw.sasync.inbound.config.defaultConfig as defaultInboundConfig
import com.jackbradshaw.sasync.inbound.inboundComponent
import com.jackbradshaw.sasync.outbound.config.defaultConfig as defaultOutboundConfig
import com.jackbradshaw.sasync.outbound.outboundComponent
import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.standard.StandardComponent
import com.jackbradshaw.sasync.standard.error.StandardError
import com.jackbradshaw.sasync.standard.output.StandardOutput
import com.jackbradshaw.sasync.standard.standardComponent
import dagger.BindsInstance
import dagger.Component
import java.io.ByteArrayOutputStream
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestScope

/** Concrete test for [RunnerImpl]. */
class RunnerImplTest : RunnerTest() {

  @Inject lateinit var testScope: TestScope
  @Inject @StandardOutput lateinit var outputTransport: OutboundTransport
  @Inject @StandardError lateinit var errorTransport: OutboundTransport

  @Inject lateinit var runner: Runner

  override fun setupSubject(
      workspace: Workspace,
      out: ByteArrayOutputStream,
      err: ByteArrayOutputStream
  ) {
    val coroutines = testCoroutinesComponent()
    val concurrency = testConcurrencyComponent()

    val sasync =
        standardComponent(
            inbound = inboundComponent(defaultInboundConfig, coroutines, concurrency),
            outbound = outboundComponent(defaultOutboundConfig, coroutines, concurrency),
            output = out,
            error = err)

    val component =
        DaggerTestCheckerComponent.builder()
            .standardComponent(sasync)
            .coroutines(coroutines)
            .concurrency(concurrency)
            .workspace(workspace)
            .build()

    component.inject(this)
  }

  override fun subject() = runner

  override suspend fun awaitClosure() {
    val outputJob = testScope.launch { outputTransport.close() }
    val errorJob = testScope.launch { errorTransport.close() }
    testScope.testScheduler.advanceUntilIdle()
    outputJob.join()
    errorJob.join()
  }
}

@Singleton
@Component(
    dependencies =
        [
            StandardComponent::class,
            TestCoroutinesComponent::class,
            TestConcurrencyComponent::class,
        ],
    modules =
        [
            WorkspaceCheckerImplModule::class,
            PackageCheckerImplModule::class,
            RunnerImplModule::class,
        ])
internal interface TestCheckerComponent {
  fun inject(target: RunnerImplTest)

  @Component.Builder
  interface Builder {
    fun standardComponent(component: StandardComponent): Builder

    fun coroutines(coroutinesComponent: TestCoroutinesComponent): Builder

    fun concurrency(concurrencyComponent: TestConcurrencyComponent): Builder

    @BindsInstance fun workspace(workspace: Workspace): Builder

    fun build(): TestCheckerComponent
  }
}
