package com.jackbradshaw.sealant

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.closet.resourcemanager.ResourceManagerImplModule
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.sealant.hub.SealedHubModule
import dagger.Component
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SealantComponentImplTest : SealantComponentTest() {

  private val testScopeHandle = Job()

  private val testScope by lazy { CoroutineScope(ioDispatcher + testScopeHandle) }

  private lateinit var component: TestComponent

  @Inject @Io lateinit var ioDispatcher: CoroutineDispatcher

  @Inject @Coroutines lateinit var barrier: TestingTaskBarrier

  @Before
  fun setUp() {
    component =
        DaggerSealantComponentImplTest_TestComponent.builder()
            .realisticCoroutinesTestingComponent(realisticCoroutinesTestingComponent())
            .build()
    component.inject(this)
  }

  @After
  fun tearDown() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  override suspend fun subject(): SealantComponent = component

  override suspend fun barrier(): TestingTaskBarrier = barrier

  override suspend fun testScope(): CoroutineScope = testScope

  @SealantScope
  @Component(
      modules =
          [SealedHubModule::class, ResourceManagerImplModule::class],
      dependencies = [RealisticCoroutinesTestingComponent::class])
  interface TestComponent : SealantComponent {
    fun inject(target: SealantComponentImplTest)
  }
}
