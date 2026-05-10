package com.jackbradshaw.closet.observable

/**
 * An object that may hold resources or processes until it is closed.
 * The [close] method suspends until the object is fully closed.
 */
interface SuspendableClosable {
  suspend fun close()
}
