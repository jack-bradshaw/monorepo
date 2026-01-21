package com.jackbradshaw.codestone.lifecycle.uniconverter.listenablefuture

import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture
import com.jackbradshaw.codestone.lifecycle.uniconverter.UniConverter
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.startstop.StartStopImpl
import com.jackbradshaw.codestone.lifecycle.work.Work
import java.util.concurrent.ExecutorService

import com.jackbradshaw.codestone.lifecycle.conversion.operation.startStopWork

class BackwardsUniConverter(
  private val entanglementExecutor: ExecutorService
) : UniConverter<Work<ListenableFuture<Unit>>, Work<StartStop<*, *>>> {
  
  override fun convert(source: Work<ListenableFuture<Unit>>): Work<StartStop<*, *>> {
    val startStop = StartStopImpl<Unit, Throwable>()
    
    startStop.onStart {
      Futures.addCallback(
          source.handle,
          object : FutureCallback<Unit> {
            override fun onSuccess(unit: Unit) {
              startStop.stop()
            }

            override fun onFailure(t: Throwable) {
              if (source.handle.isCancelled) startStop.stop() else throw t
            }
          },
          entanglementExecutor)
    }

    startStop.onStop { 
      if (!source.handle.isDone) source.handle.cancel(/* mayInterruptIfRunning= */ true)
    }

    startStop.start()

    return startStopWork(startStop)
  }
}
