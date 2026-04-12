package com.jackbradshaw.concurrency.pulsar.testing

import com.jackbradshaw.concurrency.ConcurrencyScope
import com.jackbradshaw.coroutines.testing.realistic.RealisticCoroutinesTestingComponent
import com.jackbradshaw.coroutines.testing.realistic.realisticCoroutinesTestingComponent
import dagger.Component

class TestPulsarImplTest : TestPulsarTest() {

  private lateinit var subject: TestPulsar

  override fun setup() {
    val testComponent =
        DaggerTestComponent.builder().consuming(realisticCoroutinesTestingComponent()).build()
    testComponent.inject(this)

    subject = testComponent.testPulsar()
  }

  override fun subject() = subject
}

@ConcurrencyScope
@Component(
    dependencies = [RealisticCoroutinesTestingComponent::class],
    modules = [TestPulsarModule::class])
interface TestComponent {
  fun inject(target: TestPulsarImplTest)

  fun testPulsar(): TestPulsar

  @Component.Builder
  interface Builder {
    fun consuming(coroutines: RealisticCoroutinesTestingComponent): Builder

    fun build(): TestComponent
  }
}
