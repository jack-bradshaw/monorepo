package io.jackbradshaw.codestone.sustainment.omnisustainer

import io.jackbradshaw.codestone.sustainment.primitives.Sustainable
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.infrastructure.coroutines.CodestoneInternalCoroutines
import io.jackbradshaw.codestone.sustainment.omniconverter.OmniConverter
import io.jackbradshaw.codestone.sustainment.operations.KtCoroutineOperation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine

// TODO (jackbradshaw): Refactor this class to avoid flows
/** A simple implementation of [OmniSustainer]. */
class OmniSustainerSimplex<O : Operation<*>>
@Inject
constructor(
    private val inputConverter: OmniConverter<Operation<StartStop>>,
    private val outputConverter: OmniConverter<O>
) : OmniSustainer<O> {

  private val toSustain = MutableSharedFlow<Sustainable<*>>(replay = Int.MAX_VALUE)
  private val toRelease = MutableSharedFlow<Sustainable<*>>(replay = Int.MAX_VALUE)
  private val toReleaseAll = MutableSharedFlow<Unit>(replay = Int.MAX_VALUE)

  private val work = ConcurrentHashMap<Sustainable<*>, StartStop>()

  override fun sustain(sustainment: Sustainable<*>) {
    toSustain.tryEmit(sustainment)
  }

  override fun release(sustainment: Sustainable<*>) {
    toRelease.tryEmit(sustainment)
  }

  override fun releaseAll() {
    toReleaseAll.tryEmit(Unit)
  }

  override val operation =
      outputConverter.convert(
          object : KtCoroutineOperation() {
            override fun work() =
            CodestoneInternalCoroutines.forSustainment.launch {
                  launch {
                    toSustain.collect {
                      work[it] =
                          inputConverter.convert(it.operation as Operation<out Any>).work().also {
                            it.start()
                          }
                    }
                  }

                  launch {
                    toRelease.collect {
                      work[it]?.stop()
                      work.remove(it)
                    }
                  }

                  launch { toReleaseAll.collect { cancelAllOperations() } }

                  suspendCancellableCoroutine { it.invokeOnCancellation { cancelAllOperations() } }
                }
          })

  private fun cancelAllOperations() {
    for (task in work.values) task.stop()
    work.clear()
  }
}
