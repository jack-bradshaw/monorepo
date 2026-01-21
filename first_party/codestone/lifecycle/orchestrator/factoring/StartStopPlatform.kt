package com.jackbradshaw.codestone.lifecycle.orchestrator.factoring

import com.jackbradshaw.codestone.lifecycle.uniconverter.startstop.PassThroughUniConverter
import com.jackbradshaw.codestone.lifecycle.orchestrator.factoring.Platform
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlin.reflect.typeOf
import com.jackbradshaw.codestone.lifecycle.uniconverter.UniConverter

/**
 * Constructs UniConverters for converting between [StartStop] and
 * [StartStop]. All converters are essentially just pass throughs.
 */
class StartStopPlatform : Platform<Work<StartStop<*, *>>> {
  
  override fun forwardsUniConverter() = passThroughUniConverter()

  override fun backwardsUniConverter() = passThroughUniConverter()

  @Suppress("UNCHECKED_CAST")
  override fun passThroughUniConverter() = Pair(
      Pair(typeOf<Work<StartStop<*, *>>>(), typeOf<Work<StartStop<*, *>>>()), 
      PassThroughUniConverter() as UniConverter<Work<StartStop<*, *>>, Work<StartStop<*, *>>>
  )

  companion object {
    @JvmStatic fun create() = StartStopPlatform()
  }
}
