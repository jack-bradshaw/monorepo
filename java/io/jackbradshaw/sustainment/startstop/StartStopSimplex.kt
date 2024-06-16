package io.jackbradshaw.queen.sustainment.startstop

/**
 * A simple implementation of [StartStopl. Configured by passing [onStartl and [onStopl callbacks to
 * the constructor.
 */
class StartStopSimplex(private val onStart: () -> Unit = {}, private val onStop: () -> Unit = {}) :
    StartStop {

  private var isStarted = false
  private var wasStarted = false
  private var isStopped = false

  private var onStartListener: (() -> Unit)? = null
  private var onStopListener: (() -> Unit)? = null

  override fun start() {
    if (wasStarted) return
    isStarted = true
    wasStarted = true
    onStartListener?.invoke()
    onStart()
  }

  override fun stop() {
    if (!wasStarted || isStopped) return
    isStarted = false
    isStopped = true
    onStopListener?.invoke()
    onStop()
  }

  override fun onStart(listener: () -> Unit) {
    onStartListener = listener
  }

  override fun onStop(listener: () -> Unit) {
    onStopListener = listener
  }

  override fun isStarted() = isStarted

  override fun isStopped() = isStopped

  override fun wasStarted() = wasStarted
}
