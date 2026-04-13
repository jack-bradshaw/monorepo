package com.jackbradshaw.codestone.lifecycle.orchestrator.factoring

import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.uniconverter.coroutine.ForwardsUniConverter
import com.jackbradshaw.codestone.lifecycle.uniconverter.coroutine.BackwardsUniConverter
import com.jackbradshaw.codestone.lifecycle.uniconverter.coroutine.PassThroughUniConverter
import com.jackbradshaw.codestone.lifecycle.orchestrator.factoring.Platform
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
      Pair(Pair(typeOf<Work<StartStop<*, *>>>(), typeOf<Work<Job>>()), ForwardsUniConverter(conversionScope))

  override fun backwardsUniConverter() =
      Pair(Pair(typeOf<Work<Job>>(), typeOf<Work<StartStop<*, *>>>()), BackwardsUniConverter(conversionScope))

  override fun passThroughUniConverter() = Pair(Pair(typeOf<Work<Job>>(), typeOf<Work<Job>>()), PassThroughUniConverter())

  companion object {
    @JvmStatic fun create(scope: CoroutineScope) = CoroutinePlatform(scope)
  }
}
