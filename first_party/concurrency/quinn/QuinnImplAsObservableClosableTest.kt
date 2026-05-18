<=
package com.jackbradshaw.concurrency.quinn
>

import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Scope
import kotlinx.coroutines.runBlocking
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Test

@RunWith(JUnit4::class)
class QuinnImplAsObservableClosableTest : ObservableClosableTest<Quinn<String>>() {

  private val coroutines = realisticCoroutinesTestingComponent()

  private val underTest = runBlocking {
    DaggerQuinnImplAsObservableClosableTest_TestComponent.builder()
        .consuming(coroutines)
        .consuming(quinnComponent())
        .build()
        .factory()
        .createQuinn<String>()
  }

  override fun subject() = underTest

  override fun testDispatcher() = coroutines.ioDispatcher()

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
