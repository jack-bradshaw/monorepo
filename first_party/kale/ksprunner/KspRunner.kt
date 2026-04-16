package com.jackbradshaw.kale.ksprunner

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.model.KspArtefacts
import com.jackbradshaw.kale.model.KspVersions

/**
 * Runs Kotlin Symbol Processing (KSP).
 *
 * The classpath for execution is inherited from the calling process.
 */
interface KspRunner {

  /** Run KSP on [sources] with [providers] and [options]. */
  fun runKsp(
      sources: Set<JvmSource>,
      providers: Set<SymbolProcessorProvider> = emptySet(),
      versions: KspVersions = KspVersions(),
      options: Map<String, String> = emptyMap()
  ): Result

  /** Convenience function to invoke [runKsp] with only one provider. */
  fun runKsp(
      sources: Set<JvmSource>,
      providers: SymbolProcessorProvider,
      versions: KspVersions = KspVersions(),
      options: Map<String, String> = emptyMap()
  ): Result

  /**
   * The outputs and metadata from a KSP run.
   *
   * Files produced by the run are stored in [artefacts] (if any).
   */
  sealed class Result(val artefacts: KspArtefacts) {

    /** Indicates the run succeeded without errors. */
    class Success(artefacts: KspArtefacts) : Result(artefacts)

    /** Indicates the run failed with [error] (present where possible). */
    class Failure(artefacts: KspArtefacts, val error: Throwable? = null) : Result(artefacts)
  }
}
