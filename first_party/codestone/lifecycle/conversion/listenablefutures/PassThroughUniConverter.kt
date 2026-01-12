package com.jackbradshaw.codestone.lifecycle.uniconverter.listenablefuture

import com.jackbradshaw.codestone.lifecycle.uniconverter.UniConverter
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.google.common.util.concurrent.ListenableFuture

/*
 * Pass through converter. The source is passed through to the output without modification.
 */
class PassThroughUniConverter : UniConverter<Work<ListenableFuture<Unit>>, Work<ListenableFuture<Unit>>> {
  override fun convert(input: Work<ListenableFuture<Unit>>): Work<ListenableFuture<Unit>> = input
}
