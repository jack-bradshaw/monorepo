package com.jackbradshaw.codestone.lifecycle.startstop

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/** A straightforward implementation of [StartStop]. */
open class StartStopImpl<R, T : Throwable>
@Inject
constructor() : StartStop<R, T> {

  private val _state = MutableStateFlow<ExecutionState>(ExecutionState.Pending)
  private val stateLock = Any()
  private var onStartAction: (() -> Unit)? = null
  private var onStopAction: (() -> Unit)? = null

  fun onStart(action: () -> Unit) {
    onStartAction = action
  }

  fun onStop(action: () -> Unit) {
    onStopAction = action
  }


  override val state: StateFlow<ExecutionState> = _state

  override fun start() {
    synchronized(stateLock) {
      val state = _state.value
      if (state != ExecutionState.Pending) {
         // Idempotent or error? Original said error.
        throw IllegalStateException("Cannot move to started state from $state.")
      }
      _state.value = ExecutionState.Running
    }
    onStartAction?.invoke()
  }

  override fun abort() {
    synchronized(stateLock) {
       val state = _state.value
       if (state != ExecutionState.Running) {
         throw IllegalStateException("Cannot move to aborted state from $state.")
       }
       _state.value = ExecutionState.Concluded.Aborted // Concluded is sealed, Aborted is object.
    }
    onStopAction?.invoke()
  }
  
  fun stop() {
    abort()
  }

  override fun complete(result: R) {
    synchronized(stateLock) {
      val state = _state.value
      if (state != ExecutionState.Running) {
        throw IllegalStateException("Cannot move to completed state from $state.")
      }
      _state.value = ExecutionState.Concluded.Completed(result)
    }
    onStopAction?.invoke()
  }

  override fun fail(error: Throwable) {
    // E : Throwable. But fail takes Throwable. error is Throwable.
    // _state.value expectation? Failed<T>.
    // If T != E, error. But T is inferred? 
    // StartStop<R, E>.
    // _state is ExecutionState (not generic).
    // Failed stores <T : Throwable>.
    // So generic E doesn't matter for _state type.
    synchronized(stateLock) {
      val state = _state.value
      if (state != ExecutionState.Running) {
        throw IllegalStateException("Cannot move to failed state from $state.")
      }
      _state.value = ExecutionState.Concluded.Failed(error)
    }
    onStopAction?.invoke()
  }

  override fun observeConclusion(): Flow<Unit> = _state.filter { 
    it is ExecutionState.Concluded
  }.map { }
}
