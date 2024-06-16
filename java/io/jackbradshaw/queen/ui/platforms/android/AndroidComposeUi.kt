package io.jackbradshaw.queen.ui.platforms.android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Composer

/** An Android UI built with [Jetpack Compose](http://developer.android.com/jetpack/compose). */
interface AndroidComposeUi : AndroidUi {
  /** Creates the composition. */
  @Composable fun composition()
}
