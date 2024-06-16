package io.jackbradshaw.queen.sustainment.omnisustainer

import io.jackbradshaw.queen.sustainment.primitives.Sustainable
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation

/**
 * Sustains multiple [sustainments] of any type (limited by the converters on the classpath) and
 * exposes a single work handle to control them all.
 *
 * For example:
 * ***
 * class SomeObject : Sustainable<KtCoroutineOperation> { override val operation =
 * OmniSustainers.create<ListenableFutureOperation>( ListenableFutureOperation.WORK_TYPE ). apply {
 * sustain (ktCoroutineSustianable) sustain (listenableFuturesustainment) sustain
 * (startStopsustainment) . sustain (someOthersustainment)
 *
 * }
 */
interface OmniSustainer<O : Operation<*>> : Sustainable<O> {
  fun sustain(sustainment: Sustainable<*>)

  fun release(sustainment: Sustainable<*>)

  fun releaseAll()
}
