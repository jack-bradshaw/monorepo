package com.jackbradshaw.concurrency.testing

import com.jackbradshaw.concurrency.ConcurrencyComponent
import com.jackbradshaw.concurrency.ConcurrencyScope
import com.jackbradshaw.concurrency.pulsar.testing.TestPulsar
import com.jackbradshaw.concurrency.pulsar.testing.TestPulsarModule
import dagger.Component

/** Provides test doubles for concurrency utilities. */
@ConcurrencyScope
@Component(modules = [TestPulsarModule::class])
interface TestConcurrencyComponent : ConcurrencyComponent {
  fun testPulsar(): TestPulsar

  @Component.Builder
  interface Builder {
    fun build(): TestConcurrencyComponent
  }
}

/** Creates an instance of [TestConcurrencyComponent]. */
fun testConcurrencyComponent(): TestConcurrencyComponent =
    DaggerTestConcurrencyComponent.builder().build()
