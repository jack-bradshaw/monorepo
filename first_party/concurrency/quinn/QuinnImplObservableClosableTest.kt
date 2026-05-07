
package com.jackbradshaw.concurrency.quinn


import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Scope
import org.junit.After
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuinnImplAsObservableClosableTest : ObservableClosableTest<Quinn<String>>() {

  private val underTest =
      DaggerQuinnImplAsObservableClosableTest_TestComponent.builder()
          .consuming(realisticCoroutinesTestingComponent())
          .consuming(DaggerQuinnComponentImpl.create())
          .build()
          .factory()
          .createQuinn<String>()

  @After
  override fun tearDown() {
    underTest.close()
  }

  override fun subject() = underTest

  @Scope annotation class TestScope

  @TestScope
  @Component(dependencies = [QuinnComponent::class, RealisticCoroutinesTestingComponent::class])
  interface TestComponent {
    fun factory(): Quinn.Factory

    @Component.Builder
    interface Builder {
      fun consuming(quinn: QuinnComponent): Builder

      fun consuming(coroutines: RealisticCoroutinesTestingComponent): Builder

      fun build(): TestComponent
    }
  }
}
