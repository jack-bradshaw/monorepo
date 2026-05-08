package com.jackbradshaw.sealant.hub

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.resourcemanager.ResourceManagerImplModule
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.sealant.SealantScope
import com.jackbradshaw.sealant.flow.SealedFlow
import dagger.Component
import jakarta.inject.Inject
import java.util.UUID
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SealedHubImplFactoryTest : SealedHubFactoryTest<String>() {

  private val testScopeHandle = Job()
  private val testScope by lazy { CoroutineScope(testScopeHandle + ioDispatcher) }

  @Inject lateinit var subject: SealedHub.Factory
  @Inject @Io lateinit var ioDispatcher: CoroutineDispatcher
  @Inject @Coroutines lateinit var taskBarrier: TestingTaskBarrier

  private val underlyingFlow = MutableSharedFlow<String>()
  private val underlyingSessionInternalFlow = MutableSharedFlow<String>()
  private lateinit var underlyingSession: SealedFlow<String>

  @Before
  fun setUp() =
      runBlocking<Unit> {
        DaggerSealedHubImplFactoryTest_TestComponent.builder()
            .realisticCoroutinesTestingComponent(realisticCoroutinesTestingComponent())
            .build()
            .inject(this@SealedHubImplFactoryTest)

        val sessionHub = subject.create(underlyingSessionInternalFlow)
        underlyingSession = sessionHub.createFlow()
      }

  @After
  fun tearDown() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  override suspend fun subject(): SealedHub.Factory = subject

  override suspend fun underlyingFlow(): Flow<String> = underlyingFlow

  override suspend fun underlyingSession(): SealedFlow<String> = underlyingSession

  override suspend fun createValue(): String = UUID.randomUUID().toString()

  override suspend fun emitUpstream(value: String) {
    underlyingFlow.emit(value)
  }

  override suspend fun emitUpstreamSession(value: String) {
    underlyingSessionInternalFlow.emit(value)
  }

  override suspend fun testScope(): CoroutineScope = testScope

  override suspend fun taskBarrier(): TestingTaskBarrier = taskBarrier

  @SealantScope
  @Component(
      modules =
          [SealedHubModule::class, ResourceManagerImplModule::class],
      dependencies = [RealisticCoroutinesTestingComponent::class])
  interface TestComponent {
    fun inject(target: SealedHubImplFactoryTest)
  }
}
