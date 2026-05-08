package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.quinnComponent
import com.jackbradshaw.concurrency.quinn.testing.prod.prodPassThroughComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IdleableQuinnImplTest : IdleableQuinnTest() {

  private lateinit var subject: IdleableQuinn<String>

  private lateinit var testDispatcher: CoroutineDispatcher

  private lateinit var taskBarrier: TestingTaskBarrier

  @Before
  fun setUp() {
    val prodFactory = prodPassThroughComponent(quinnComponent()).prodQuinnFactory()
    subject = IdleableQuinnImpl(prodFactory.createQuinn<String>())

    val testCoroutinesComponent = realisticCoroutinesTestingComponent()
    testDispatcher = testCoroutinesComponent.cpuDispatcher()
    taskBarrier = testCoroutinesComponent.taskBarrier()
  }

  override fun subject(): IdleableQuinn<String> = subject

  override fun testDispatcher() = testDispatcher

  override fun taskBarrier(): TestingTaskBarrier = taskBarrier
}
