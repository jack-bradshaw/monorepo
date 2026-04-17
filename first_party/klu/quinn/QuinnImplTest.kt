package com.jackbradshaw.quinn

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.coroutines.testing.Coroutines
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Scope

@RunWith(JUnit4::class)
class QuinnImplTest : QuinnTest<String>() {

  private lateinit var underTest: Quinn<String>
  private lateinit var taskBarrier: TestingTaskBarrier
  private lateinit var cpuDispatcher: CoroutineDispatcher
  
  /** Counter to ensure resources produced by [createResource] are unique. */
  private var resourceCounter = java.util.concurrent.atomic.AtomicInteger(0)

  @Before
  fun setup() {
    val coroutines = realisticCoroutinesTestingComponent()
    val component =
        DaggerQuinnImplTest_TestComponent.builder()
            .consuming(coroutines)
            .consuming(DaggerQuinnComponentImpl.create())
            .build()

    underTest = component.factory().createQuinn()
    taskBarrier = component.taskBarrier()
    cpuDispatcher = component.cpuDispatcher()
  }

  override fun subject() = underTest
  
  override fun cpuDispatcher() = cpuDispatcher
  
  override fun taskBarrier() = taskBarrier

  override fun createResource() = "TestResource_${resourceCounter.incrementAndGet()}"

  @Scope annotation class TestScope

  @TestScope
  @Component(dependencies = [QuinnComponent::class, RealisticCoroutinesTestingComponent::class])
  interface TestComponent {
    fun factory(): Quinn.Factory

    @Coroutines fun taskBarrier(): TestingTaskBarrier
    
    @com.jackbradshaw.coroutines.Cpu fun cpuDispatcher(): CoroutineDispatcher

    @Component.Builder
    interface Builder {
      fun consuming(quinn: QuinnComponent): Builder
      fun consuming(coroutines: RealisticCoroutinesTestingComponent): Builder
      fun build(): TestComponent
    }
  }
}
