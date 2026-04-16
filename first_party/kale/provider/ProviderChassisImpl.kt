package com.jackbradshaw.kale.provider

import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.kale.ksprunner.KspRunner
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.model.KspVersions
import javax.inject.Inject

class ProviderChassisImpl @Inject internal constructor(private val kspRunner: KspRunner) :
    ProviderChassis {

  override suspend fun <S : SymbolProcessorProvider> runProvider(
      provider: S,
      sources: Set<JvmSource>,
      versions: KspVersions,
      compilerOptions: Map<String, String>
  ): ProviderChassis.Result {
    val result =
        kspRunner.runKsp(sources, setOf(provider), versions = versions, options = compilerOptions)
    return when (result) {
      is KspRunner.Result.Success -> ProviderChassis.Result.Success(result.artefacts)
      is KspRunner.Result.Failure -> ProviderChassis.Result.Failure(result.artefacts, result.error)
    }
  }
}
