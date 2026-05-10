package com.jackbradshaw.sealant.flow

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicBoolean

class SealedFlowImpl<T, R>(
    private val source: Flow<T>,
    private val transformation: suspend (Flow<T>) -> Flow<R>,
    private val ioDispatcher: CoroutineDispatcher
) : SealedFlow<R> {

  private val _hasTerminalState = MutableStateFlow(false)

  private val _hasTerminatedProcesses = MutableStateFlow(false)

  private val _isUpstreamConnected = MutableStateFlow(false)

  private val hasBeenCollected = AtomicBoolean(false)

  override val hasTerminalState = _hasTerminalState.asStateFlow()

  override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

  override val isConnectedToHub: StateFlow<Boolean> = _isUpstreamConnected.asStateFlow()

  override val flow: Flow<R> = flow {
    check(hasBeenCollected.compareAndSet(false, true)) {
      "SealedFlow flows can only be collected by a single downstream consumer. They cannot be collected repeatedly, even if the previous collector has disconnected."
    }

    val fromSource = channelFlow {
      _isUpstreamConnected.value = true
      val forward = launch { source.collect { send(it) } }
      
      try {
        _hasTerminalState.first { it }
      } finally {
        forward.cancelAndJoin()
        _isUpstreamConnected.value = false
      }
    }

    val transformedFromSource = transformation(fromSource)

    try {
      transformedFromSource.collect { emit(it) }
    } finally {
      if (!_hasTerminalState.value) {
        CoroutineScope(ioDispatcher).launch { this@SealedFlowImpl.close() }
      }
    }
  }

  override suspend fun awaitConnectionToHub() {
    isConnectedToHub.first { it }
  }

  override suspend fun close() {
    if (_hasTerminalState.value) return
    _hasTerminalState.value = true

    isConnectedToHub.first { !it }
    _hasTerminatedProcesses.value = true
  }
}
