package com.jackbradshaw.closet.observable.standard

import com.jackbradshaw.closet.ClosetScope
import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosable.Status
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/** Default implementation of [StandardObservableClosableFactory]. */
@ClosetScope
class StandardObservableClosableFactoryImpl @Inject internal constructor() :
    StandardObservableClosableFactory {

  override suspend fun createStandardClosable(closure: suspend ObservableClosable.() -> Unit) =
      object : ObservableClosable {

        private val closeMutex = Mutex()

        private val _closureStatus = MutableStateFlow(Status.OPEN)

        override val closureStatus = _closureStatus.asStateFlow()

        override suspend fun close() {
          closeMutex.withLock {
            if (_closureStatus.value != Status.CLOSED) {
              _closureStatus.value = Status.CLOSING
              closure()
              _closureStatus.value = Status.CLOSED
            }
          }
        }
      }
}
