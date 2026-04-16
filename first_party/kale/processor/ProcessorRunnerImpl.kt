package com.jackbradshaw.kale.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.model.Versions
import com.jackbradshaw.kale.provider.ProviderRunner
import javax.inject.Inject

/** [ProcessorRunner] that executes on [providerRunner]. */
class ProcessorRunnerImpl @Inject internal constructor(private val providerRunner: ProviderRunner) :
    ProcessorRunner {

  override suspend fun runProcessors(
      processors: Set<SymbolProcessor>,
      sources: Set<Source>,
      versions: Versions,
      options: Map<String, String>
  ): Result {
    val providers =
        processors
            .map { processor ->
              object : SymbolProcessorProvider {
                override fun create(environment: SymbolProcessorEnvironment) = processor
              }
            }
            .toSet()

    return providerRunner.runProviders(providers, sources, versions, options)
  }

  override suspend fun <P : SymbolProcessor> runProcessor(
      processor: P,
      sources: Set<Source>,
      versions: Versions,
      options: Map<String, String>
  ): Result = runProcessors(setOf(processor), sources, versions, options)
}
