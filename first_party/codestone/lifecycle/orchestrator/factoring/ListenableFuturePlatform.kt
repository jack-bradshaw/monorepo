package com.jackbradshaw.codestone.lifecycle.orchestrator.factoring

import com.jackbradshaw.codestone.lifecycle.uniconverter.listenablefuture.ForwardsUniConverter
import com.jackbradshaw.codestone.lifecycle.uniconverter.listenablefuture.BackwardsUniConverter
import com.jackbradshaw.codestone.lifecycle.uniconverter.listenablefuture.PassThroughUniConverter
import com.google.common.util.concurrent.ListenableFuture
import com.jackbradshaw.codestone.lifecycle.orchestrator.factoring.Platform
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlin.reflect.typeOf
import java.util.concurrent.ExecutorService

/**
 * Constructs UniConverters for converting between ListenableFuture work and
 * StartStop work.
 */
class ListenableFuturePlatform(
    private val executor: ExecutorService 
) : Platform<Work<ListenableFuture<Unit>>> {
    
  override fun forwardsUniConverter() =
      Pair(Pair(typeOf<Work<StartStop<*, *>>>(), typeOf<Work<ListenableFuture<Unit>>>()), ForwardsUniConverter(executor))

  override fun backwardsUniConverter() =
      Pair(Pair(typeOf<Work<ListenableFuture<Unit>>>(), typeOf<Work<StartStop<*, *>>>()), BackwardsUniConverter(executor))

  override fun passThroughUniConverter() =
      Pair(Pair(typeOf<Work<ListenableFuture<Unit>>>(), typeOf<Work<ListenableFuture<Unit>>>()), PassThroughUniConverter())

  companion object {
    @JvmStatic fun create(executor: ExecutorService) = ListenableFuturePlatform(executor)
  }
}
