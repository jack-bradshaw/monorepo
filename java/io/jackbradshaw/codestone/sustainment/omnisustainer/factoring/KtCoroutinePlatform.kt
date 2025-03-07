package io.jackbradshaw.codestone.sustainment.omnisustainer.factoring

import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.uniconverter.ktcoroutine.ForwardsUniConverter
import io.jackbradshaw.codestone.sustainment.uniconverter.ktcoroutine.BackwardsUniConverter
import io.jackbradshaw.codestone.sustainment.uniconverter.ktcoroutine.PassThroughUniConverter
import io.jackbradshaw.codestone.sustainment.omnisustainer.factoring.Platform
import io.jackbradshaw.codestone.sustainment.operations.KtCoroutineOperation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
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
