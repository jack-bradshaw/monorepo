package com.jackbradshaw.sealant.hub

import com.jackbradshaw.closet.resourcemanager.ResourceManager
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.sealant.SealantScope
import com.jackbradshaw.sealant.flow.SealedFlow
import com.jackbradshaw.sealant.flow.SealedFlowImpl
import jakarta.inject.Inject
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SealedHubImpl<T>
constructor(
    private val underlyingFlow: Flow<T>,
    private val resourceManagerFactory: ResourceManager.Factory,
    private val ioDispatcher: CoroutineDispatcher
) : SealedHub<T> {

  /**
   * [ResourceManager] operates as a key-value map, but only set functionaity is needed, so the keys
   * are just thread-safe auto-incremented values.
   */
  private val nextSessionId = AtomicInteger(0)

  /** Stores all sessions for automatic closure. */
  private val resourceManager =
      resourceManagerFactory.createResourceManager<Int, SealedFlow<*>>()

  private val _hasTerminatedProcesses = MutableStateFlow(false)

  /** Handle for controlling [dataLinkScope]. */
  private val dataLinkScopeHandle = Job()

  /** The scope that runs the logic to pipe data from [underlyingFlow] to each session */
  private val dataLinkScope = CoroutineScope(ioDispatcher + dataLinkScopeHandle)

  /** Accepts data from [underlyingFlow] and forwards it to each session. */
  private val intermediatePipe = MutableSharedFlow<T>(replay = 0)

  init {
    /* Using UNDISPATCHED avoids leaks by ensuring init waits until collect is active.
     * This is only reliable here because the body of the coroutine is minimal and there are no
     * suspending points before `collect` (assuming upstream pipe has no leaks). */
    dataLinkScope.launch(start = CoroutineStart.UNDISPATCHED) {
      underlyingFlow.collect { intermediatePipe.emit(it) }
    }
  }

  override val hasTerminalState = resourceManager.hasTerminalState

  override val hasTerminatedProcesses = _hasTerminatedProcesses

  override suspend fun createFlow(): SealedFlow<T> = createFlow { it }

  override suspend fun <R> createFlow(transformation: suspend (Flow<T>) -> Flow<R>): SealedFlow<R> {
    check(!hasTerminalState.value) { "This hub is closed. Cannot open flows after closure." }

    val sessionId = nextSessionId.getAndIncrement()
    val session = SealedFlowImpl(
        source = intermediatePipe,
        transformation = transformation,
        ioDispatcher = ioDispatcher
    )
    
    resourceManager.put(sessionId, session)
    return session
  }

  override suspend fun close() {
    resourceManager.close()
    dataLinkScopeHandle.cancelAndJoin()
    _hasTerminatedProcesses.value = true
  }

  /** Default implementation of [SealantHub.Factory].
   * 
   * The implmentation of [createWithAutomaticClosure] uses two launched coroutines to handle
   * automatic closure: One to observe upstream closure and propagate it downstream (i.e. close the
   * returned hub when `underlyingFlow` is closed) and another to cancel the first job after closure
   * to avoid reasource leaks.
   */
  @SealantScope
  class Factory
  @Inject
  internal constructor(
      private val resourceManagerFactory: ResourceManager.Factory,
      @Io private val ioDispatcher: CoroutineDispatcher
  ) : SealedHub.Factory {

    override fun <T> create(underlyingFlow: Flow<T>): SealedHub<T> =
        SealedHubImpl(
            underlyingFlow = underlyingFlow, 
            resourceManagerFactory = resourceManagerFactory, 
            ioDispatcher = ioDispatcher
        )

    override fun <T> createWithAutomaticClosure(
        underlyingFlow: SealedFlow<T>
    ): SealedHub<T> {

      val hub =
          SealedHubImpl(
              underlyingFlow = underlyingFlow.flow, 
              resourceManagerFactory = resourceManagerFactory, 
              ioDispatcher = ioDispatcher
          )

      val scopeHandle = Job()
      val scope = CoroutineScope(ioDispatcher + scopeHandle)

      // Downwards closure propagation: Closing underlying flow closes hub.
      scope.launch {
        underlyingFlow.hasTerminatedProcesses.first { it }
        hub.close()
      }

      // Resource leak prevention: Closing hub ends session observation job.
      scope.launch {
        hub.hasTerminatedProcesses.first { it }
        scopeHandle.cancel()
      }

      return hub
    }
  }
}
