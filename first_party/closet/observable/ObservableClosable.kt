package com.jackbradshaw.closet.observable

import kotlinx.coroutines.flow.StateFlow

/**
 * An [AutoCloseable] which broadcasts its closure status using flows.
 *
 * Closure status is split into [hasTerminalState], which tracks whether the state of the closable
 * has reached its terminal closed state, and [hasTerminatedProcesses] which tracks whether the
 * background work managed by this closable has terminated. The [close] function must transition
 * both to closed and block until the transition is complete.
 *
 * Implementations must ensure:
 * - The [close] function is idempotent and thread-safe.
 * - The [hasTerminalState] flag becomes true before or as [hasTerminatedProcesses] becomes true. It
 *   is an error for [hasTerminatedProcesses] to become true first.
 * - The [hasTerminatedProcesses] flag does not become true until all background work managed by
 *   this closable has fully halted (not just been scheduled for cancellation).
 * - The [close] function blocks until [hasTerminalState] and [hasTerminatedProcesses] are both
 *   true.
 *
 * Essentially, an observable closable broadcasts its closure state but is otherwise a normal
 * [AutoClosable], with a few conditions added around how the flags interact with closure.
 */
interface ObservableClosable : AutoCloseable {

  /** Whether the state managed by this object has reached a terminal state. */
  val hasTerminalState: StateFlow<Boolean>

  /** Whether the processes managed by this object have terminated. */
  val hasTerminatedProcesses: StateFlow<Boolean>
}
