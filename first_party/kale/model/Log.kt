package com.jackbradshaw.kale.model

import com.google.devtools.ksp.symbol.KSNode

/** A log message captured from a KSP run. */
sealed interface Log {
  /** A logged message with no specific level. */
  data class Unspecified(val message: String, val symbol: KSNode? = null) : Log

  /** A logged message with level INFO. */
  data class Info(val message: String, val symbol: KSNode? = null) : Log

  /** A logged message with level WARN. */
  data class Warning(val message: String, val symbol: KSNode? = null) : Log

  /** A logged message with level ERROR. */
  data class Error(val message: String, val symbol: KSNode? = null) : Log

  /** A logged fatal exception. */
  data class Exception(val error: Throwable) : Log
}
