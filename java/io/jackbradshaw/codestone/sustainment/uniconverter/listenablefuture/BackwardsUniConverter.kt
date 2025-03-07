package io.jackbradshaw.codestone.sustainment.uniconverter.listenablefuture

import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import io.jackbradshaw.codestone.infrastructure.executors.CodestoneInternalExecutors
import io.jackbradshaw.codestone.sustainment.uniconverter.UniConverter
import io.jackbradshaw.codestone.sustainment.operations.ListenableFutureOperation
import io.jackbradshaw.codestone.sustainment.operations.StartStopOperation
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import io.jackbradshaw.codestone.sustainment.startstop.StartStopSimplex

class BackwardsUniConverter : UniConverter<Operation<ListenableFuture<Unit>>, Operation<StartStop>> {
  override fun convert(source: Operation<ListenableFuture<Unit>>): Operation<StartStop> =
      object : StartStopOperation() {
        override fun work() = StartStopSimplex().also { connect(source, it) }
      }

  private fun connect(source: Operation<ListenableFuture<Unit>>, outputWork: StartStop) {
    lateinit var sourceWork: ListenableFuture<Unit>
    outputWork.onStart {
      sourceWork = source.work()
      Futures.addCallback(
          sourceWork,
          object : FutureCallback<Unit> {
            override fun onSuccess(unit: Unit) {
              outputWork.stop()
            }

            override fun onFailure(t: Throwable) {
              if (sourceWork.isCancelled()) outputWork.stop() else throw t
            }
          },
          CodestoneInternalExecutors.forsustainment)
    }

    outputWork.onStop { sourceWork.cancel(true) }
  }
}
