package com.jackbradshaw.codestone.interaction.platforms.android

import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.interaction.platforms.android.AndroidUi
import com.jackbradshaw.codestone.interaction.destination.Destination

/** A screen in an Android application. */
interface AndroidDestination<W : Work<*>> : Destination<AndroidUi, W>
