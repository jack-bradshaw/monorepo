package com.jackbradshaw.codestone.lifecycle.platforms.startstop

import com.jackbradshaw.codestone.lifecycle.platforms.startstop.converters.PassThroughUniConverter
import com.jackbradshaw.codestone.lifecycle.orchestrator.factory.Platform
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlin.reflect.typeOf
import com.jackbradshaw.codestone.lifecycle.conversion.uniconverter.UniConverter

/**
 * Constructs UniConverters for converting between [StartStop] and
 * [StartStop]. All converters are essentially just pass throughs.
 */
class StartStopPlatform : Platform<Work<StartStop<*, *>>> {
  
  override fun forwardsUniConverter() = passThroughUniConverter()

  override fun backwardsUniConverter() = passThroughUniConverter()

  @Suppress("UNCHECKED_CAST")
  override fun passThroughUniConverter() = Pair(
      Pair(typeOf<StartStop<*, *>>(), typeOf<StartStop<*, *>>()), 
      PassThroughUniConverter() as UniConverter<Work<StartStop<*, *>>, Work<StartStop<*, *>>>
  )

  companion object {
    @JvmStatic fun create() = StartStopPlatform()
  }
}
