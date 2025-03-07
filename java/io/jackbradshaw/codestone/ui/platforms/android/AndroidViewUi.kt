package io.jackbradshaw.codestone.ui.platforms.android

import android.view.View

/** An Android UI build with [Views] (https://developer.android.com/reference/android/view/View). */
interface AndroidViewUi : AndroidUi {
  /** Creates the View. */
  fun view(): View
}
