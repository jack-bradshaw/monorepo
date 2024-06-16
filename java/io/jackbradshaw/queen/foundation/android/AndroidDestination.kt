package io.jackbradshaw.queen.foundation.android

import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.ui.platforms.android.AndroidUi
import io.jackbradshaw.queen.foundation.Destination

/** A screen in an Android application. */
interface AndroidDestination<O : Operation<*>> : Destination<AndroidUi, O>
