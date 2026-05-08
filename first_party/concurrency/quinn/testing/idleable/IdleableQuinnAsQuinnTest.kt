package com.jackbradshaw.concurrency.quinn.testing.idleable

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.QuinnTest
import com.jackbradshaw.concurrency.quinn.quinnComponent
import com.jackbradshaw.concurrency.quinn.testing.prod.prodPassThroughComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import java.util.concurrent.atomic.AtomicInteger
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/** Check that [IdleableQuinn] complies with the [Quinn] contract by passing [QuinnTest]. */
@RunWith(JUnit4::class)
class IdleableQuinnAsQuinnTest : QuinnTest<String>() {

  private lateinit var subject: IdleableQuinn<String>

  private lateinit var mainDispatcher: CoroutineDispatcher

  private lateinit var mainTaskBarrier: TestingTaskBarrier

  private lateinit var secondaryDispatcher: CoroutineDispatcher

  private lateinit var secondaryTaskBarrier: TestingTaskBarrier

  /** Counter to ensure resources produced by [createResource] are unique. */
  private var resourceCounter = AtomicInteger(0)

  @Before
  fun setUp() {
    val prodFactory = prodPassThroughComponent(quinnComponent()).prodQuinnFactory()
    subject = IdleableQuinnImpl(prodFactory.createQuinn<String>())

    val mainCoroutines = realisticCoroutinesTestingComponent()
    mainDispatcher = mainCoroutines.cpuDispatcher()
    mainTaskBarrier = mainCoroutines.taskBarrier()

    val secondaryCoroutines = realisticCoroutinesTestingComponent()
    secondaryDispatcher = secondaryCoroutines.cpuDispatcher()
    secondaryTaskBarrier = secondaryCoroutines.taskBarrier()
  }

  override fun subject() = subject

  override fun mainDispatcher() = mainDispatcher

  override fun mainTaskBarrier() = mainTaskBarrier

  override fun secondaryDispatcher() = secondaryDispatcher

  override fun secondaryTaskBarrier() = secondaryTaskBarrier

  override fun createResource() = "TestResource_${resourceCounter.incrementAndGet()}"
}
