package com.jackbradshaw.codestone.lifecycle.platforms.futures

import com.jackbradshaw.codestone.lifecycle.platforms.futures.converters.ForwardsUniConverter
import com.jackbradshaw.codestone.lifecycle.platforms.futures.converters.BackwardsUniConverter
import com.jackbradshaw.codestone.lifecycle.platforms.futures.converters.PassThroughUniConverter
import com.google.common.util.concurrent.ListenableFuture
import com.jackbradshaw.codestone.lifecycle.orchestrator.factory.Platform
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlin.reflect.typeOf
import java.util.concurrent.ExecutorService

/**
 * Constructs UniConverters for converting between ListenableFuture work and
 * StartStop work.
 */
class FuturePlatform(
    private val executor: ExecutorService 
) : Platform<Work<ListenableFuture<Unit>>> {
    
  override fun forwardsUniConverter() =
      Pair(Pair(typeOf<StartStop<*, *>>(), typeOf<ListenableFuture<Unit>>()), ForwardsUniConverter(executor))

  override fun backwardsUniConverter() =
      Pair(Pair(typeOf<ListenableFuture<Unit>>(), typeOf<StartStop<*, *>>()), BackwardsUniConverter(executor))

  override fun passThroughUniConverter() =
      Pair(Pair(typeOf<ListenableFuture<Unit>>(), typeOf<ListenableFuture<Unit>>()), PassThroughUniConverter())

  companion object {
    @JvmStatic fun create(executor: ExecutorService) = FuturePlatform(executor)
  }
}
