package io.jackbradshaw.queen.sustainment.uniconverter.listenablefuture

import io.jackbradshaw.queen.sustainment.uniconverter.UniConverter
import io.jackbradshaw.queen.sustainment.operations.ListenableFutureOperation
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation

import com.google.common.util.concurrent.ListenableFuture
/**
 * Pass through converter for [ListenableFutureOperation]. The source is passed through to the
 * output without modification.
 */
class PassThroughUniConverter : UniConverter<Operation<ListenableFuture<Unit>>, Operation<ListenableFuture<Unit>>> {
  override fun convert(source: Operation<ListenableFuture<Unit>>): Operation<ListenableFuture<Unit>> = source
}
