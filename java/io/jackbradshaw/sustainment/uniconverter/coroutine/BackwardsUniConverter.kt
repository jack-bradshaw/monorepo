package io.jackbradshaw.queen.sustainment.uniconverter.coroutine

import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.infrastructure.coroutines.QueenInternalCoroutines
import io.jackbradshaw.queen.sustainment.uniconverter.UniConverter
import io.jackbradshaw.queen.sustainment.operations.KtCoroutineOperation
import io.jackbradshaw.queen.sustainment.operations.StartStopOperation
import io.jackbradshaw.queen.sustainment.startstop.StartStop
import io.jackbradshaw.queen.sustainment.startstop.StartStopSimplex
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
      QueenInternalCoroutines.forSustainment.launch {
        val sourceWork = source.work().also { it.invokeOnCompletion { outputWork.stop() } }
        outputWork.onStop { sourceWork.cancel() }
      }
    }
  }
}
