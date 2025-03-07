package io.jackbradshaw.codestone.sustainment.uniconverter.listenablefuture

import io.jackbradshaw.codestone.sustainment.uniconverter.UniConverter
import io.jackbradshaw.codestone.sustainment.operations.ListenableFutureOperation
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation

import com.google.common.util.concurrent.ListenableFuture
/**
 * Pass through converter for [ListenableFutureOperation]. The source is passed through to the
 * output without modification.
 */
class PassThroughUniConverter : UniConverter<Operation<ListenableFuture<Unit>>, Operation<ListenableFuture<Unit>>> {
  override fun convert(source: Operation<ListenableFuture<Unit>>): Operation<ListenableFuture<Unit>> = source
}
