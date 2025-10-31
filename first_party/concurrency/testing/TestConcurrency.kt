package com.jackbradshaw.concurrency.testing

import com.jackbradshaw.concurrency.Concurrency
import com.jackbradshaw.concurrency.ConcurrencyScope
import com.jackbradshaw.concurrency.pulsar.testing.TestPulsar
import com.jackbradshaw.concurrency.pulsar.testing.TestPulsarModule
import dagger.Component

/** Provides test doubles for concurrency utilities. */
@ConcurrencyScope
@Component(modules = [TestPulsarModule::class])
interface TestConcurrency : Concurrency {
  fun testPulsar(): TestPulsar
}

/** Creates an instance of [TestConcurrency]. */
fun testConcurrency() = DaggerTestConcurrency.create()
