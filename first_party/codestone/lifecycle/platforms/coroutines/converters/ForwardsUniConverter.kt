package com.jackbradshaw.codestone.lifecycle.platforms.coroutines.converters

import com.jackbradshaw.codestone.lifecycle.conversion.uniconverter.UniConverter
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.async
import kotlinx.coroutines.Job
import kotlinx.coroutines.CancellationException
import kotlin.reflect.typeOf

// Renamed from ToFrameworkConverter to match Platform expectation
class ForwardsUniConverter(
  private val conversionScope: CoroutineScope
) : UniConverter<Work<StartStop<*, *>>, Work<Job>> {

  override fun convert(input: Work<StartStop<*, *>>): Work<Job> {
    val startStop = input.handle
    val deferred = conversionScope.async {
      try {
        startStop.start()
      } catch (e: Exception) {
          throw e
      }
      startStop.observeConclusion().collect()
    }
    
    deferred.invokeOnCompletion { cause ->
        if (cause != null && cause !is CancellationException) {
            startStop.abort()
        } else if (cause != null) {
            startStop.abort()
        }
    }
    
    return object : Work<Job> {
        override val handle = deferred
        override val workType = typeOf<Job>()
    }
  }
}
