package com.jackbradshaw.codestone.lifecycle.uniconverter.coroutine

import com.jackbradshaw.codestone.lifecycle.uniconverter.UniConverter
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlinx.coroutines.Job

/*
 * Pass through converter for [CoroutineOperation]. The source is passed through to the output
 * without modification.
 */
class PassThroughUniConverter : UniConverter<Work<Job>, Work<Job>> {
  override fun convert(input: Work<Job>): Work<Job> = input
}
