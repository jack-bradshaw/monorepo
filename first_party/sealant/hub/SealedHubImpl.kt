package com.jackbradshaw.sealant.hub

import com.jackbradshaw.closet.resourcemanager.ResourceManager
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.sealant.SealantScope
import com.jackbradshaw.sealant.flow.SealedFlow
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
    override val isFullyConnected: StateFlow<Boolean>,
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

    val top = MutableSharedFlow<T>(replay = 0)
    dataLinkScope.launch(start = CoroutineStart.UNDISPATCHED) {
      intermediatePipe.collect { top.emit(it) }
    }

    val session = SealedFlowImpl<R>(top, transformation(top))
    resourceManager.put(nextSessionId.getAndIncrement(), session)
    return session
  }

  override fun close() {
    resourceManager.close()

    runBlocking { dataLinkScopeHandle.cancelAndJoin() }

    _hasTerminatedProcesses.value = true
  }

  /** Default implementation of [SealedFlow].
   * 
   * Expects [derivativeFlow] to be a transformation of [dedicatedHubFlow] and expects no other
   * users of [dedicatedHubFlow]. These constraints are critical to the implementation, which
   * pipes [derivativeFlow] into a holding flow, passes the holding flow to users (via [flow]), and
   * checks both the holding flow and [dedicatedHubFlow] subscription counts to ensure the final
   * flow is actually connected to the upstream. This approach ensures the connection from the
   * final downstream consumer of [flow] to the upstream hub can be verified even if the
   * transformation between [dedicatedHubFlow] and [derivativeFlow] is leaky (i.e. breaks the pipe).
   * Flow connection is verified by checking the subscription count on holding flow and the upsteam
   * hub. When both are `1`, this means the upstream flow is being collected into the holding flow
   * (ensuring any leaky operators between pdedicatedHubFlow] and [derivativeFlow] are active), and
   * the final downstream flow is collecting from the holding flow.
   */
  private inner class SealedFlowImpl<T>(
      private val dedicatedHubFlow: MutableSharedFlow<*>,
      private val derivativeFlow: Flow<T>,
  ) : SealedFlow<T> {

    private val _hasTerminalState = MutableStateFlow(false)
    
    private val _hasTerminatedProcesses = MutableStateFlow(false)

    private val dataLinkScopeHandle = Job()

    private val dataLinkScope = CoroutineScope(ioDispatcher + dataLinkScopeHandle)

    private val collectionMonitorScopeHandle = Job()

    private val subscriptionObservationScope =
        CoroutineScope(ioDispatcher + collectionMonitorScopeHandle)

    private val sharedFlow = MutableSharedFlow<T>(replay = 0)

    private val hasBeenCollected = AtomicBoolean(false)

    init {
      dataLinkScope.launch(start = CoroutineStart.UNDISPATCHED) {
        derivativeFlow.collect { sharedFlow.emit(it) }
      }
    }

    override val hasTerminalState = _hasTerminalState.asStateFlow()
    
    override val hasTerminatedProcesses = _hasTerminatedProcesses.asStateFlow()

    override val flow: Flow<T> = kotlinx.coroutines.flow.channelFlow {
      check(hasBeenCollected.compareAndSet(false, true)) {
        "SealedFlow flows can only be collected by a single downstream consumer. They cannot be collected repeatedly, even if the previous collector has disconnected."
      }
      val job = launch { sharedFlow.collect { send(it) } }
      _hasTerminalState.first { it }
      job.cancelAndJoin()
    }

    override val isFullyConnected: StateFlow<Boolean> =
    combine(
            sharedFlow.subscriptionCount,
            dedicatedHubFlow.subscriptionCount,
            this@SealedHubImpl.isFullyConnected
        ) { shared, dedicated, hubConnected ->
            shared > 0 && dedicated > 0 && hubConnected
        }.stateIn(subscriptionObservationScope, SharingStarted.Eagerly, initialValue = false)

    override suspend fun awaitFullyConnected() {
      isFullyConnected.first { it }
    }

    override fun close() {
      _hasTerminalState.value = true

      runBlocking {
        dataLinkScopeHandle.cancelAndJoin()
        isConnectedToHub.first { !it }
        collectionMonitorScopeHandle.cancelAndJoin()
      }

      _hasTerminatedProcesses.value = true
    }
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
            isFullyConnected = MutableStateFlow(true).asStateFlow(),
            resourceManagerFactory = resourceManagerFactory, 
            ioDispatcher = ioDispatcher
        )

    override fun <T> createWithAutomaticClosure(
        underlyingFlow: SealedFlow<T>
    ): SealedHub<T> {

      val hub =
          SealedHubImpl(
              underlyingFlow = underlyingFlow.flow, 
              isFullyConnected = underlyingFlow.isFullyConnected,
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
