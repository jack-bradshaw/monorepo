package io.jackbradshaw.codestone.ui.platforms.android

import androidx.compose.runtime.Composable

/** An Android UI built with [Jetpack Compose](http://developer.android.com/jetpack/compose). */
interface AndroidComposeUi : AndroidUi {
  /** Creates the composition. */
  @Composable fun composable()
}
