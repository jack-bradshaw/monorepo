package com.jackbradshaw.sealant.hub

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.resourcemanager.ResourceManagerImplModule
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.sealant.SealantScope
import com.jackbradshaw.sealant.flow.SealedFlow
import com.jackbradshaw.sealant.flow.SealedFlowTest
import dagger.Component
import jakarta.inject.Inject
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Scope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Verifies taht all [SealedFlow] instances produced by [SealedHubImpl] confirm to
 * [SealedFlowTest]. */
@RunWith(JUnit4::class)
class SealedFlowFromHubTest : SealedFlowTest<String>() {

  private val testScopeHandle = Job()

  private val testScope by lazy { CoroutineScope(testScopeHandle + ioDispatcher) }

  private val valueCounter = AtomicInteger(0)

  private val underlyingFlow = MutableSharedFlow<String>()

  private lateinit var subject: SealedFlow<String>

  @Inject lateinit var sealedHubFactory: SealedHub.Factory

  @Inject @Coroutines lateinit var taskBarrier: TestingTaskBarrier

  @Inject @Io lateinit var ioDispatcher: CoroutineDispatcher

  @Before
  fun setUp() =
      runBlocking<Unit> {
        DaggerSealedFlowFromHubTest_TestComponent.builder()
            .realisticCoroutinesTestingComponent(realisticCoroutinesTestingComponent())
            .build()
            .inject(this@SealedFlowFromHubTest)

        val hub = sealedHubFactory.create(underlyingFlow)
        subject = hub.createFlow()
      }

  @After
  fun tearDown() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  protected override fun subject() = subject

  protected override suspend fun emitUpstream(value: String) {
    underlyingFlow.emit(value)
  }

  protected override suspend fun createValue() = "${valueCounter.getAndIncrement()}"

  protected override suspend fun taskBarrier() = taskBarrier

  protected override fun testScope(): CoroutineScope = testScope

  @SealantScope
  @Component(
      modules = [SealedHubModule::class, ResourceManagerImplModule::class],
      dependencies = [RealisticCoroutinesTestingComponent::class])
  interface TestComponent {
    fun inject(target: SealedFlowFromHubTest)
  }
}
