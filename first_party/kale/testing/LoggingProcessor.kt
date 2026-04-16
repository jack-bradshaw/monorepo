package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

/**
 * A [SymbolProcessor] that tracks whether [process] was invoked and emits test logs during
 * processing.
 *
 * Logs are emitted at every level, and each log string derives from the level (e.g. "test info" for
 * info level logging).
 */
class LoggingProcessor(private val logger: KSPLogger) : SymbolProcessor {

  /** Whether [process] was invoked. */
  var didRunProcess = false
    private set

  override fun process(resolver: Resolver): List<KSAnnotated> {
    didRunProcess = true
    logger.logging("test logging")
    logger.info("test info")
    logger.warn("test warn")
    logger.error("test error")
    logger.exception(RuntimeException("test exception"))
    return emptyList()
  }
}
