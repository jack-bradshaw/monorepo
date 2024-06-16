package io.jackbradshaw.queen.ui.platforms.android

import android.view.View
import android.content.Context

/** An UI which uses Views(https://developer.android.com/reference/android/view/View) */
interface AndroidViewUi : AndroidUi {
  /** Creates the View. */
  fun view(context: Context): View
}