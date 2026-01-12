package com.jackbradshaw.codestone.lifecycle.startstop

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * An operation which starts then stops at some point later. Once stopped, never starts again.
 */
interface StartStop<R, T : Throwable> {

  /** The current state of the operation */
  val state: StateFlow<ExecutionState>

  /** Starts the operation. Fails if [state] is not [ExecutionState.Pending]. Suspends until the
   * state has changed (or the state change fails). */
  fun start()

  fun abort()

  fun complete(result: R)
  
  fun fail(error: Throwable)

  /** Creates a new Flow which emits a value when any concluded state is reached by this operation.
   * The flow will finish after emiting a single value, and if
   * the flow is collected when a concluded state has already been reached it will still emit a
   * value. */
  fun observeConclusion(): Flow<Unit>
}

/** 
 * The state of an [Operation].
 * 
 * @param isPostStart whether the operation has been started
 * @param isPostStop whether the operation has been stopped after starting
 */
sealed class ExecutionState(
  val isPostStart: Boolean,
  val isPostStop: Boolean
) {

  interface Finished

  /** Execution has not started yet. */
  object Pending : ExecutionState(isPostStart = false, isPostStop = false)

  /** Execution has started and is proceeding towards a conclusion. */
  object Running : ExecutionState(isPostStart = true, isPostStop = false)

  /** Execution started then concluded. */
  sealed class Concluded : ExecutionState(isPostStart = true, isPostStop = true) {
    
    /** Execution started then concluded with premature abortion. */
    data object Aborted : Concluded(), Finished
  
    /** Execution started then concluded with successful completion. */
    data class Completed<T>(val result: T) : Concluded(), Finished
    
    /** Execution started then concluded with failure. */
    data class Failed<T : Throwable>(val error: T): Concluded(), Finished
  }
}