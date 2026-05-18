package com.jackbradshaw.closet.observable

import com.jackbradshaw.closet.suspending.SuspendableClosable
import kotlinx.coroutines.flow.StateFlow

/**
 * A [SuspendableClosable] that broadcasts its [closureStatus] using state flows.
 *
 * The [closureStatus] is initially [Status.OPEN] and transitions to [Status.CLOSING] when the first
 * call to [close] occurs. It remains in that state until closure completes, even if the active call
 * to [close] is cancelled. Transitions are strictly monotonic, meaning [Status.OPEN] ->
 * [Status.CLOSING] -> [Status.CLOSED] is the only legal path.
 */
interface ObservableClosable : SuspendableClosable {

  /** The current closure status of this system. */
  val closureStatus: StateFlow<Status>

  /** The status of an [ObservableClosable]. */
  enum class Status {
    /** The closable is open (i.e. closure has not started). */
    OPEN,

    /** The closable is mid-way through closure. */
    CLOSING,

    /** The closable is completely closed. */
    CLOSED
  }
}
