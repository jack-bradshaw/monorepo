package com.jackbradshaw.chronosphere.testingtaskdriver

import com.jackbradshaw.chronosphere.advancable.Advancable
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Inject
import javax.inject.Scope
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestingTaskDriverImplTest : TestingTaskDriverTest() {

  @Inject internal lateinit var factory: TestingTaskDriver.Factory

  private var gatingComponents = emptyList<Advancable>()

  @Before
  fun setUp() {
    DaggerTestingTaskDriverImplTest_TestComponent.builder()
        .coroutines(realisticCoroutinesTestingComponent())
        .build()
        .inject(this)
  }

  override fun setupSubject(advancables: List<Advancable>) {
    this.gatingComponents = advancables
  }

  override fun subject(): TestingTaskDriver {
    return factory.create(gatingComponents.toSet())
  }

  @Scope annotation class TestScope

  @TestScope
  @Component(
      dependencies = [RealisticCoroutinesTestingComponent::class],
      modules = [TestingTaskDriverModule::class])
  interface TestComponent {
    fun inject(target: TestingTaskDriverImplTest)

    @Component.Builder
    interface Builder {
      fun coroutines(coroutines: RealisticCoroutinesTestingComponent): Builder

      fun build(): TestComponent
    }
  }
}
