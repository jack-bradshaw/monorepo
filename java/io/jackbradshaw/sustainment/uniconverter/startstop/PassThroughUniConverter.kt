package io.jackbradshaw.queen.sustainment.uniconverter.startstop

import io.jackbradshaw.queen.sustainment.uniconverter.UniConverter
import io.jackbradshaw.queen.sustainment.operations.StartStopOperation
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.startstop.StartStop

/*
 * Pass through converter for [StartStopOperation]. The source is passed through to the output
 * without modification.
 */
class PassThroughUniConverter : UniConverter<Operation<StartStop>, Operation<StartStop>> {
  override fun convert(source: Operation<StartStop>): Operation<StartStop> = source
}
