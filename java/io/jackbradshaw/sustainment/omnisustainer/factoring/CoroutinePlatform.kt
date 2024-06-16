package io.jackbradshaw.queen.sustainment.omnisustainer.factoring

import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.uniconverter.coroutine.ForwardsUniConverter
import io.jackbradshaw.queen.sustainment.uniconverter.coroutine.BackwardsUniConverter
import io.jackbradshaw.queen.sustainment.uniconverter.coroutine.PassThroughUniConverter
import io.jackbradshaw.queen.sustainment.omnisustainer.factoring.Platform
import io.jackbradshaw.queen.sustainment.operations.KtCoroutineOperation
import io.jackbradshaw.queen.sustainment.startstop.StartStop
import kotlin.reflect.typeOf
import kotlinx.coroutines.Job

/**
 * Constructs UniConverters for converting between [KtCoroutineOperation] and
 * [StartStopOperation].
 */
class KtCoroutinePlatform : Platform<Operation<Job>> {

  override fun forwardsUniConverter() =
      Pair(Pair(typeOf<StartStop>(), typeOf<Job>()), ForwardsUniConverter())

  override fun backwardsUniConverter() =
      Pair(Pair(typeOf<Job>(), typeOf<StartStop>()), BackwardsUniConverter())

  override fun passThroughUniConverter() = Pair(Pair(typeOf<Job>(), typeOf<Job>()), PassThroughUniConverter())

  companion object {
    @JvmStatic fun create() = KtCoroutinePlatform()
  }
}
