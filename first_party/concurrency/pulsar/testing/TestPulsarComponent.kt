package com.jackbradshaw.concurrency.pulsar.testing

import com.jackbradshaw.concurrency.pulsar.PulsarComponent

interface TestPulsarComponent : PulsarComponent {
  fun testPulsar(): TestPulsar
}
