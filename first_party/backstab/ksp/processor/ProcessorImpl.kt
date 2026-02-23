package com.jackbradshaw.backstab.ksp.processor

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.typeregistry.BackstabTypeRegistry
import com.jackbradshaw.backstab.ksp.parser.Parser
import com.jackbradshaw.coroutines.io.Io
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/** The KSP processor for the Backstab annotation processor. */
@CoreScope
class KspBackend
@Inject
constructor(
    private val parser: Parser,
    private val environment: SymbolProcessorEnvironment,
    @Io private val scope: CoroutineScope
) : Processor {

  private val _onProcessingComplete = MutableSharedFlow<Unit>(replay = 1)

  private val outboundProcessingQueue =
      MutableSharedFlow<BackstabTarget>(replay = 0, extraBufferCapacity = OUTGOING_QUEUE_SIZE)

  private val currentRoundResponseTracker = MutableSharedFlow<Unit>(replay = 0)

  private var currentRoundTargets: Map<BackstabTarget, KSClassDeclaration> = emptyMap()

  override val onProcessingComplete: SharedFlow<Unit> = _onProcessingComplete.asSharedFlow()

  override fun process(resolver: Resolver): List<KSAnnotated> = runBlocking {
    val allBackstabSymbols = resolveBackstabAnnotatedClasses(resolver)
    val validSymbols = resolveValidSymbols(allBackstabSymbols)

    currentRoundTargets = validSymbols.associateBy { parser.toBackstabTarget(it) }

    val roundCompleteTracker =
        scope.launch { currentRoundResponseTracker.take(validSymbols.size).collect() }

    for (target in currentRoundTargets.keys) {
      scope.launch { outboundProcessingQueue.emit(target) }
    }

    if (validSymbols.isNotEmpty()) {
      roundCompleteTracker.join()
    }

    return@runBlocking resolveInvalidSymbols(allBackstabSymbols)
  }

  override fun finish() {
    /* Using runBlocking prevents finish from returning until all shared flow collectors have
     * processed the event, thereby keeping the process alive until they have cleaned up resources.*/
    runBlocking { _onProcessingComplete.emit(Unit) }
  }

  override fun observeTargets(): Flow<BackstabTarget> = outboundProcessingQueue

  override suspend fun publishModules(target: BackstabTarget, modules: List<BackstabModule>) {
    val declaration =
        checkNotNull(currentRoundTargets[target]) {
          "Received response for target $target which was not found in the current processing round."
        }
    val file = checkNotNull(declaration.containingFile)
    val dependencies = Dependencies(true, file)
    writeModule(modules, dependencies)
    currentRoundResponseTracker.emit(Unit)
  }

  override suspend fun publishError(target: BackstabTarget, error: Throwable) {
    checkNotNull(currentRoundTargets[target]) {
      "Received error for target $target which was not found in the current processing round."
    }
    environment.logger.error(
        "Backstab: Error during generation for target $target: ${error.message}")
    currentRoundResponseTracker.emit(Unit)
  }

  private fun resolveBackstabAnnotatedClasses(resolver: Resolver): List<KSAnnotated> {
    return resolver.getSymbolsWithAnnotation(BackstabTypeRegistry.BACKSTAB.qualifiedName!!).toList()
  }

  private fun resolveValidSymbols(symbols: List<KSAnnotated>): List<KSClassDeclaration> {
    return symbols.filterIsInstance<KSClassDeclaration>().filter { it.validate() }
  }

  private fun resolveInvalidSymbols(symbols: List<KSAnnotated>): List<KSAnnotated> {
    return symbols.filter { if (it is KSClassDeclaration) !it.validate() else true }
  }

  private suspend fun writeModule(modules: List<BackstabModule>, dependencies: Dependencies) {
    for (module in modules) {
      val file =
          environment.codeGenerator.createNewFile(
              dependencies = dependencies,
              packageName = module.sourceFile.packageName,
              fileName = module.sourceFile.fileName,
              extensionName = module.sourceFile.extension)

      file.use { it.write(module.sourceFile.contents.toByteArray()) }
    }
  }

  private companion object {
    /**
     * Size of the buffer used to schedule files for processing. The value was chosen arbitrarily
     * since the system can continue to operate with a full buffer and individaul processing rounds
     * are unlikely to contain more than 1000 files in practice.
     */
    private val OUTGOING_QUEUE_SIZE = 1000
  }
}
