package com.jackbradshaw.concurrency.quinn

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Scope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuinnImplTest : QuinnTest<String>() {

  private lateinit var underTest: Quinn<String>

  private lateinit var scope1TaskBarrier: TestingTaskBarrier

  private lateinit var scope1Dispatcher: CoroutineDispatcher

  private lateinit var scope2TaskBarrier: TestingTaskBarrier

  private lateinit var scope2Dispatcher: CoroutineDispatcher

  /** Counter to ensure resources produced by [createResource] are unique. */
  private var resourceCounter = AtomicInteger(0)

  @Before
  fun setup() {
    val component = DaggerQuinnImplTest_TestComponent.builder().consuming(quinnComponent()).build()
    runBlocking { underTest = component.factory().createQuinn() }

    val scope1Coroutines = realisticCoroutinesTestingComponent()
    scope1TaskBarrier = scope1Coroutines.taskBarrier()
    scope1Dispatcher = scope1Coroutines.cpuDispatcher()

    val scope2Coroutines = realisticCoroutinesTestingComponent()
    scope2TaskBarrier = scope2Coroutines.taskBarrier()
    scope2Dispatcher = scope2Coroutines.cpuDispatcher()
  }

  override fun subject() = underTest

  override fun scope1Dispatcher() = scope1Dispatcher

  override fun scope1TaskBarrier() = scope1TaskBarrier

  override fun scope2Dispatcher() = scope2Dispatcher

  override fun scope2TaskBarrier() = scope2TaskBarrier

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
