package com.jackbradshaw.codestone.interaction.platforms.android

import android.content.Intent
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.interaction.platforms.android.AndroidUi
import com.jackbradshaw.codestone.interaction.navigator.Navigator

/** A [Navigator] which moves between [AndroidDestination]s. */
interface AndroidNavigator<A, W : Work<*>> : Navigator<A, Intent, AndroidUi, AndroidDestination<*>, W>
