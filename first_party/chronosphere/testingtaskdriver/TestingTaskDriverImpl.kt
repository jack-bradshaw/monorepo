package com.jackbradshaw.chronosphere.testingtaskdriver

import com.jackbradshaw.chronosphere.advancable.Advancable
import javax.inject.Inject

class TestingTaskDriverImpl
internal constructor(private val gating: Set<@JvmSuppressWildcards Advancable>) :
    TestingTaskDriver {

  override fun advanceAllBy(millis: Int) {
    gating.forEach { it.advanceBy(millis) }
  }

  class Factory @Inject internal constructor() : TestingTaskDriver.Factory {
    override fun create(gating: Set<Advancable>): TestingTaskDriver {
      // Defensive copy
      return TestingTaskDriverImpl(gating.toSet())
    }
  }
}
