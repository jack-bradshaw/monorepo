package io.jackbradshaw.queen.foundation.android

import android.content.Intent
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.ui.platforms.android.AndroidUi
import io.jackbradshaw.queen.foundation.Navigator

/** A [Navigator] which moves between [AndroidDestination]s. */
interface AndroidNavigator<A, O : Operation<*>> : Navigator<A, Intent, AndroidUi, AndroidDestination<*>, O>
