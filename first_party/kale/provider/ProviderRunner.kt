package com.jackbradshaw.kale.provider

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.model.Versions

/**
 * Runs Kotlin Symbol Processing (KSP) on [SymbolProcessorProvider]s.
 *
 * The classpath for execution is inherited from the calling process.
 */
interface ProviderRunner {

  /** Run [providers] on [sources] in a KSP environment configured with [versions] and [options]. */
  suspend fun runProviders(
      providers: Set<SymbolProcessorProvider> = emptySet(),
      sources: Set<Source> = emptySet(),
      versions: Versions = Versions(),
      options: Map<String, String> = emptyMap()
  ): Result

  /** Convenience function to invoke [runProviders] with exactly one provider. */
  suspend fun <S : SymbolProcessorProvider> runProvider(
      provider: S,
      sources: Set<Source> = emptySet(),
      versions: Versions = Versions(),
      options: Map<String, String> = emptyMap()
  ): Result
}
