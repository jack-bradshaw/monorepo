package io.jackbradshaw.queen.foundation.android

import android.app.Application
import android.content.Context
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import android.app.ActivityManager
import android.app.Activity
import android.os.Bundle

import kotlinx.coroutines.flow.takeWhile
import android.content.Intent
import io.jackbradshaw.sustainment.primitives.Sustainable.Operation
import io.jackbradshaw.queen.sustainment.startstop.StartStop
import io.jackbradshaw.queen.sustainment.operations.StartStopOperation
import io.jackbradshaw.queen.ui.platforms.android.AndroidUi
import io.jackbradshaw.queen.sustainment.omnisustainer.factoring.OmniSustainers
import io.jackbradshaw.queen.foundation.Root
import io.jackbradshaw.queen.sustainment.operations.ktCoroutineSustainable
import kotlinx.coroutines.GlobalScope
import io.jackbradshaw.queen.foundation.Navigator
import io.jackbradshaw.queen.foundation.Coordinator
import io.jackbradshaw.queen.foundation.omniform.CoordinatorImpl

/**
 * The [Root] of an Android application.
 */
class AndroidRoot<A>() :
    Application(),
    Root<A, Intent, AndroidUi, AndroidDestination<*>, AndroidNavigator<A, *>, Coordinator<A, Intent, AndroidUi, AndroidDestination<*>, AndroidNavigator<A, *>, *>> {

  var isMonoActivityRunning = false

  private var work: StartStop? = null

  private val omniSustainer = OmniSustainers.create<StartStopOperation>(
    StartStopOperation.WORK_TYPE
  )

  override val coordinator = CoordinatorImpl<A, Intent, AndroidUi, AndroidDestination<*>, AndroidNavigator<A, *>>()

  init {
    omniSustainer.sustain(coordinator)
    omniSustainer.sustain(ktCoroutineSustainable(GlobalScope) {
      coordinator.destination.filterNotNull()
      .onEach { if (!isMonoActivityRunning) launchMonoActivity() }.collect()
    })
  }

  override fun onCreate() {
    super.onCreate()

    registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity::class == MonoActivity::class) isMonoActivityRunning = true
      }

      override fun onActivityStarted(activity: Activity) {
          
      }

      override fun onActivityResumed(activity: Activity) {
          
      }

      override fun onActivityPaused(activity: Activity) {
          
      }

      override fun onActivityStopped(activity: Activity) {
          
      }

      override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
          
      }

      override fun onActivityDestroyed(activity: Activity) {
        if (activity::class == MonoActivity::class) isMonoActivityRunning = false
          
      }
  })


    work = omniSustainer.operation.work()
    work?.start()
  }

  override fun onTerminate() {
    super.onTerminate()
    work?.stop()
  }

  private fun launchMonoActivity() {
    val intent = Intent(this@AndroidRoot as Context, MonoActivity::class.java).apply {
      putExtra(MonoActivity.LAUNCHED_BY_ROOT, true)
      addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
  }
}
