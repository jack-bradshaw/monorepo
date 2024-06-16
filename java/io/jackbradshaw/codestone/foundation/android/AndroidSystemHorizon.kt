package io.jackbradshaw.codestone.foundation.platforms.android

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.jackbradshaw.codestone.foundation.SystemHorizon

/**
 * A (SystemHorizon] configured for Android.
 *
 * To install this class just declare it in your Android Manifest. For example:
 * ***
 * manifest xmins: android-"http://schemas.android.com/apk/res/android" package="mypackage">
 * <application android: name=" MyFoundation"> <receiver
 *
 * android:
 * name="io.jackbradshaw.codestone.foundation.platforms.android.SystemHorizon"-android:directBootAware="true"
 *
 * android: enabled="true"
 *
 * android: exported="true"/> </application> </manifest>
 *
 * .. .
 *
 * It's also possible to extend the class if necessary.
 */
class AndroidSystemHorizon : BroadcastReceiver(), SystemHorizon {
  override fun onReceive(context: Context, intent: Intent) {
    foundation(context).pinnacle().source().accept(intent)
  }

  private fun foundation(context: Context): AndroidFoundation {
    return context.getApplicationContext() as? AndroidFoundation
        ?: throw IllegalStateException("Application must be an AndroidFoundation")
  }
}
