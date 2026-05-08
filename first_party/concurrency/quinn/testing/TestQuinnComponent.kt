package com.jackbradshaw.concurrency.quinn.testing

import com.jackbradshaw.chronosphere.testingtaskbarrier.TestingTaskBarrier
import com.jackbradshaw.concurrency.quinn.QuinnComponent
import com.jackbradshaw.concurrency.quinn.QuinnSpecific

/** The Quinn instances produced by this component are idleable and linked to the [taskBarrier]. */
interface TestQuinnComponent : QuinnComponent {
  @QuinnSpecific fun taskBarrier(): TestingTaskBarrier
}
