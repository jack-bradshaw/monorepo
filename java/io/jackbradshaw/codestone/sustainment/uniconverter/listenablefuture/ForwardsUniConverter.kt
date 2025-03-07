package io.jackbradshaw.codestone.sustainment.uniconverter.listenablefuture

import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.SettableFuture
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import com.google.common.util.concurrent.ListenableFuture
import io.jackbradshaw.codestone.infrastructure.executors.CodestoneInternalExecutors
import io.jackbradshaw.codestone.sustainment.uniconverter.UniConverter
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import io.jackbradshaw.codestone.sustainment.operations.ListenableFutureOperation
import io.jackbradshaw.codestone.sustainment.startstop.StartStopSimplex

/** Converts from a [Operation<StartStop>] to a [Operation>ListenableFuture>]. */
class ForwardsUniConverter : UniConverter<Operation<StartStop>, Operation<ListenableFuture<Unit>>> {
  override fun convert(source: Operation<StartStop>): Operation<ListenableFuture<Unit>> =
      object : ListenableFutureOperation() {
        override fun work() = SettableFuture.create<Unit>().also { connect(source, it) }
      }

  private fun connect(source: Operation<StartStop>, outputWork: SettableFuture<Unit>) {
    val sourceWork = source.work().also { it.start() }

    Futures.addCallback(
        outputWork,
        object : FutureCallback<Unit> {
          override fun onSuccess(unit: Unit) {
            sourceWork.stop()
          }

          override fun onFailure(t: Throwable) {
            if (outputWork.isCancelled()) sourceWork.stop() else throw t
          }
        },
        CodestoneInternalExecutors.forsustainment)
    sourceWork.onStop { outputWork.set(Unit) }
  }
}
