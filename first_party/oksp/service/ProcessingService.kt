package com.jackbradshaw.oksp.service

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSNode
import com.jackbradshaw.oksp.model.SourceFile
import kotlinx.coroutines.flow.Flow

interface ProcessingService {

  fun onRoundStart(): Flow<Unit>

  suspend fun withResolver(block: (Resolver) -> Unit)

  suspend fun publishSource(source: SourceFile, anchors: List<KSNode> = emptyList())

  suspend fun publishError(error: Throwable, anchor: KSNode? = null)

  suspend fun publishError(error: String, anchor: KSNode? = null)

  suspend fun publishDeferred(node: KSAnnotated)

  suspend fun completeRound()
}
