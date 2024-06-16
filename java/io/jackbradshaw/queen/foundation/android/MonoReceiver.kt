package io.jackbradshaw.queen.foundation.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Receives enironmental signals and routes the to the [Coordinator] hosted by [AndroidRoot].
 */
class MonoReceiver : BroadcastReceiver() {
  override fun onReceive(context: Context, intent: Intent) {
    val root = context.getApplicationContext()
    check(root is AndroidRoot<*>) {
      "Application must be an AndroidRoot"
    }
    root.coordinator.onSignalFromEnvironment(intent)
  }

  companion object {
    /** The action associated with [MonoReceiver]. */
    const val ACTION = "io.jackbradshaw.queen.foundation.android.MonoReceiver.ACTION"
  }
}
