package com.jackbradshaw.closet.observable.standard

import com.jackbradshaw.closet.observable.ObservableClosable

/** Creates standard instances of [ObservableClosable]. */
interface StandardObservableClosableFactory {
  /**
   * Creates a new [ObservableClosable] that invokes [closure] during closure.
   *
   * The returned instance strictly adheres to the [ObservableClosable] contract, meaning it updates
   * [closureStatus] when [close] starts/finishes, it manages concurrent access to prevent
   * concurrent execution of [close], and it skips [close] after the terminal CLOSED state is
   * reached. This allows callers to ignore the complexity of the [ObservableClosable] contract and
   * instead define their custom closure logic via [closure].
   *
   * The returned instance invokes [closure] during [close]. It runs after entering the CLOSING
   * state but must complete before it can enter the terminal CLOSED state. It passes itself as the
   * receiver, and the invocation inherits the cancellation scope of the active [close] call,
   * meaning if the coroutine calling [close] is cancelled, the execution of [closure] is also
   * cancelled. Every call to [close] will invoke the logic until the terminal CLOSED state is
   * reached, but since the returned instance strictly adheres to the [ObservableClosable] contract,
   * at most one invocation will be running at any given time.
   */
  suspend fun createStandardClosable(
      closure: suspend ObservableClosable.() -> Unit = {}
  ): ObservableClosable
}
