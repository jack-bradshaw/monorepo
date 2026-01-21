package com.jackbradshaw.codestone.lifecycle.conversion.operation

import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlin.reflect.typeOf

fun startStopWork(handle: StartStop<*, *>) = object : Work<StartStop<*, *>> {
  override val handle = handle
  override val workType = StartStopOperation.WORK_TYPE
}

/** Convenience function for building a new [StartStop] based Worker. */
fun startStopWorker(handle: StartStop<*, *>) =
    object : Worker<Work<StartStop<*, *>>> {
      override val work = startStopWork(handle)
    }

fun startStopWorker(handleProvider: () -> StartStop<*, *>) =
    object : Worker<Work<StartStop<*, *>>> {
      override val work = startStopWork(handleProvider())
    }

object StartStopOperation {
  val WORK_TYPE = typeOf<StartStop<*, *>>()
}
