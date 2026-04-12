package com.jackbradshaw.chronosphere.testingtaskdriver

import com.jackbradshaw.chronosphere.advancable.Advancable
import dagger.Component
import javax.inject.Scope
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestingTaskDriverImplTest : TestingTaskDriverTest() {

  private lateinit var underTest: TestingTaskDriver

  override fun setupSubject(advancables: List<Advancable>) {
    underTest =
        DaggerTestingTaskDriverImplTest_TestComponent.builder()
            .consuming(testingTaskDriverComponent())
            .build()
            .factory()
            .create(advancables.toSet())
  }

  override fun subject() = underTest

  @Scope annotation class TestScope

  @TestScope
  @Component(dependencies = [TestingTaskDriverComponent::class])
  interface TestComponent {
    fun factory(): TestingTaskDriver.Factory

    @Component.Builder
    interface Builder {
      fun consuming(taskDriver: TestingTaskDriverComponent): Builder

      fun build(): TestComponent
    }
  }
}
