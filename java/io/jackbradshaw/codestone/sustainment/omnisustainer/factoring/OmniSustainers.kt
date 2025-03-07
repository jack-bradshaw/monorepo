package io.jackbradshaw.codestone.sustainment.omnisustainer.factoring

import io.jackbradshaw.codestone.sustainment.primitives.Sustainable
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.omnisustainer.OmniSustainer
import io.jackbradshaw.codestone.sustainment.omnisustainer.OmniSustainerSimplex
import io.jackbradshaw.codestone.sustainment.uniconverter.UniConverter
import io.jackbradshaw.codestone.sustainment.omniconverter.OmniConverter
import io.jackbradshaw.codestone.sustainment.omniconverter.OmniConverterSimplex
import io.jackbradshaw.codestone.sustainment.operations.StartStopOperation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KType

/**
 * Creates new OmniSustainer instances for converting between any concurrency type, with the
 * limitation being the platforms currently on the classpath.
 */
object OmniSustainers {

  @Suppress("UNCHECKED_CAST") // Safe by design, but uncheckable due to language limitations.
  private val allUniConverters =
      buildMap<Pair<KType, KType>, UniConverter<Operation<*>, Operation<*>>> {
        for (platform in PlatformsSimplex().getAll()) {
          platform.forwardsUniConverter().let {
            put(it.first, it.second as UniConverter<Operation<*>, Operation<*>>)
          }
          platform.backwardsUniConverter().let {
            put(it.first, it.second as UniConverter<Operation<*>, Operation<*>>)
          }
          platform.passThroughUniConverter().let {
            put(it.first, it.second as UniConverter<Operation<*>, Operation<*>>)
          }
        }
      }

  private val startStopOmniConverter =
      OmniConverterSimplex<Operation<StartStop>>(
          StartStopOperation.WORK_TYPE, allUniConverters)

  private val targetsToOmniConverters = ConcurrentHashMap<KType, OmniConverter<*>>()

  @Suppress("UNCHECKED_CAST") // Safe by design, but uncheckable due to language limitations.
  fun <T : Operation<*>> create(target: KType): OmniSustainer<T> {
    val targetOmniConverter: OmniConverter<T> =
        (targetsToOmniConverters[target]
            ?: OmniConverterSimplex<Operation<T>>(target, allUniConverters).also {
              targetsToOmniConverters[target] = it
            })
            as OmniConverter<T>

    return OmniSustainerSimplex(startStopOmniConverter, targetOmniConverter)
  }

  fun <T : Operation<*>> create(target: KType, sustaining: Set<Sustainable<*>>): OmniSustainer<T> {
    val omniSustainer = create<T>(target)
    sustaining.forEach { omniSustainer.sustain(it) }
    return omniSustainer
  }
}

fun <T : Operation<*>> create(target: KType, sustaining: Sustainable<*>): OmniSustainer<T> {
  return OmniSustainers.create(target, setOf(sustaining))
}
