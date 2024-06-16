package io.jackbradshaw.codestone.sustainment.omnisustainer.factoring

import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.uniconverter.UniConverter
import io.jackbradshaw.codestone.sustainment.operations.StartStopOperation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import kotlin.reflect.KType

// TODO (jackbradshaw): Make this more readable by avoiding the nested pair.
/** Exposes UniConverters for working with operations of type [O]. */
interface Platform<O : Operation<*>> {
  /** Gets a converter that converts from a [StartStop] to an [O]. Paired with to/from metadata. */
  fun forwardsUniConverter(): Pair<Pair<KType, KType>, UniConverter<Operation<StartStop>, O>>

  /** Gets a converter that converts from an [O] to a [StartStop]. Paired with to/from metadata. */
  fun backwardsUniConverter(): Pair<Pair<KType, KType>, UniConverter<O, Operation<StartStop>>>

  /**
   * Gets a converter that passes source operation through as output operation. Paired with to/from
   * metadata.
   */
  fun passThroughUniConverter(): Pair<Pair<KType, KType>, UniConverter<O, O>>
}
