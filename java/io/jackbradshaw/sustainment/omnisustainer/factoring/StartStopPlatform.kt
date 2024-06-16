package io.jackbradshaw.queen.sustainment.omnisustainer.factoring

import io.jackbradshaw.queen.sustainment.uniconverter.startstop.PassThroughUniConverter
import io.jackbradshaw.queen.sustainment.omnisustainer.factoring.Platform
import io.jackbradshaw.queen.sustainment.operations.StartStopOperation
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.startstop.StartStop
import kotlin.reflect.typeOf

/**
 * Constructs UniConverters for converting between [StartStopOperation] and
 * [StartStopOperation]. All converters are essentially just pass throughs.
 */
class StartStopPlatform : Platform<Operation<StartStop>> {
  
  override fun forwardsUniConverter() = passThroughUniConverter()

  override fun backwardsUniConverter() = passThroughUniConverter()

  override fun passThroughUniConverter() = Pair(Pair(typeOf<StartStop>(), typeOf<StartStop>()), PassThroughUniConverter())

  companion object {
    @JvmStatic fun create() = StartStopPlatform()
  }
}
