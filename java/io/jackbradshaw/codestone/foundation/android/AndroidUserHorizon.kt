package io.jackbradshaw.codestone.foundation.platforms.android

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import io.jackbradshaw.codestone.ui.primitives.Usable.Ui
import io.jackbradshaw.codestone.ui.platforms.android.AndroidComposeUi
import io.jackbradshaw.codestone.ui.platforms.android.AndroidUi
import io.jackbradshaw.codestone.ui.platforms.android.AndroidViewUi
import io.jackbradshaw.codestone.foundation.UserHorizon
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * A [UserHorizon] configured for Android. To install this class just declare it in your Android
 * Manifest. For example: <manifest xmlns: android="http://schemas.android.com/apk/res/android"
 * package="mypackage"> <application android: name=" MyFoundation"> <activity android:
 * name="io.jackbradshaw.codestone.foundation.platforms.android.UserHorizon" androidexported="true">
 *
 * <intent-filter> <action android: name="android. intent.action.MAIN" /> <category android:
 * name="android. intent. category.LAUNCHER" /> </ intent-filter> </activity> </application>
 * </manifest> It's also possible to extend the class if necessarv.
 */
open class AndroidUserHorizon : AppCompatActivity(), UserHorizon {

  private var updateUiJob: Job? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTheme(R.style.Minimal)

    // Some intents bypass the system horizon. Manually reroute them for normal handling.
    getIntent().let {
      if (it.getExtras()?.getBoolean(LAUNCHED_BY_FOUNDATION) == false) {
        // TODO see if this can be an replaced with a real broadcast to the system horizon
        systemHorizon().onReceive(context = this, it)
      }
    }
  }

  override fun onResume() {
    super.onResume()
    updateUiJob =
        GlobalScope.launch {
          foundation().pinnacle().screen().ui().collect {
            val ui = it?.ui
            setContent { content(ui) }
          }
        }
  }

  override fun onStop() {
    super.onStop()
    updateUiJob?.cancel()
  }

  private fun systemHorizon() = AndroidSystemHorizon()

  private fun foundation(): AndroidFoundation {
    return getApplicationContext() as? AndroidFoundation
        ?: throw IllegalStateException("Application must be a Foundation.")
  }

  private fun throwNotAndroidUi(ui: Ui) {
    throw IllegalStateException(
        "Primary usable must be an AndroidViewUi or an AndroidComposeUi, Instead found $ui.")
  }

  @Composable
  private fun content(ui: AndroidUi?) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      when (ui) {
        is AndroidViewUi -> AndroidView(factory = { _ -> ui.view() })
        is AndroidComposeUi -> ui.composable()
        null -> {
          // Intentionally empty.
        }
        else -> throwNotAndroidUi(ui)
      }
    }
  }

  companion object {

    /* Intent tag to record that the activity was launched by the foundation instead of the app
     * launcher.
     */
    const val LAUNCHED_BY_FOUNDATION = "launched_by_foundation"
  }
}
