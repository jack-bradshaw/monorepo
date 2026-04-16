package com.jackbradshaw.kale.provider

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.model.KspArtefacts
import com.jackbradshaw.kale.model.KspVersions

/**
 * Acts identically to [ProcessorChassis] but targets the full [SymbolProcessorProvider] lifecycle
 * boundary, invoking both `create` and the internal `process` functions of an injected provider.
 */
interface ProviderChassis {
  suspend fun <S : SymbolProcessorProvider> runProvider(
      provider: S,
      sources: Set<JvmSource> = emptySet(),
      versions: KspVersions = KspVersions(),
      compilerOptions: Map<String, String> = emptyMap()
  ): Result
  /**
   * The outputs and metadata from a provider chassis run.
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
