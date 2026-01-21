package com.jackbradshaw.codestone.lifecycle.conversion.multiconverter

import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.conversion.uniconverter.UniConverter
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.full.starProjectedType

/** A simple implementation of [MultiConverter]. */
class MultiConverterImpl<O : Any>(
    private val outputType: KType,
    private val converters: Map<Pair<KType, KType>, UniConverter<*, *>>,
) : MultiConverter<O> {

  override fun convert(input: Any): O {
    val inputType = if (input is com.jackbradshaw.codestone.lifecycle.work.Work<*>) {
        input.workType
    } else {
        // Fallback or error? Codestone should deal with Work.
        input::class.starProjectedType
    }

    val incomingConverter = converters[Pair(inputType, operationClass)]
        ?: throw IllegalStateException("No converter found for input type $inputType to StartStop")
    val outgoingConverter = converters[Pair(operationClass, outputType)]
        ?: throw IllegalStateException("No converter found for StartStop to output type $outputType")
    
    // Unsafe casting required due to Map storing UniConverter<*, *>
    @Suppress("UNCHECKED_CAST")
    val safeIncoming = incomingConverter as UniConverter<Any, Any>
    
    @Suppress("UNCHECKED_CAST")
    val safeOutgoing = outgoingConverter as UniConverter<Any, O>

    return safeOutgoing.convert(safeIncoming.convert(input))
  }

  companion object {
    /** Cached to prevent frequent reflective access. */
    private val operationClass = kotlin.reflect.typeOf<com.jackbradshaw.codestone.lifecycle.startstop.StartStop<*, *>>()
  }
}
