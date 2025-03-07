package io.jackbradshaw.codestone.sustainment.omnisustainer.factoring

import io.jackbradshaw.codestone.sustainment.uniconverter.listenablefuture.ForwardsUniConverter
import io.jackbradshaw.codestone.sustainment.uniconverter.listenablefuture.BackwardsUniConverter
import io.jackbradshaw.codestone.sustainment.uniconverter.listenablefuture.PassThroughUniConverter
import com.google.common.util.concurrent.ListenableFuture
import io.jackbradshaw.codestone.sustainment.omnisustainer.factoring.Platform
import io.jackbradshaw.codestone.sustainment.operations.ListenableFutureOperation
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import kotlin.reflect.typeOf

/**
 * Constructs UniConverters for converting between [ListenableFutureOperation] and
 * [StartStopOperation].
 */
class ListenableFuturePlatform : Platform<Operation<ListenableFuture<Unit>>> {
  override fun forwardsUniConverter() =
      Pair(Pair(typeOf<StartStop>(), typeOf<ListenableFuture<Unit>>()), ForwardsUniConverter())

  override fun backwardsUniConverter() =
      Pair(Pair(typeOf<ListenableFuture<Unit>>(), typeOf<StartStop>()), BackwardsUniConverter())

  override fun passThroughUniConverter() =
      Pair(Pair(typeOf<ListenableFuture<Unit>>(), typeOf<ListenableFuture<Unit>>()), PassThroughUniConverter())

  companion object {
    @JvmStatic fun create() = ListenableFuturePlatform()
  }
}
