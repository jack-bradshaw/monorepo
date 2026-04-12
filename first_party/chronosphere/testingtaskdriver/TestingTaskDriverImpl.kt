package com.jackbradshaw.chronosphere.testingtaskdriver

import com.jackbradshaw.chronosphere.advancable.Advancable
import javax.inject.Inject

class TestingTaskDriverImpl
internal constructor(private val driving: Set<@JvmSuppressWildcards Advancable>) :
    TestingTaskDriver {

  override fun advanceAllBy(millis: Int) {
    driving.forEach { it.advanceBy(millis) }
  }

  override fun advanceBy(millis: Int) {
    advanceAllBy(millis)
  }

  class Factory @Inject internal constructor() : TestingTaskDriver.Factory {
    override fun create(driving: Set<Advancable>): TestingTaskDriver {
      return TestingTaskDriverImpl(driving.toSet())
    }
  }
}
