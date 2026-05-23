with open("first_party/concurrency/quinn/QuinnImplObservableClosableTest.kt", "r") as f:
    content = f.read()

replacement = """package com.jackbradshaw.concurrency.quinn


import com.jackbradshaw.closet.observable.ObservableClosableTest
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component
import javax.inject.Scope
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@RunWith(JUnit4::class)
class QuinnImplAsObservableClosableTest : ObservableClosableTest<Quinn<String>>() {

  private lateinit var underTest: Quinn<String>

  override fun testDispatcher(): CoroutineDispatcher = Dispatchers.Default

  @Before
  fun setUp() = runBlocking {
    underTest = DaggerQuinnImplAsObservableClosableTest_TestComponent.builder()
        .consuming(quinnComponent())
        .build()
        .factory()
        .createQuinn<String>()
  }

  @After
  override fun tearDown() {
    runBlocking {
      super.tearDown()
    }
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
"""

with open("first_party/concurrency/quinn/QuinnImplObservableClosableTest.kt", "w") as f:
    f.write(replacement)

print("done")
