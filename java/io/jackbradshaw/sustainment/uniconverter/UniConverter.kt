package io.jackbradshaw.queen.sustainment.uniconverter

import io.jackbradshaw.sustainment.primitives.Sustainable.Operation

/** Converts an operation of type [I] to an operation of type [O]. */
interface UniConverter<in I : Operation<*>, out O : Operation<*>> {

  /**
   * Converts the input operation to an equivalent operation of type [O].
   *
   * The state of the output is linked to the state of the input by the following contract:
   *
   * TODO (jackbradshaw): List the confusing details
   */
  fun convert(source: I): O
}