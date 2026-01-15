package com.jackbradshaw.concurrency.testing

import com.jackbradshaw.concurrency.ConcurrencyComponent
import com.jackbradshaw.concurrency.pulsar.testing.TestPulsar

interface TestConcurrencyComponent : ConcurrencyComponent {
  fun testPulsar(): TestPulsar
}
