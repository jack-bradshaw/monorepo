package com.jackbradshaw.kale.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.kale.ksprunner.KspRunner
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.model.KspVersions
import javax.inject.Inject

class ProcessorChassisImpl @Inject internal constructor(private val kspRunner: KspRunner) :
    ProcessorChassis {

  override suspend fun <P : SymbolProcessor> runProcoessor(
      processor: P,
      sources: Set<JvmSource>,
      versions: KspVersions,
      options: Map<String, String>
  ): ProcessorChassis.Result {

    val providers =
        setOf(
            object : SymbolProcessorProvider {
              override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
                return processor
              }
            })

    val result = kspRunner.runKsp(sources, providers, versions = versions, options = options)

    return result.toProcessorChassisResult()
  }
}

/** Converts this [KspRunner.Result] to an equivalent [ProcessorChassis.Result]. */
private fun KspRunner.Result.toProcessorChassisResult() =
    when (this) {
      is KspRunner.Result.Success -> ProcessorChassis.Result.Success(this.artefacts)
      is KspRunner.Result.Failure -> ProcessorChassis.Result.Failure(this.artefacts, this.error)
    }
