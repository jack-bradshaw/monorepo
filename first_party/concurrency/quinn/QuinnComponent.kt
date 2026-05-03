
package com.jackbradshaw.concurrency.quinn

/** Provides a [Quinn.Factory]. */

interface QuinnComponent {
  /** Provides a [Quinn.Factory]. */
  fun quinnFactory(): Quinn.Factory
}
