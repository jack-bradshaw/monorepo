package com.jackbradshaw.concurrency.pulsar.testing

import com.jackbradshaw.concurrency.ConcurrencyScope
import com.jackbradshaw.coroutines.testing.TestCoroutinesComponent
import com.jackbradshaw.coroutines.testing.testCoroutinesComponent
import dagger.Component
import kotlinx.coroutines.test.TestScope

class TestPulsarImplTest : TestPulsarTest() {

  private lateinit var subject: TestPulsar

  private lateinit var testScope: TestScope

  override fun setup() {
    val component = DaggerTestComponent.builder().consuming(testCoroutinesComponent()).build()

    subject = component.testPulsar()
    testScope = component.testScope()
  }

  override fun subject() = subject

  override fun testScope() = testScope
}

@ConcurrencyScope
@Component(dependencies = [TestCoroutinesComponent::class], modules = [TestPulsarModule::class])
interface TestComponent {
  fun testScope(): TestScope

  fun testPulsar(): TestPulsar

  @Component.Builder
  interface Builder {
    fun consuming(coroutines: TestCoroutinesComponent): Builder

    fun build(): TestComponent
  }
}
