package com.jackbradshaw.kale.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.model.Versions

/**
 * Runs Kotlin Symbol Processing (KSP) on [SymbolProcessor]s.
 *
 * The classpath for execution is inherited from the calling process.
 */
interface ProcessorRunner {

  /**
   * Run [processors] on [sources] in a KSP environment configured with [versions] and [options].
   */
  suspend fun runProcessors(
      processors: Set<SymbolProcessor> = emptySet(),
      sources: Set<Source> = emptySet(),
      versions: Versions = Versions(),
      options: Map<String, String> = emptyMap()
  ): Result

  /** Convenience function to invoke [runProcessors] with exactly one processor. */
  suspend fun <P : SymbolProcessor> runProcessor(
      processor: P,
      sources: Set<Source> = emptySet(),
      versions: Versions = Versions(),
      options: Map<String, String> = emptyMap()
  ): Result
}
