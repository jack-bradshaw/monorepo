package com.jackbradshaw.sealant.hub

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.resourcemanager.ResourceManagerImplModule
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.sealant.SealantScope
import dagger.Component
import jakarta.inject.Inject
import java.util.UUID
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

@RunWith(JUnit4::class)
class SealedHubImplTest : SealedHubTest<String>() {

  private val testScopeHandle = Job()

  private val testScope by lazy { CoroutineScope(testScopeHandle + ioDispatcher) }

  private val underlyingFlow = MutableSharedFlow<String>()

  private lateinit var subject: SealedHub<String>

  @Inject lateinit var sealedHubFactory: SealedHub.Factory

  @Inject @Io lateinit var ioDispatcher: CoroutineDispatcher

  @Inject @Coroutines lateinit var taskBarrier: TestingTaskBarrier

  @Before
  fun setUp() =
      runBlocking<Unit> {
        DaggerSealedHubImplTest_TestComponent.builder()
            .realisticCoroutinesTestingComponent(realisticCoroutinesTestingComponent())
            .build()
            .inject(this@SealedHubImplTest)

        subject = sealedHubFactory.create(underlyingFlow)
      }

  @After
  fun tearDown() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  override suspend fun subject(): SealedHub<String> = subject

  override suspend fun createValue(): String = UUID.randomUUID().toString()

  override suspend fun emitUpstream(value: String) {
    underlyingFlow.emit(value)
  }

  override suspend fun testScope(): CoroutineScope = testScope

  override suspend fun taskBarrier(): TestingTaskBarrier = taskBarrier

  @SealantScope
  @Component(
      modules =
          [SealedHubModule::class, ResourceManagerImplModule::class],
      dependencies = [RealisticCoroutinesTestingComponent::class])
  interface TestComponent {
    fun inject(target: SealedHubImplTest)
  }
}
