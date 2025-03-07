package io.jackbradshaw.codestone.foundation.platforms.android

import android.content.Intent
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.ui.platforms.android.AndroidUi
import io.jackbradshaw.codestone.foundation.Wingsuit

/** A (Wingsuit configured for Android. */
interface AndroidWingsuit : Wingsuit<Intent, AndroidUi, Any, Operation<out Any>>
