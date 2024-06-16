package io.jackbradshaw.queen.sustainment.omniconverter

import io.jackbradshaw.sustainment.primitives.Sustainable.Operation

/** Converts an operation of any type to an operation of type [T]. */
interface OmniConverter<out T : Operation<*>> {
  fun convert(source: Operation<*>): T
}
