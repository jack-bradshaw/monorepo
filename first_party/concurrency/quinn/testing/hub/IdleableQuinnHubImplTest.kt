package com.jackbradshaw.concurrency.quinn.testing.hub

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrierComponent
import com.jackbradshaw.chronosphere.testingtaskbarrier.testingTaskBarrierComponent
import com.jackbradshaw.concurrency.quinn.QuinnScope
import com.jackbradshaw.concurrency.quinn.testing.prod.ProdPassThroughComponent
import com.jackbradshaw.concurrency.quinn.testing.prod.prodPassThroughComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IdleableQuinnHubImplTest : IdleableQuinnHubTest() {

  private val testScopeHandle = Job()

  private lateinit var testScope: CoroutineScope

  private lateinit var subject: IdleableQuinnHub

  private lateinit var taskBarrier: TestingTaskBarrier

  @Before
  fun setUp() {
    val coroutinesComponent = realisticCoroutinesTestingComponent()

    val testComponent =
        DaggerIdleableQuinnHubImplTest_TestComponent.builder()
            .testingTaskBarrierComponent(testingTaskBarrierComponent())
            .prodPassThroughComponent(prodPassThroughComponent())
            .build()

    subject = testComponent.hub()
    testScope = CoroutineScope(coroutinesComponent.cpuDispatcher() + testScopeHandle)
    taskBarrier = coroutinesComponent.taskBarrier()
  }

  @After
  fun tearDown() {
    runBlocking { testScopeHandle.cancelAndJoin() }
  }

  override fun subject(): IdleableQuinnHub = subject

  override fun testScope(): CoroutineScope = testScope

  override fun taskBarrier(): TestingTaskBarrier = taskBarrier

  @QuinnScope
  @Component(
      dependencies = [TestingTaskBarrierComponent::class, ProdPassThroughComponent::class],
      modules = [IdleableQuinnHubModule::class])
  internal interface TestComponent {
    fun hub(): IdleableQuinnHub
  }
}
