package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.QuinnTest
import com.jackbradshaw.concurrency.quinn.testing.testingQuinnComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Check that [IdleableQuinn] complies with the [Quinn] contract by passing [QuinnTest]. */
@RunWith(JUnit4::class)
class IdleableQuinnAsQuinnTest : QuinnTest<String>() {

  private lateinit var subject: IdleableQuinn<String>

  private lateinit var scope1Dispatcher: CoroutineDispatcher

  private lateinit var scope1TaskBarrier: TestingTaskBarrier

  private lateinit var scope2Dispatcher: CoroutineDispatcher

  private lateinit var scope2TaskBarrier: TestingTaskBarrier

  /** Counter to ensure resources produced by [createResource] are unique. */
  private var resourceCounter = AtomicInteger(0)

  @Before
  fun setUp() {
    runBlocking {
      val quinnFactory = testingQuinnComponent().factory()
      subject = IdleableQuinnImpl(quinnFactory.createQuinn<String>())
    }

    val scope1Coroutines = realisticCoroutinesTestingComponent()
    scope1Dispatcher = scope1Coroutines.cpuDispatcher()
    scope1TaskBarrier = scope1Coroutines.taskBarrier()

    val scope2Coroutines = realisticCoroutinesTestingComponent()
    scope2Dispatcher = scope2Coroutines.cpuDispatcher()
    scope2TaskBarrier = scope2Coroutines.taskBarrier()
  }

  override fun subject() = subject

  override fun scope1Dispatcher() = scope1Dispatcher

  override fun scope1TaskBarrier() = scope1TaskBarrier

  override fun scope2Dispatcher() = scope2Dispatcher

  override fun scope2TaskBarrier() = scope2TaskBarrier

  override fun createResource() = "TestResource_${resourceCounter.incrementAndGet()}"
}
