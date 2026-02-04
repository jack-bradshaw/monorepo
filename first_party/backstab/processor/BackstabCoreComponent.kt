package com.jackbradshaw.backstab.processor

import com.jackbradshaw.backstab.processor.core.Processor

/** The core component of the Backstab framework. */
interface BackstabCoreComponent {
  /** Returns a [Processor] which can be used to generate Aggregate Components. */
  fun processor(): Processor
}
