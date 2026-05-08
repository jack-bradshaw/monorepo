package com.jackbradshaw.concurrency.quinn

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Scope
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuinnImplTest : QuinnTest<String>() {

  private lateinit var underTest: Quinn<String>

  private lateinit var mainTaskBarrier: TestingTaskBarrier

  private lateinit var mainDispatcher: CoroutineDispatcher

  private lateinit var secondaryTaskBarrier: TestingTaskBarrier

  private lateinit var secondaryDispatcher: CoroutineDispatcher

  /** Counter to ensure resources produced by [createResource] are unique. */
  private var resourceCounter = AtomicInteger(0)

  @Before
  fun setup() {
    val component =
        DaggerQuinnImplTest_TestComponent.builder()
            .consuming(DaggerQuinnComponentImpl.create())
            .build()
    underTest = component.factory().createQuinn()

    val mainCoroutines = realisticCoroutinesTestingComponent()
    mainTaskBarrier = mainCoroutines.taskBarrier()
    mainDispatcher = mainCoroutines.cpuDispatcher()

    val secondaryCoroutines = realisticCoroutinesTestingComponent()
    secondaryTaskBarrier = secondaryCoroutines.taskBarrier()
    secondaryDispatcher = secondaryCoroutines.cpuDispatcher()
  }

  override fun subject() = underTest

  override fun mainDispatcher() = mainDispatcher

  override fun mainTaskBarrier() = mainTaskBarrier

  override fun secondaryDispatcher() = secondaryDispatcher

  override fun secondaryTaskBarrier() = secondaryTaskBarrier

  override fun createResource() = "TestResource_${resourceCounter.incrementAndGet()}"

  @Scope annotation class TestScope

  @TestScope
  @Component(dependencies = [QuinnComponent::class])
  interface TestComponent {
    fun factory(): Quinn.Factory

    @Component.Builder
    interface Builder {
      fun consuming(quinn: QuinnComponent): Builder

      fun build(): TestComponent
    }
  }
}
