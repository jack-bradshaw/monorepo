package io.jackbradshaw.codestone.sustainment.operations

import io.jackbradshaw.codestone.sustainment.primitives.Sustainable
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import kotlin.reflect.typeOf

/** A [Sustainable.Operation] that uses a [StartStop] for the work. */
abstract class StartStopOperation : Operation<StartStop> {
  override val workType = WORK_TYPE

  companion object {
    // Caching the value in static memory avoids repeatedly making reflective calls.
    val WORK_TYPE = typeOf<StartStop>()
  }
}

/** Convenience function for building a new [StartStop] based [Operation]. */
fun startStopOperation(workBuilder: () -> StartStop) =
    object : StartStopOperation() {
      override fun work() = workBuilder()
    }

/** Convenience function for building a new [StartStop] based [Sustainable]. */
fun startStopSustainable(workBuilder: () -> StartStop) =
    object : Sustainable<Operation<StartStop>> {
      override val operation = startStopOperation(workBuilder)
    }
