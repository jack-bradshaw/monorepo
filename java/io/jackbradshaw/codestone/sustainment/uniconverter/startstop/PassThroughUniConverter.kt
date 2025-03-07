package io.jackbradshaw.codestone.sustainment.uniconverter.startstop

import io.jackbradshaw.codestone.sustainment.uniconverter.UniConverter
import io.jackbradshaw.codestone.sustainment.operations.StartStopOperation
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop

/*
 * Pass through converter for [StartStopOperation]. The source is passed through to the output
 * without modification.
 */
class PassThroughUniConverter : UniConverter<Operation<StartStop>, Operation<StartStop>> {
  override fun convert(source: Operation<StartStop>): Operation<StartStop> = source
}
