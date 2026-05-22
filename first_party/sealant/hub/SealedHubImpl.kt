package com.jackbradshaw.sealant.hub

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.helpers.awaitClosed
import com.jackbradshaw.closet.observable.helpers.checkOpen
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableFactory
import com.jackbradshaw.closet.resourcemanager.set.ResourceSet
import com.jackbradshaw.coroutines.Io
import com.jackbradshaw.sealant.SealantScope
import com.jackbradshaw.sealant.session.SealedSession
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class SealedHubImpl<T>(
    upstreamFlow: Flow<T>,
    private val ioDispatcher: CoroutineDispatcher,
    private val sealedSessionFactory: SealedSession.Factory,
    private val standardObservableClosableFactory: StandardObservableClosableFactory,
    private val resourceSetFactory: ResourceSet.Factory
) : SealedHub<T> {

  /** Accepts data from [upstreamFlow] and forwards it to each session. */
  private val intermediatePipe = MutableSharedFlow<T>(replay = 0)

  /** Handle for cancelling [dataLinkScope]. */
  private val dataLinkScopeHandle = Job()

  /** Scope that runs the job to forward [upstreamFlow] to [intermediatePipe]. */
  private val dataLinkScope = CoroutineScope(dataLinkScopeHandle + ioDispatcher)

  /** Standard delegate for closure operations. */
  private lateinit var standardClosable: ObservableClosable

  /** All open sessions created by this hub. */
  private lateinit var sessions: ResourceSet<SealedSession<*>>

  override val closureStatus
    get() = standardClosable.closureStatus

  init {
    /*
     * Upstream collection must begin before init returns, but this is the exact problem sealant
     * exists to solve, which creates a circular problem. Using UNDISPATCHED here will only wait
     * until the first suspension point is reached, so external users will have to ensure they do
     * not pass in flows that suspend. This is well documented in various places.
     */
    dataLinkScope.launch(start = CoroutineStart.UNDISPATCHED) {
      upstreamFlow.collect { intermediatePipe.emit(it) }
    }
  }

  override suspend fun createSession(): SealedSession<T> = createSession { it }

  override suspend fun <R> createSession(
      transformation: suspend (Flow<T>) -> Flow<R>
  ): SealedSession<R> {
    checkOpen("This hub is closed. Cannot open sessions after closure.")

    return sealedSessionFactory.create(intermediatePipe, transformation).also { sessions.add(it) }
  }

  override suspend fun close() = standardClosable.close()

  internal suspend fun initialize() {
    sessions = resourceSetFactory.createResourceSet<SealedSession<*>>()
    standardClosable =
        standardObservableClosableFactory.createStandardClosable {
          sessions.close()
          dataLinkScopeHandle.cancelAndJoin()
        }
  }

  /**
   * Default implementation of [SealantHub.Factory].
   *
   * The implementation of [createWithAutomaticClosure] uses two launched coroutines to handle
   * automatic closure: One to observe upstream closure and propagate it downstream (i.e. close the
   * returned hub when `upstreamFlow` is closed) and another to cancel the first job after closure
   * to avoid resource leaks.
   */
  @SealantScope
  class Factory
  @Inject
  internal constructor(
      private val resourceSetFactory: ResourceSet.Factory,
      private val standardObservableClosableFactory: StandardObservableClosableFactory,
      private val sealedSessionFactory: SealedSession.Factory,
      @Io private val ioDispatcher: CoroutineDispatcher
  ) : SealedHub.Factory {

    override suspend fun <T> create(upstreamFlow: Flow<T>): SealedHub<T> {
      return SealedHubImpl(
              upstreamFlow,
              ioDispatcher,
              sealedSessionFactory,
              standardObservableClosableFactory,
              resourceSetFactory)
          .apply { initialize() }
    }

    override suspend fun <T> createWithAutomaticClosure(
        upstreamFlow: SealedSession<T>
    ): SealedHub<T> {

      val hub = create(upstreamFlow.flow)

      val scopeHandle = Job()
      val scope = CoroutineScope(ioDispatcher + scopeHandle)

      // Downwards closure propagation: Closing upstream flow closes hub.
      scope.launch {
        upstreamFlow.awaitClosed()
        hub.close()
      }

      // Resource-leak prevention: Closing hub ends session observation job.
      scope.launch {
        hub.awaitClosed()
        scopeHandle.cancel()
      }

      return hub
    }
  }
}
