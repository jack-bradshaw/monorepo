package io.jackbradshaw.queen.sustainment.omniconverter

import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.uniconverter.UniConverter
import kotlin.reflect.KType

/** A simple implementation of [OmniConverter]. */
class OmniConverterSimplex<O : Operation<*>>(
    private val oReified: KType,
    private val UniConverters:
        Map<Pair<KType, KType>, UniConverter<Operation<*>, Operation<*>>>
) : OmniConverter<O> {

  override fun convert(source: Operation<*>): O {
    val from = source.workType
    val fromTo: Pair<KType, KType> = from to oReified
    val converter = UniConverters[fromTo]
            ?: throw IllegalStateException(
                "No UniConverter found for converting $from to $oReified.")

    return converter.convert(source) as? O
        ?: throw IllegalStateException("Conversion from $from to $oReified failed.")
  }
}
