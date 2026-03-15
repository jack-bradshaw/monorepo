package com.jackbradshaw.closet.observable

import kotlinx.coroutines.flow.StateFlow

/** 
 * An [AutoClosable] which broadcasts its closure status using flows.
 * 
 * Closure statis is split into [hasTerminalState], which tracks whether the internal state of the
 * closable has reached a closed state, and [hasTerminaltedProcessing] which tracks whether the
 * background work managed by this closable has fully halted. The [close] function must transition
 * both to closed and block until both state and processes have been closed.
 * 
 * Implementations must ensure:
 * 
 * - The [close] function is idempotent and thread-safe.
 * - The [hasTerminalState] flag becomes true before or as [hasTerminatedProcesses] becomes true. It
 * is an error for [hasTerminatedProcesses] to become true first.
 * - The [hasTerminatedProcesses] flag does not become true until all background work managed by
 * this closable has fully halted (not just been scheduled for cancellation).
 * - The [close] function blocks until [hasTerminalState] and [hasTerminatedProcesses] are both
 * true.
 * 
 * Essentially, an obsevable closable broadcasts its closure state, differentiates between state
 * closure and process closure, and blocks [close] until both state and processes have closed.
 *  */
interface ObservableClosable : AutoCloseable {

  /** Whether the state managed by this object has reached a terminal state.  */
  val hasTerminalState: StateFlow<Boolean>

  /** Whether the processes managed by this object have reached a terminal condition. */
  val hasTerminatedProcesses: StateFlow<Boolean>
}