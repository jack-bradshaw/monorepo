package com.jackbradshaw.concurrency.quinn

import com.jackbradshaw.closet.observable.ObservableClosableTest
import dagger.Component
import javax.inject.Scope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class QuinnImplObservableClosableTest : ObservableClosableTest<Quinn<String>>() {

  private lateinit var underTest: Quinn<String>

  override fun testDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @Before
  fun setUp() = runBlocking {
    underTest =
        DaggerQuinnImplObservableClosableTest_TestComponent.builder()
            .consuming(quinnComponent())
            .build()
            .factory()
            .createQuinn<String>()
  }

  @After
  override fun tearDown() {
    runBlocking { super.tearDown() }
  }

  override fun subject() = underTest

  @Scope annotation class TestScope

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
