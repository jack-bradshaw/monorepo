package com.jackbradshaw.closet.observable

import kotlinx.coroutines.flow.StateFlow

/** 
 * An [AutoClosable] which exposes its closure status using flows.
 * 
 * Closed state is bifurcated into [hasTerminalState], which tracks whether the internal state of the
 * object has reached a terminal state, and [hasTerminaltedProcessing] which tracks whether the
 * internal processes have halted. The main [close] function must block until both are true.
 * Implementations must guaranteed that [hasTerminalState] becomes true before or in
 * synchronisation with [hasTerminatedProcesses], but never after [hasTerminatedProcesses],
 * thereby ensuring closure is deterministic and derived from state. */
interface ObservableClosable : AutoCloseable {

  /** Whether the state managed by this object has reached a terminal state.  */
  val hasTerminalState: StateFlow<Boolean>

  /** Whether the processes managed by this object have reached a terminal condition. */
  val hasTerminatedProcesses: StateFlow<Boolean>
}