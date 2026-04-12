package com.jackbradshaw.chronosphere.testingtaskbarrier

/** Provides a factory for producing task barriers. */
interface TestingTaskBarrierComponent {
  fun testingTaskBarrierFactory(): TestingTaskBarrier.Factory
}
