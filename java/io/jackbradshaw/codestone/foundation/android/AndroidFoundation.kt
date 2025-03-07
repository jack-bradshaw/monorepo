package io.jackbradshaw.codestone.foundation.platforms.android

import android.app.Application
import android.content.Context
import android.content.Intent
import io.jackbradshaw.codestone.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.codestone.sustainment.startstop.StartStop
import io.jackbradshaw.codestone.ui.platforms.android.AndroidUi
import io.jackbradshaw.codestone.foundation.Coordinator
import io.jackbradshaw.codestone.foundation.Foundation
import io.jackbradshaw.codestone.foundation.Pinnacle
import io.jackbradshaw.codestone.foundation.Screen
import io.jackbradshaw.codestone.foundation.Source
import io.jackbradshaw.codestone.foundation.omniform.OmniCoordinator
import io.jackbradshaw.codestone.foundation.omniform.OmniPinnacle
import io.jackbradshaw.codestone.foundation.omniform.OmniScreen
import io.jackbradshaw.codestone.foundation.omniform.OmniSource

/**
 * A [Foundation] configured for Android.
 *
 * To use this class, create an application class which extends from it and:
 * - Pass in a Wingsuit creation lambda.
 * - Declare the class in your Android Manifest.
 *
 * For example:
 *
 * class MyFoundation: AndroidFoundation({ _, _, _, _ -> MyWingsuit() })
 *
 * <manifest xmlns:android="http://schemas.android.com/apk/res/android" package="mypackage">
 *
 * <application android:name="MyFoundation" /> </manifest>
 *
 * Codestone handles the rest.
 */
abstract class AndroidFoundation(
    private val initialWingsuit:
        (
            context: Context?,
            source: Source<Intent>,
            coordinator: Coordinator<Intent, AndroidUi, Any, Operation<out Any>, AndroidWingsuit>,
            screen: Screen<AndroidUi>) -> AndroidWingsuit
) :
    Application(),
    Foundation<
        Intent,
        AndroidUi,
        Any,
        Operation<out Any>,
        AndroidWingsuit,
        Source<Intent>,
        Coordinator<Intent, AndroidUi, Any, Operation<out Any>, AndroidWingsuit>,
        Screen<AndroidUi>> {

  private val _pinnacle by lazy { createPinnacle() }

  override fun pinnacle() = _pinnacle

  private fun createPinnacle():
      Pinnacle<
          Intent,
          AndroidUi,
          Any,
          Operation<out Any>,
          AndroidWingsuit,
          Source<Intent>,
          Coordinator<Intent, AndroidUi, Any, Operation<out Any>, AndroidWingsuit>,
          Screen<AndroidUi>> {
    return OmniPinnacle<
        Context,
        Intent,
        AndroidUi,
        Any,
        Operation<out Any>,
        AndroidWingsuit,
        Source<Intent>,
        Coordinator<Intent, AndroidUi, Any, Operation<out Any>, AndroidWingsuit>,
        Screen<AndroidUi>>(
        godBuilder = { this },
        foundationBuilder = initialWingsuit,
        sourceBuilder = { OmniSource() },
        coordinatorBuilder = { source: Source<Intent>, screen: Screen<AndroidUi> ->
          OmniCoordinator(source, screen)
        },
        screenBuilder = { OmniScreen() },
        userHorizonLauncher = {
          this@AndroidFoundation.startActivity(
              Intent(this@AndroidFoundation as Context, AndroidUserHorizon::class.java).apply {
                putExtra(AndroidUserHorizon.LAUNCHED_BY_FOUNDATION, true)
              })
        })
  }

  private var pinnacleOperation: StartStop? = null

  override fun onCreate() {
    super.onCreate()
    pinnacle().operation.work().also { pinnacleOperation = it }.start()
  }

  override fun onTerminate() {
    super.onTerminate()
    pinnacleOperation?.stop()
  }
}
