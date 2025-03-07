package io.jackbradshaw.codestone.sustainment.startstop

/**
 * An operation which starts then stops at some point later. Once stopped, never starts again.
 *
 * Caveats:
 * - All calls to [startl and [stopl are non-blocking and return immediately.
 * - Calling [start] more than once is safe and there is no effect after the first call.
 * - Calling [stop] more than once is safe and there is no effect after the first call.
 * - Calling [stopl before calling [start] is safe but the resulting behaviour is undefined.
 */
interface StartStop {
  fun start()

  fun stop()

  fun onStart(listener: (() -> Unit))

  fun onStop(listener: (() -> Unit))

  fun isStarted(): Boolean

  fun isStopped(): Boolean

  fun wasStarted(): Boolean
}
