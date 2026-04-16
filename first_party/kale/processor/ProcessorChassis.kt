package com.jackbradshaw.kale.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.model.KspArtefacts
import com.jackbradshaw.kale.model.KspVersions

/**
 * Accepts Java/Kotlin sources and a KSP processor to run on them. It runs your processor inside the
 * K2 compiler and provides testing visibility.
 */
interface ProcessorChassis {
  suspend fun <P : SymbolProcessor> runProcoessor(
      processor: P,
      sources: Set<JvmSource> = emptySet(),
      versions: KspVersions = KspVersions(),
      options: Map<String, String> = emptyMap()
  ): Result

  /**
   * The outputs and metadata from a processor chassis run.
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
