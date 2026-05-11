package com.jackbradshaw.sealant.source

import com.jackbradshaw.sealant.flow.SealedFlow
import com.jackbradshaw.sealant.hub.SealedHub
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow

class SealedSourceImpl<T>(
    private val hubFactory: SealedHub.Factory
) : SealedSource<T> {

  private val sharedFlow = MutableSharedFlow<T>()

  private val delegate = hubFactory.create(sharedFlow)

  override val hasTerminalState: StateFlow<Boolean> = delegate.hasTerminalState

  override val hasTerminatedProcesses: StateFlow<Boolean> = delegate.hasTerminatedProcesses
  
  override suspend fun emit(value: T) {
    sharedFlow.emit(value)
  }

  override suspend fun createFlow(): SealedFlow<T> = delegate.createFlow()

  override suspend fun <R> createFlow(transformation: suspend (Flow<T>) -> Flow<R>): SealedFlow<R> =
      delegate.createFlow(transformation)

  override suspend fun close() {
    delegate.close()
  }
}
