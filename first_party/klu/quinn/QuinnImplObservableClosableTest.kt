package com.jackbradshaw.quinn

import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import org.junit.After
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Scope

@RunWith(JUnit4::class)
class QuinnImplObservableClosableTest : ObservableClosableTest<Quinn<String>>() {

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

  private val component =
      DaggerQuinnImplObservableClosableTest_TestComponent.builder()
          .consuming(realisticCoroutinesTestingComponent())
          .consuming(DaggerQuinnComponentImpl.create())
          .build()

  private val underTest = component.factory().createQuinn<String>()

  @After
  override fun tearDown() {
    underTest.close()
  }

  override fun subject() = underTest
}
