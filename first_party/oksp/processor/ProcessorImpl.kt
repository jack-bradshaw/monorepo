package com.jackbradshaw.oksp.processor

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import kotlinx.coroutines.channels.BufferOverflow
import com.jackbradshaw.oksp.model.SourceFile
import com.jackbradshaw.oksp.services.ProcessingService
import com.jackbradshaw.oksp.services.LifecycleService
import com.jackbradshaw.oksp.services.Stage
import javax.inject.Inject
import com.jackbradshaw.coroutines.io.Io
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.LinkedList

import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSDeclaration
import com.jackbradshaw.oksp.processor.Processor as OkspProcessor


/** The KSP processor for the Backstab annotation processor.
 * 
 * Notes on concurrency:
 * 
 * 
 */
class ProcessorImpl
@Inject
constructor(
    private val environment: SymbolProcessorEnvironment,
    @Io private val scope: CoroutineScope
) : OkspProcessor, ProcessingService, LifecycleService {

  private val _stage = MutableStateFlow(Stage.PENDING)

  override val stage = _stage

  private val roundResolver = MutableSharedFlow<Resolver>(
    replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
  )

  private val roundComplete = MutableSharedFlow<Unit>(
    replay = 0, onBufferOverflow = BufferOverflow.SUSPEND, extraBufferCapacity = 1
  )

  private val roundDeferred = MutableSharedFlow<KSAnnotated>(
    replay = 0, onBufferOverflow = BufferOverflow.SUSPEND, extraBufferCapacity = DEFERRED_BUFFER_SIZE
  )

  override fun process(resolver: Resolver): List<KSAnnotated> = runBlocking {
    if (_stage.value == Stage.PENDING) {
      _stage.value = Stage.RUNNING
    } else if (_stage.value == Stage.FINISHED) {
      throw IllegalStateException(
        "Received data into `process` after `finish` callback. This should not happen as it " +
        "violates the KSP contract: A `finish` callback should mean no more `process` callbacks " +
        "are coming."
      )
    }

    val allDeferred = LinkedList<KSAnnotated>()
    val collectAllDeferred = scope.launch {
      roundDeferred.onEach {
        allDeferred.add(it)
      }.collect()
    }

    roundResolver.emit(resolver)
    roundComplete.first()
    collectAllDeferred.cancel()

    return@runBlocking allDeferred
  }

  override fun finish() {
    runBlocking { 
      _stage.value = Stage.FINISHED
    }
  }

  override fun observeResolver(): Flow<Resolver> = roundResolver

  override suspend fun publishSource(source: SourceFile, anchors: List<KSNode>) {
    val dependencies = Dependencies(aggregating = true, *anchors.mapNotNull { it.getEnclosingFile() }.toTypedArray())
    val file =
        environment.codeGenerator.createNewFile(
            dependencies = dependencies,
            packageName = source.packageName,
            fileName = source.fileName,
            extensionName = source.extension)

    file.use { it.write(source.contents.toByteArray()) }
  }

  override suspend fun publishError(error: Throwable, anchors: KSNode) {
    environment.logger.error(error.toString(), anchors)
  }

  override suspend fun publishError(error: Throwable) {
    environment.logger.error(error.toString())
  }

  override suspend fun publishError(error: String, anchors: KSNode) {
    environment.logger.error(error, anchors)
  }

  override suspend fun publishError(error: String) {
    environment.logger.error(error)
  }

  override suspend fun publishDeferred(node: KSAnnotated) {
    roundDeferred.emit(node)
  }

  override suspend fun completeRound() {
    roundComplete.emit(Unit)
  }

  private companion object {
    private const val DEFERRED_BUFFER_SIZE = 1000
  }
}

private fun KSNode.getEnclosingFile(): KSFile? = when (this) {
  is KSFile -> this
  is KSDeclaration -> containingFile
  else -> parent?.getEnclosingFile()
}