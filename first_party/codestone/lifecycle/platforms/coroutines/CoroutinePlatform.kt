package com.jackbradshaw.codestone.lifecycle.platforms.coroutines

import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters.ForwardsUniConverter
import com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters.BackwardsUniConverter
import com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters.PassThroughUniConverter
import com.jackbradshaw.codestone.lifecycle.orchestrator.factory.Platform
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlin.reflect.typeOf
import kotlinx.coroutines.Job
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

/**
 * Constructs UniConverters for converting between Coroutine work and
 * StartStop work.
 */
class CoroutinePlatform @Inject constructor(
  private val conversionScope: CoroutineScope
) : Platform<Work<Job>> {

  override fun forwardsUniConverter() =
      Pair(Pair(typeOf<StartStop<*, *>>(), typeOf<Job>()), ForwardsUniConverter(conversionScope))

  override fun backwardsUniConverter() =
      Pair(Pair(typeOf<Job>(), typeOf<StartStop<*, *>>()), BackwardsUniConverter(conversionScope))

  override fun passThroughUniConverter() = Pair(Pair(typeOf<Job>(), typeOf<Job>()), PassThroughUniConverter())

  companion object {
    @JvmStatic fun create(scope: CoroutineScope) = CoroutinePlatform(scope)
  }
}
