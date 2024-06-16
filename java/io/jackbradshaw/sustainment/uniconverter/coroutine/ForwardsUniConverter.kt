package io.jackbradshaw.queen.sustainment.uniconverter.coroutine

import io.jackbradshaw.queen.infrastructure.coroutines.QueenInternalCoroutines
import io.jackbradshaw.queen.sustainment.uniconverter.UniConverter
import io.jackbradshaw.queen.sustainment.operations.KtCoroutineOperation
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.operations.StartStopOperation
import io.jackbradshaw.queen.sustainment.startstop.StartStop
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

/** Converts from a [StartStopOperation] to a [KtCoroutineOperation]. */
class ForwardsUniConverter : UniConverter<Operation<StartStop>, Operation<Job>> {
  override fun convert(source: Operation<StartStop>): Operation<Job> =
      object : KtCoroutineOperation() {
        override fun work() =
            QueenInternalCoroutines.forSustainment.launch {
              val sourceWork = source.work().also { it.start() }
              suspendCancellableCoroutine {
                it.invokeOnCancellation { sourceWork.stop() }
                sourceWork.onStop { it.cancel() }
              }
            }
      }
}