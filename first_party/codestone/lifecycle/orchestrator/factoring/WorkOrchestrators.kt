package com.jackbradshaw.codestone.lifecycle.orchestrator.factoring

import com.jackbradshaw.codestone.lifecycle.work.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.orchestrator.WorkOrchestrator
import com.jackbradshaw.codestone.lifecycle.orchestrator.WorkOrchestratorImpl
import com.jackbradshaw.codestone.lifecycle.uniconverter.UniConverter
import com.jackbradshaw.codestone.lifecycle.omniconverter.MultiConverter
import com.jackbradshaw.codestone.lifecycle.omniconverter.MultiConverterImpl
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KType
import kotlin.reflect.typeOf
import kotlinx.coroutines.CoroutineScope

object WorkOrchestrators {

  @Suppress("UNCHECKED_CAST")
  private val allUniConverters =
      buildMap<Pair<KType, KType>, UniConverter<*, *>> {
        for (platform in PlatformsImpl().getAll()) {
          platform.forwardsUniConverter().let {
            put(it.first, it.second)
          }
          platform.backwardsUniConverter().let {
            put(it.first, it.second)
          }
          platform.passThroughUniConverter().let {
            put(it.first, it.second)
          }
        }
      }

  private val startStopWorkType = typeOf<Work<StartStop<*, *>>>()

  private val startStopMultiConverter =
      MultiConverterImpl<Work<StartStop<*, *>>>(
          startStopWorkType, allUniConverters)

  private val targetsToMultiConverters = ConcurrentHashMap<KType, MultiConverter<*>>()

  @Suppress("UNCHECKED_CAST")
  fun <T : com.jackbradshaw.codestone.lifecycle.primitives.Work<*>> create(target: KType, integrationScope: CoroutineScope): WorkOrchestrator<T> {
    val targetMultiConverter: MultiConverter<T> =
        (targetsToMultiConverters[target]
            ?: MultiConverterImpl<T>(target, allUniConverters).also {
              targetsToMultiConverters[target] = it
            })
            as MultiConverter<T>

    return WorkOrchestratorImpl(startStopMultiConverter, targetMultiConverter, integrationScope)
  }

  fun <T : com.jackbradshaw.codestone.lifecycle.primitives.Work<*>> create(
      target: KType, 
      sustaining: Set<Worker<*>>, 
      integrationScope: CoroutineScope
  ): WorkOrchestrator<T> {
    val orchestrator = create<T>(target, integrationScope)
    sustaining.forEach { orchestrator.orchestrate(it) }
    return orchestrator
  }
}
