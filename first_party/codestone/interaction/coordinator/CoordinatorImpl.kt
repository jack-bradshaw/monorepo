package com.jackbradshaw.codestone.interaction.coordinator

import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.interaction.usable.Usable
import com.jackbradshaw.codestone.interaction.usable.Usable.Ui
import kotlinx.coroutines.channels.BufferOverflow
import com.jackbradshaw.codestone.interaction.navigator.Navigator
import com.jackbradshaw.codestone.interaction.destination.Destination
import com.jackbradshaw.codestone.interaction.coordinator.Coordinator
import com.jackbradshaw.codestone.lifecycle.platforms.coroutines.ktCoroutineWorker
import com.jackbradshaw.codestone.lifecycle.platforms.startstop.StartStopOperation
import com.jackbradshaw.codestone.lifecycle.orchestrator.factory.WorkOrchestrators
import com.jackbradshaw.codestone.lifecycle.startstop.StartStopImpl
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
  Work<StartStop<StartStop<*, *>, Throwable>>
> {

  private val rail = WorkOrchestrators.create<Work<StartStop<StartStop<*, *>, Throwable>>>(
    StartStopOperation.WORK_TYPE,
    GlobalScope
  )

  private val pendingSignalFromApplication = MutableStateFlow<A?>(null)

  private val pendingSignalFromEnvironment = MutableStateFlow<E?>(null)

  private val _destination = MutableStateFlow<D?>(null)

  init {
    val manageNavigators = ktCoroutineWorker(GlobalScope) {
        navigator.withPrevious().onEach {
          if (it.previous != null) rail.release(it.previous)
          if (it.current != null) rail.orchestrate(it.current)
        }.collect()
      }

      val manageDestinations = ktCoroutineWorker(GlobalScope) {
        _destination.withPrevious().onEach {
          if (it.previous != null) rail.release(it.previous)
          if (it.current != null) rail.orchestrate(it.current)
        }.collect()
      }

    val translatePendingApplicationSignals = ktCoroutineWorker(GlobalScope) {
      pendingSignalFromApplication
                  .filterNotNull()
                  .onEach { signal ->
                    val navigator = navigator.value ?: return@onEach
                    _destination.value = navigator.translateSignalFromApplication(signal)
                  }
                .collect()
        }

    val translatePendingEnvironmentSignals = ktCoroutineWorker(GlobalScope) {
      pendingSignalFromEnvironment
                  .filterNotNull()
                  .onEach { signal ->
                    val navigator = navigator.value ?: return@onEach
                    _destination.value = navigator.translateSignalFromEnvironment(signal)
                  }
                .collect()
        }

    rail.orchestrate(manageNavigators)
    rail.orchestrate(manageDestinations)
    rail.orchestrate(translatePendingApplicationSignals)
    rail.orchestrate(translatePendingEnvironmentSignals)
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

  override val work = rail.work
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