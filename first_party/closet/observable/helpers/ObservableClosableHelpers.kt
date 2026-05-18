package com.jackbradshaw.closet.observable.helpers

import com.jackbradshaw.closet.observable.ObservableClosable
import com.jackbradshaw.closet.observable.ObservableClosable.Status
import java.lang.IllegalStateException
import kotlinx.coroutines.flow.first

/** Suspends until this [ObservableClosable] is closed. Resumes immediately if already closed. */
suspend fun ObservableClosable.awaitClosed() {
  this.closureStatus.first { it == Status.CLOSED }
}

/**
 * Suspends until this [ObservableClosable] is closing. Resumes immediately if already closing or
 * closed.
 */
suspend fun ObservableClosable.awaitClosing() {
  this.closureStatus.first { it == Status.CLOSING || it == Status.CLOSED }
}

/**
 * Throws an [IllegalStateException] if this [ObservableClosable] is not open, otherwise returns
 * normally. The exception will contain [notOpenMessage].
 */
fun ObservableClosable.checkOpen(notOpenMessage: String = "This resource is not open.") {
  check(this.closureStatus.value == Status.OPEN) { notOpenMessage }
}
