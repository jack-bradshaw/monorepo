package com.jackbradshaw.publicity.conformance

import com.jackbradshaw.publicity.conformance.runner.Runner

/** Provides the conformance runner. */
interface ConformanceComponent {
  fun runner(): Runner
}
