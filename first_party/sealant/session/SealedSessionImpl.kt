package com.jackbradshaw.sealant.session

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.helpers.awaitClosing
import com.jackbradshaw.closet.observable.standard.StandardObservableClosableFactory
import com.jackbradshaw.coroutines.Io
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** Default implementation of [SealedSession]. */
class SealedSessionImpl<T, R>(
    private val source: Flow<T>,
    private val transformation: suspend (Flow<T>) -> Flow<R>,
    private val ioDispatcher: CoroutineDispatcher,
    private val standardObservableClosableFactory: StandardObservableClosableFactory
) : SealedSession<R> {

  /** Whether [source] is being collected. */
  private val _isConnectedToSource = MutableStateFlow(false)

  /** Whether [flow] was collected previously. True if still presently being collected. */
  private val hasBeenCollected = AtomicBoolean(false)

  /** Standard closable delegate. */
  private lateinit var closable: ObservableClosable

  override val closureStatus
    get() = closable.closureStatus

  override val isConnectedToSource = _isConnectedToSource

  override val flow: Flow<R> = flow {
    check(hasBeenCollected.compareAndSet(false, true)) {
      "SealedSession flows can only be collected by a single downstream consumer. They cannot be collected repeatedly, even if the previous collector has disconnected."
    }

    /*
     * Using a channel flow to pull from source ensures the _isConnectedToSource is set when the
     * collection call has actually passed through the transform and begun collecting from the
     * source.
     */
    val fromSource = channelFlow {
      /*
       * Using undispatched ensures launch suspends until collect beins, although, strictly speaking, it
       * only guarantees that it suspends until the first suspending point in the coroutine, but the
       * broader architecture of sealant ensures the first suspending point is the collection. This
       * is guaranteed by applying the transformation downstream of this channel flow and ensuring
       * the hub provides a simple flow (e.g. a MSF) for the source. The implementation
       * of the hub is critical here, and this assumption will not hold if hub impl passes in a complex
       * flow with a suspending operator between the ultimate upstream and this session..
       */
      val forward = launch(start = CoroutineStart.UNDISPATCHED) { source.collect { send(it) } }
      _isConnectedToSource.value = true

      try {
        closable.awaitClosing() // Wait until closure is initiated
      } finally {
        /*
         * When the downstream flow collection job cancels, a cancellation exception will be thrown
         * from the suspending point (the await closing), and finally will be
         * entered. Since the coroutine is cancelled, NonCancellable is required to ensure the
         * cancel and join can suspend while the inner forward call is cancelling (instead of
         * immediately throwing its own cancellation exception).
         */
        withContext(NonCancellable) {
          forward.cancelAndJoin()
          _isConnectedToSource.value = false
        }
      }
    }

    try {
      transformation(fromSource).collect { emit(it) }
    } finally {
      /*
       * Similar to above, using NonCancellable ensures `close()` can suspend until closure is
       * complete (instead of immediately throwing a cancellation exception).
       */
      withContext(NonCancellable) { closable.close() }
    }
  }

  override suspend fun close() = closable.close()

  override suspend fun awaitConnectionToSource() {
    _isConnectedToSource.first { it }
  }

  internal suspend fun initialize() {
    closable =
        standardObservableClosableFactory.createStandardClosable {
          _isConnectedToSource.first { !it }
        }
  }

  class FactoryImpl
  @Inject
  internal constructor(
      @Io private val ioDispatcher: CoroutineDispatcher,
      private val standardObservableClosableFactory: StandardObservableClosableFactory
  ) : SealedSession.Factory {

    override suspend fun <T, R> create(
        source: Flow<T>,
        transformation: suspend (Flow<T>) -> Flow<R>
    ) =
        SealedSessionImpl(source, transformation, ioDispatcher, standardObservableClosableFactory)
            .apply { initialize() }
  }
}
