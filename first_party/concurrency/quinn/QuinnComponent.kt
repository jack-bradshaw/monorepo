package com.jackbradshaw.concurrency.quinn

/** Generic multi-producer single-consumer actor-object infrastructure. */
interface QuinnComponent {
  /** Provides a [Quinn.Factory]. */
  fun quinnFactory(): Quinn.Factory
}
