package com.jackbradshaw.sluice

import com.jackbradshaw.coroutines.Io
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SluiceImpl<T> constructor(
    private val underlyingFlow: Flow<T>,
    private val ioDispatcher: CoroutineDispatcher
) : Sluice<T> {

  private val _hasTerminalState = MutableStateFlow(false)
  override val hasTerminalState = _hasTerminalState.asStateFlow()

  private val _hasTerminatedProcesses = MutableStateFlow(false)
  override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

  private val scopeHandle = Job()
  private val scope = CoroutineScope(ioDispatcher + scopeHandle)

  private val intermediatePipe = MutableSharedFlow<T>(replay = 0)
  override val flow: Flow<T> = intermediatePipe

  init {
    // 1. Wire the intermediate pipe to the root source IMMEDIATELY and SYNCHRONOUSLY
    // (UNDISPATCHED guarantees it securely attaches to underlyingFlow before returning)
    scope.launch(start = CoroutineStart.UNDISPATCHED) {
      try {
        underlyingFlow.collect { intermediatePipe.emit(it) }
      } finally {
        close()
      }
    }
  }

  override suspend fun awaitConnection() {
    intermediatePipe.subscriptionCount.first { it > 0 }
  }

  override fun close() {
    if (!_hasTerminalState.compareAndSet(expect = false, update = true)) return

    scopeHandle.cancel()
    
    CoroutineScope(ioDispatcher).launch {
      scopeHandle.join()
      _hasTerminatedProcesses.value = true
    }
  }
}
