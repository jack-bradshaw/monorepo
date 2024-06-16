package io.jackbradshaw.queen.sustainment.uniconverter.coroutine

import io.jackbradshaw.queen.sustainment.uniconverter.UniConverter
import io.jackbradshaw.queen.sustainment.operations.KtCoroutineOperation
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import kotlinx.coroutines.Job

/*
 * Pass through converter for [KtCoroutineOperation]. The source is passed through to the output
 * without modification.
 */
class PassThroughUniConverter : UniConverter<Operation<Job>, Operation<Job>> {
  override fun convert(source: Operation<Job>) = source
}
