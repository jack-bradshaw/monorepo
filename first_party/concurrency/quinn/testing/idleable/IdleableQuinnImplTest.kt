package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.quinnComponent
import com.jackbradshaw.concurrency.quinn.testing.prod.prodPassThroughComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IdleableQuinnImplTest : IdleableQuinnTest() {

  private lateinit var subject: IdleableQuinn<String>

  private lateinit var scope1Dispatcher: CoroutineDispatcher

  private lateinit var scope1TaskBarrier: TestingTaskBarrier

  private lateinit var scope2Dispatcher: CoroutineDispatcher

  private lateinit var scope2TaskBarrier: TestingTaskBarrier

  @Before
  fun setUp() {
    runBlocking {
      val prodFactory = prodPassThroughComponent(quinnComponent()).prodQuinnFactory()
      subject = IdleableQuinnImpl(prodFactory.createQuinn<String>())
    }

    val scope1CoroutinesComponent = realisticCoroutinesTestingComponent()
    scope1Dispatcher = scope1CoroutinesComponent.cpuDispatcher()
    scope1TaskBarrier = scope1CoroutinesComponent.taskBarrier()

    val scope2CoroutinesComponent = realisticCoroutinesTestingComponent()
    scope2Dispatcher = scope2CoroutinesComponent.cpuDispatcher()
    scope2TaskBarrier = scope2CoroutinesComponent.taskBarrier()
  }

  override fun subject(): IdleableQuinn<String> = subject

  override fun scope1Dispatcher() = scope1Dispatcher

  override fun scope1TaskBarrier(): TestingTaskBarrier = scope1TaskBarrier

  override fun scope2Dispatcher() = scope2Dispatcher

  override fun scope2TaskBarrier(): TestingTaskBarrier = scope2TaskBarrier
}
