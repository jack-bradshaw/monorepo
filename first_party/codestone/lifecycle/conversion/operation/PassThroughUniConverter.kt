package com.jackbradshaw.codestone.lifecycle.uniconverter.startstop

import com.jackbradshaw.codestone.lifecycle.uniconverter.UniConverter
import com.jackbradshaw.codestone.lifecycle.conversion.operation.StartStopOperation
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Work

/*
 * Pass through converter for [StartStopOperation]. The source is passed through to the output
 * without modification.
 */
class PassThroughUniConverter : UniConverter<Work<StartStop<*, *>>, Work<StartStop<*, *>>> {
  override fun convert(source: Work<StartStop<*, *>>): Work<StartStop<*, *>> = source
}
