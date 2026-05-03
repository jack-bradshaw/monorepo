
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
import org.junit.Test
import com.jackbradshaw.coroutines.Cpu

@RunWith(JUnit4::class)
class QuinnImplTest : QuinnTest<String>() {

  private lateinit var underTest: Quinn<String>

  private lateinit var subjectLinkedTaskBarrier: TestingTaskBarrier
  private lateinit var subjectLinkedDispatcher: CoroutineDispatcher

  private lateinit var subjectIndependentTaskBarrier: TestingTaskBarrier
  private lateinit var subjectIndependentDispatcher: CoroutineDispatcher


  /** Counter to ensure resources produced by [createResource] are unique. */
  private var resourceCounter = AtomicInteger(0)

  @Before
  fun setup() {

    val coroutines = realisticCoroutinesTestingComponent()
    val coroutines2 = realisticCoroutinesTestingComponent()
    

    val component =
        DaggerQuinnImplTest_TestComponent.builder()
            .consuming(DaggerQuinnComponentImpl.create())
            .build()
    underTest = component.factory().createQuinn()

    subjectLinkedTaskBarrier = component.taskBarrier()
    subjectLinkedDispatcher = component.cpuDispatcher()
    subjectIndependentTaskBarrier = coroutines2.taskBarrier()
    subjectIndependentDispatcher = coroutines2.cpuDispatcher()

  }

  override fun subject() = underTest


  override fun subjectLinkedDispatcher() = subjectLinkedDispatcher

  override fun subjectLinkedTaskBarrier() = subjectLinkedTaskBarrier
  
  override fun subjectIndependentDispatcher() = subjectIndependentDispatcher
  
  override fun subjectIndependentTaskBarrier() = subjectIndependentTaskBarrier


  override fun createResource() = "TestResource_${resourceCounter.incrementAndGet()}"

  @Scope annotation class TestScope

  @TestScope
  @Component(dependencies = [QuinnComponent::class])
  interface TestComponent {
    fun factory(): Quinn.Factory


    @Coroutines fun taskBarrier(): TestingTaskBarrier

    @Cpu fun cpuDispatcher(): CoroutineDispatcher

    @Component.Builder
    interface Builder {
      fun consuming(quinn: QuinnComponent): Builder
      fun consuming(coroutines: RealisticCoroutinesTestingComponent): Builder

      fun build(): TestComponent
    }
  }
}
