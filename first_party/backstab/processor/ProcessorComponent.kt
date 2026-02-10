package com.jackbradshaw.backstab.processor

import com.jackbradshaw.backstab.processor.Processor

/** The core component of the Backstab framework. */
interface ProcessorComponent {
  /** Returns a [Processor] which can be used to generate Aggregate Components. */
  fun processor(): Processor
}
