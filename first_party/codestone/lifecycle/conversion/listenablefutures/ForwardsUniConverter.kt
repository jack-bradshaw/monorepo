package com.jackbradshaw.codestone.lifecycle.uniconverter.listenablefuture

import com.jackbradshaw.codestone.lifecycle.uniconverter.UniConverter
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.SettableFuture
import java.util.concurrent.Executor
import com.jackbradshaw.codestone.lifecycle.operations.listenableFutureWork

class ForwardsUniConverter(
  private val executor: Executor
) : UniConverter<Work<StartStop<*, *>>, Work<ListenableFuture<Unit>>> {

  override fun convert(source: Work<StartStop<*, *>>): Work<ListenableFuture<Unit>> {
    val startStop = source.handle
    val future = SettableFuture.create<Unit>()
    
    future.addListener(
        {
          if (future.isCancelled) {
             startStop.abort()
          }
        },
        executor
    )
    
    return listenableFutureWork(future)
  }
}
