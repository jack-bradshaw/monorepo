package com.jackbradshaw.publicity.conformance.runner

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.testing.TestConcurrencyComponent
import com.jackbradshaw.concurrency.testing.testConcurrencyComponent
import com.jackbradshaw.coroutines.Cpu
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
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
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/** Concrete test for [RunnerImpl]. */
class RunnerImplTest : RunnerTest() {

  @Inject @Coroutines lateinit var taskBarrier: TestingTaskBarrier
  @Inject @Cpu lateinit var cpuContext: CoroutineContext
  @Inject @StandardOutput lateinit var outputTransport: OutboundTransport
  @Inject @StandardError lateinit var errorTransport: OutboundTransport

  @Inject lateinit var runner: Runner

  override fun setupSubject(
      workspace: Workspace,
      out: ByteArrayOutputStream,
      err: ByteArrayOutputStream
  ) {
    val coroutines = realisticCoroutinesTestingComponent()
    val concurrency = testConcurrencyComponent()

    val sasync =
        standardComponent(
            inbound = inboundComponent(defaultInboundConfig, coroutines, concurrency),
            outbound = outboundComponent(defaultOutboundConfig, coroutines, concurrency),
            output = out,
            error = err)

    val component =
        DaggerTestCheckerComponent.builder()
            .consuming(sasync)
            .consuming(coroutines)
            .consuming(concurrency)
            .binding(workspace)
            .build()

    component.inject(this)
  }

  override fun subject() = runner

  override suspend fun awaitClosure() {
    val outputJob = CoroutineScope(cpuContext).launch { outputTransport.close() }
    val errorJob = CoroutineScope(cpuContext).launch { errorTransport.close() }
    taskBarrier.awaitAllIdle()
    outputJob.join()
    errorJob.join()
  }
}

@Singleton
@Component(
    dependencies =
        [
            StandardComponent::class,
            RealisticCoroutinesTestingComponent::class,
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
    fun consuming(component: StandardComponent): Builder

    fun consuming(coroutinesComponent: RealisticCoroutinesTestingComponent): Builder

    fun consuming(concurrencyComponent: TestConcurrencyComponent): Builder

    @BindsInstance fun binding(workspace: Workspace): Builder

    fun build(): TestCheckerComponent
  }
}
