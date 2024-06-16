package io.jackbradshaw.queen.foundation.platforms.android

import android.content.Intent
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.ui.platforms.android.AndroidUi
import io.jackbradshaw.queen.foundation.Wingsuit

/** A (Wingsuit configured for Android. */
interface AndroidWingsuit : Wingsuit<Intent, AndroidUi, Any, Operation<out Any>>
