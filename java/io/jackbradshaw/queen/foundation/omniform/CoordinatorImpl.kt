package io.jackbradshaw.queen.foundation.omniform

import io.jackbradshaw.queen.sustainment.primitives.Sustainable
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.ui.primitives.Usable
import io.jackbradshaw.queen.ui.primitives.Usable.Ui
import kotlinx.coroutines.channels.BufferOverflow
import io.jackbradshaw.queen.foundation.Navigator
import io.jackbradshaw.queen.foundation.Destination
import io.jackbradshaw.queen.foundation.Coordinator
import io.jackbradshaw.queen.sustainment.operations.ktCoroutineOperation
import io.jackbradshaw.queen.sustainment.operations.ktCoroutineSustainable
import io.jackbradshaw.queen.sustainment.operations.StartStopOperation
import io.jackbradshaw.queen.sustainment.omnisustainer.factoring.OmniSustainers
import io.jackbradshaw.queen.sustainment.startstop.StartStop
import io.jackbradshaw.queen.sustainment.startstop.StartStopSimplex
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.GlobalScope

class CoordinatorImpl<
  A,
  E,
  U : Ui, 
  D : Destination<U, *>, 
  N : Navigator<A, E, U, D, *>
>() : Coordinator<
  A,
  E,
  U,
  D,
  N,
  Operation<StartStop>
> {

  private val rail = OmniSustainers.create<StartStopOperation>(
    StartStopOperation.WORK_TYPE
  )

  private val pendingSignalFromApplication = MutableStateFlow<A?>(null)

  private val pendingSignalFromEnvironment = MutableStateFlow<E?>(null)

  private val _destination = MutableStateFlow<D?>(null)

  init {
    val manageNavigators = ktCoroutineSustainable(GlobalScope) {
        navigator.withPrevious().onEach {
          if (it.previous != null) rail.release(it.previous)
          if (it.current != null) rail.sustain(it.current)
        }.collect()
      }

      val manageDestinations = ktCoroutineSustainable(GlobalScope) {
        _destination.withPrevious().onEach {
          if (it.previous != null) rail.release(it.previous)
          if (it.current != null) rail.sustain(it.current)
        }.collect()
      }

    val translatePendingApplicationSignals = ktCoroutineSustainable(GlobalScope) {
      pendingSignalFromApplication
                  .filterNotNull()
                  .onEach { signal ->
                    val navigator = navigator.value ?: return@onEach
                    _destination.value = navigator.translateSignalFromApplication(signal)
                  }
                .collect()
        }

    val translatePendingEnvironmentSignals = ktCoroutineSustainable(GlobalScope) {
      pendingSignalFromEnvironment
                  .filterNotNull()
                  .onEach { signal ->
                    val navigator = navigator.value ?: return@onEach
                    _destination.value = navigator.translateSignalFromEnvironment(signal)
                  }
                .collect()
        }

    rail.sustain(manageNavigators)
    rail.sustain(manageDestinations)
    rail.sustain(translatePendingApplicationSignals)
    rail.sustain(translatePendingEnvironmentSignals)
  }

  override val navigator = MutableStateFlow<N?>(null)

  override val destination: StateFlow<D?>
    get() = _destination

  override fun onSignalFromApplication(signal: A) {
    pendingSignalFromEnvironment.value = null
    pendingSignalFromApplication.value = signal
  }

  override fun onSignalFromEnvironment(signal: E) {
    pendingSignalFromApplication.value = null
    pendingSignalFromEnvironment.value = signal
  }

  override val operation = rail.operation
}

data class History<T>(val previous: T?, val current: T?)

fun <T> Flow<T>.withPrevious(): Flow<History<T>> = this
    .runningFold(History<T?>(null, null)) {
      previous, current -> History(previous.current, current)
    }
    .drop(1)
    .map {
      /* By dropping the first emission the it.current value is guaranteed to be
       * non-null when T is non-null, or null when T is null. In any case, the cast
       * to T is safe. */
      History(it.previous, it.current as T)
    }