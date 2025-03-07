package io.jackbradshaw.codestone.sustainment.uniconverter.ktcoroutine

import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.infrastructure.coroutines.CodestoneInternalCoroutines
import io.jackbradshaw.codestone.sustainment.uniconverter.UniConverter
import io.jackbradshaw.codestone.sustainment.operations.KtCoroutineOperation
import io.jackbradshaw.codestone.sustainment.operations.StartStopOperation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import io.jackbradshaw.codestone.sustainment.startstop.StartStopSimplex
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/** Converts from [KtCoroutineOperation] to a [StartStopOperation]. */
class BackwardsUniConverter : UniConverter<Operation<Job>, Operation<StartStop>> {
  override fun convert(source: Operation<Job>): Operation<StartStop> =
      object : StartStopOperation() {
        override fun work() = StartStopSimplex().also { connect(source, it) }
      }

  private fun connect(source: Operation<Job>, outputWork: StartStop) {
    outputWork.onStart {
      CodestoneInternalCoroutines.forSustainment.launch {
        val sourceWork = source.work().also { it.invokeOnCompletion { outputWork.stop() } }
        outputWork.onStop { sourceWork.cancel() }
      }
    }
  }
}
