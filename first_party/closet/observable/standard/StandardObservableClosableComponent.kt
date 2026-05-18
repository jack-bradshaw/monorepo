package com.jackbradshaw.closet.observable.standard

/** Provides a [StandardObservableClosableFactory]. */
interface StandardObservableClosableComponent {
  /**
   * Provides a [StandardObservableClosableFactory]. Calls are idempotent and return the same
   * instance.
   */
  fun standardObservableClosableFactory(): StandardObservableClosableFactory
}
