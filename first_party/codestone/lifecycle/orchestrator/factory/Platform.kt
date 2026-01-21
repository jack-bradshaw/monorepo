package com.jackbradshaw.codestone.lifecycle.orchestrator.factory

import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.conversion.uniconverter.UniConverter

import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlin.reflect.KType

// TODO (jackbradshaw): Make this more readable by avoiding the nested pair.
/** Exposes UniConverters for working with operations of type [O]. */
interface Platform<O : Work<*>> {
  /** Gets a converter that converts from a Work<StartStop> to an [O]. Paired with to/from metadata. */
  fun forwardsUniConverter(): Pair<Pair<KType, KType>, UniConverter<Work<StartStop<*, *>>, O>>

  /** Gets a converter that converts from an [O] to a Work<StartStop>. Paired with to/from metadata. */
  fun backwardsUniConverter(): Pair<Pair<KType, KType>, UniConverter<O, Work<StartStop<*, *>>>>

  /**
   * Gets a converter that passes source operation through as output operation. Paired with to/from
   * metadata.
   */
  fun passThroughUniConverter(): Pair<Pair<KType, KType>, UniConverter<O, O>>
}
