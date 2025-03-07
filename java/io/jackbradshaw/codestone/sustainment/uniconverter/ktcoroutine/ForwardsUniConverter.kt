package io.jackbradshaw.codestone.sustainment.uniconverter.ktcoroutine

import io.jackbradshaw.codestone.infrastructure.coroutines.CodestoneInternalCoroutines
import io.jackbradshaw.codestone.sustainment.uniconverter.UniConverter
import io.jackbradshaw.codestone.sustainment.operations.KtCoroutineOperation
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.operations.StartStopOperation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

/** Converts from a [StartStopOperation] to a [KtCoroutineOperation]. */
class ForwardsUniConverter : UniConverter<Operation<StartStop>, Operation<Job>> {
  override fun convert(source: Operation<StartStop>): Operation<Job> =
      object : KtCoroutineOperation() {
        override fun work() =
            CodestoneInternalCoroutines.forSustainment.launch {
              val sourceWork = source.work().also { it.start() }
              suspendCancellableCoroutine {
                it.invokeOnCancellation { sourceWork.stop() }
                sourceWork.onStop { it.cancel() }
              }
            }
      }
}