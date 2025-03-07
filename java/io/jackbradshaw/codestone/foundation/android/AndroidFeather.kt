package io.jackbradshaw.codestone.foundation.platforms.android

import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.ui.platforms.android.AndroidUi
import io.jackbradshaw.codestone.foundation.Feather

/** A [Feather] configured for Android. */
interface AndroidFeather : Feather<AndroidUi, Any, Operation<out Any>>
