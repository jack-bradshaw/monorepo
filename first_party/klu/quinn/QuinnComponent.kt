package com.jackbradshaw.quinn

/** Provides a [Quinn.Factory]. */
interface QuinnComponent {
  /** Provides a [Quinn.Factory]. Calls are idempotent and return the same instance. */
  fun quinnFactory(): Quinn.Factory
}