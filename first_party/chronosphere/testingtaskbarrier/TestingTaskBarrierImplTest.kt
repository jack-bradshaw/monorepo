package com.jackbradshaw.chronosphere.testingtaskbarrier

import com.jackbradshaw.chronosphere.idleable.Idleable
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestingTaskBarrierImplTest : TestingTaskBarrierTest() {

  @Inject internal lateinit var factory: TestingTaskBarrier.Factory

  private var gatingComponents = emptyList<Idleable>()

  @Before
  fun setUp() {
    DaggerTestingTaskBarrierImplTest_TestComponent.builder()
        .coroutines(realisticCoroutinesTestingComponent())
        .build()
        .inject(this)
  }

  override fun setupSubject(idleables: List<Idleable>) {
    this.gatingComponents = idleables
  }

  override fun subject(): TestingTaskBarrier {
    return factory.create(gatingComponents.toSet())
  }

  @Scope annotation class TestScope

  @TestScope
  @Component(
      dependencies = [RealisticCoroutinesTestingComponent::class],
      modules = [TestingTaskBarrierModule::class])
  interface TestComponent {
    fun inject(target: TestingTaskBarrierImplTest)

    @Component.Builder
    interface Builder {
      fun coroutines(coroutines: RealisticCoroutinesTestingComponent): Builder

      fun build(): TestComponent
    }
  }
}
