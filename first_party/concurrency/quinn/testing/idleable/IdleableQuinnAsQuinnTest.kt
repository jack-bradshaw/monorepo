package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import com.jackbradshaw.concurrency.quinn.testing.DaggerTestingQuinnComponent
import com.jackbradshaw.chronosphere.testingtaskbarrier.testingTaskBarrierComponent
import com.jackbradshaw.concurrency.quinn.QuinnTest
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IdleableQuinnAsQuinnTest : QuinnTest<String>() {

  private lateinit var subject: IdleableQuinn<String>
  private lateinit var subjectLinkedDispatcher: CoroutineDispatcher
  private lateinit var subjectLinkedTaskBarrier: TestingTaskBarrier
  
  private lateinit var subjectIndependentDispatcher: CoroutineDispatcher
  private lateinit var subjectIndependentTaskBarrier: TestingTaskBarrier

  /** Counter to ensure resources produced by [createResource] are unique. */
  private var resourceCounter = java.util.concurrent.atomic.AtomicInteger(0)

  @Before
  fun setUp() {
    val taskBarrierComponent = testingTaskBarrierComponent()
    val coroutinesComponent = realisticCoroutinesTestingComponent(taskBarrierComponent)
    val taskBarrierComponent2 = testingTaskBarrierComponent()
    val coroutinesComponent2 = realisticCoroutinesTestingComponent(taskBarrierComponent2)
    val resourceManager = com.jackbradshaw.closet.resourcemanager.resourceManagerComponent(coroutinesComponent)

    val testComponent = DaggerTestingQuinnComponent.builder()
      .testingTaskBarrierComponent(taskBarrierComponent)
      .resourceManagerComponent(resourceManager)
      .build()

    subject = testComponent.idleableHub().createQuinn<String>() as IdleableQuinn<String>
    subjectLinkedDispatcher = coroutinesComponent.cpuDispatcher()
    subjectLinkedTaskBarrier = coroutinesComponent.taskBarrier()
    
    subjectIndependentDispatcher = coroutinesComponent2.cpuDispatcher()
    subjectIndependentTaskBarrier = coroutinesComponent2.taskBarrier()
  }

  override fun subject() = subject

  override fun subjectLinkedDispatcher() = subjectLinkedDispatcher

  override fun subjectLinkedTaskBarrier() = subjectLinkedTaskBarrier
  
  override fun subjectIndependentDispatcher() = subjectIndependentDispatcher

  override fun subjectIndependentTaskBarrier() = subjectIndependentTaskBarrier

  override fun createResource() = "TestResource_${resourceCounter.incrementAndGet()}"
}
