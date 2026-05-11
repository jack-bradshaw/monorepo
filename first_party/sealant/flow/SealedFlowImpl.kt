package com.jackbradshaw.sealant.flow

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Default implementation of [SealedFlow].
 */
class SealedFlowImpl<T, R>(
    private val source: Flow<T>,
    private val transformation: suspend (Flow<T>) -> Flow<R>,
    private val ioDispatcher: CoroutineDispatcher
) : SealedFlow<R> {

  private val hasStartedClosing = MutableStateFlow(false)

  private val hasFinishedClosing = MutableStateFlow(false)

  private val _isConnectedToSource = MutableStateFlow(false)

  private val hasBeenCollected = AtomicBoolean(false)

  override val hasTerminalState = hasStartedClosing.asStateFlow()

  override val hasTerminatedProcesses = hasFinishedClosing.asStateFlow()

  override val isConnectedToSource = _isConnectedToSource

  override val flow: Flow<R> = flow {
    check(hasBeenCollected.compareAndSet(false, true)) {
      "SealedFlow flows can only be collected by a single downstream consumer. They cannot be collected repeatedly, even if the previous collector has disconnected."
    }

    /*
     * Using a channel flow to pull from source ensures the flag is not set until collection has
     * actually passed through the transform and any downstream code, and has actually reached the
     * source.
     */
    val fromSource = channelFlow {
      _isConnectedToSource.value = true
      val forward = launch { source.collect { send(it) } }
      try {
        hasStartedClosing.first { it }
      } finally {
        /* 
         * Combining finally with NonCancellable ensures tear down runs properly even after
         * coroutine cancellation. When the downstream flow collector cancels, a cancellation
         * exception will be thrown, finally will be entered, and the non-cancellable scope will
         * ensure the `join` call does not immediately throw its own cancellation exception.
         */
        withContext(NonCancellable) {
          forward.join()
          _isConnectedToSource.value = false
        }
        
      }
    }

    try {
      transformation(fromSource).collect { emit(it) }
    } finally {
      hasStartedClosing.value = true
      
      /* 
       * Similar to above, using NonCancellable ensures `first` actually suspends and waits for
       * the channelFlow to finish before setting the closure flag.
       */
      withContext(NonCancellable) {
          _isConnectedToSource.first { !it }
          hasFinishedClosing.value = true
      }
    }
  }

  override suspend fun awaitConnectionToSource() {
    _isConnectedToSource.first { it }
  }

  override suspend fun close() {
    if (hasStartedClosing.value) return
    hasStartedClosing.value = true
    _isConnectedToSource.first { !it }
    hasFinishedClosing.value = true
  }
}
