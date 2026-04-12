package com.jackbradshaw.chronosphere.testingtaskdriver

/** Provides a factory for producing task drivers. */
interface TestingTaskDriverComponent {
  fun testingTaskDriverFactory(): TestingTaskDriver.Factory
}
