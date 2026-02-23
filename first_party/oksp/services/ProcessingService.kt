package com.jackbradshaw.oksp.services

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSNode
import com.jackbradshaw.oksp.model.SourceFile
import kotlinx.coroutines.flow.Flow
import com.google.devtools.ksp.symbol.KSAnnotated

interface ProcessingService {
  fun observeResolver(): Flow<Resolver>

  suspend fun publishSource(source: SourceFile, anchors: List<KSNode> = emptyList())

  suspend fun publishError(error: Throwable, anchors: KSNode)

  suspend fun publishError(error: Throwable)

  suspend fun publishError(error: String, anchors: KSNode)

  suspend fun publishError(error: String)

  suspend fun publishDeferred(node: KSAnnotated)

  suspend fun completeRound()
}

