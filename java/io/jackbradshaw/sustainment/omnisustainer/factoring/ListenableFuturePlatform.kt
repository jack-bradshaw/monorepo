package io.jackbradshaw.queen.sustainment.omnisustainer.factoring

import io.jackbradshaw.queen.sustainment.uniconverter.listenablefuture.ForwardsUniConverter
import io.jackbradshaw.queen.sustainment.uniconverter.listenablefuture.BackwardsUniConverter
import io.jackbradshaw.queen.sustainment.uniconverter.listenablefuture.PassThroughUniConverter
import com.google.common.util.concurrent.ListenableFuture
import io.jackbradshaw.queen.sustainment.omnisustainer.factoring.Platform
import io.jackbradshaw.queen.sustainment.operations.ListenableFutureOperation
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.startstop.StartStop
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
