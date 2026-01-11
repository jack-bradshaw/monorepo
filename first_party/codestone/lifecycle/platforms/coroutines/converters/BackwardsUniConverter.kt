package com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters

import kotlinx.coroutines.CoroutineScope
import com.jackbradshaw.codestone.lifecycle.conversion.uniconverter.UniConverter
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.startstop.StartStopImpl
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.jackbradshaw.codestone.lifecycle.platforms.startstop.startStopWork

// Renamed from FromFrameworkConverter
class BackwardsUniConverter(
  private val conversionScope: CoroutineScope
) : UniConverter<Work<Job>, Work<StartStop<*, *>>> {
  
  override fun convert(input: Work<Job>): Work<StartStop<*, *>> {
    val job = input.handle
    val startStop = StartStopImpl<Unit, Throwable>()
    
    startStop.onStart {
      job.start()
      conversionScope.launch {
        job.invokeOnCompletion { startStop.stop() }
      }
    }

    startStop.onStop { 
      if (job.isActive) job.cancel()
    }
    
    // Jobs are already started when created. Match it.
    startStop.start()

    return startStopWork(startStop)
  }
}
