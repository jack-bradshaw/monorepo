package com.jackbradshaw.publicity.conformance.runner

import com.jackbradshaw.concurrency.testing.TestConcurrency
import com.jackbradshaw.concurrency.testing.testConcurrency
import com.jackbradshaw.coroutines.testing.TestCoroutines
import com.jackbradshaw.coroutines.testing.testCoroutines
import com.jackbradshaw.publicity.conformance.model.Workspace
import com.jackbradshaw.publicity.conformance.packagechecker.PackageCheckerImplModule
import com.jackbradshaw.publicity.conformance.workspacechecker.WorkspaceCheckerImplModule
import com.jackbradshaw.sasync.inbound.config.defaultConfig as defaultInboundConfig
import com.jackbradshaw.publicity.conformance.ConformanceScope
import com.jackbradshaw.sasync.inbound.inbound
import com.jackbradshaw.sasync.outbound.config.defaultConfig as defaultOutboundConfig
import com.jackbradshaw.sasync.outbound.outbound
import com.jackbradshaw.sasync.outbound.transport.OutboundTransport
import com.jackbradshaw.sasync.standard.Standard
import com.jackbradshaw.sasync.standard.error.StandardError
import com.jackbradshaw.sasync.standard.output.StandardOutput
import com.jackbradshaw.sasync.standard.standard
import dagger.BindsInstance
import dagger.Component
import java.io.ByteArrayOutputStream
import javax.inject.Inject
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
    val coroutines = testCoroutines()
    val concurrency = testConcurrency()

    val sasync =
        standard(
            inbound = inbound(defaultInboundConfig, coroutines, concurrency),
            outbound = outbound(defaultOutboundConfig, coroutines, concurrency),
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

@ConformanceScope
@Component(
    dependencies =
        [
            Standard::class,
            TestCoroutines::class,
            TestConcurrency::class,
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
    fun standardComponent(component: Standard): Builder

    fun coroutines(coroutinesComponent: TestCoroutines): Builder

    fun concurrency(concurrencyComponent: TestConcurrency): Builder

    @BindsInstance fun workspace(workspace: Workspace): Builder

    fun build(): TestCheckerComponent
  }
}
