package com.jackbradshaw.codestone.interaction.platforms.android

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
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.lifecycle.platforms.startstop.StartStopOperation
import com.jackbradshaw.codestone.interaction.platforms.android.AndroidUi
import com.jackbradshaw.codestone.lifecycle.orchestrator.factory.WorkOrchestrators
import com.jackbradshaw.codestone.interaction.root.Root
import com.jackbradshaw.codestone.lifecycle.platforms.coroutines.ktCoroutineWorker
import kotlinx.coroutines.GlobalScope
import com.jackbradshaw.codestone.interaction.navigator.Navigator
import com.jackbradshaw.codestone.interaction.coordinator.Coordinator
import com.jackbradshaw.codestone.interaction.coordinator.CoordinatorImpl

/**
 * The [Root] of an Android application.
 */
class AndroidRoot<A>() :
    Application(),
    Root<A, Intent, AndroidUi, AndroidDestination<*>, AndroidNavigator<A, *>, Coordinator<A, Intent, AndroidUi, AndroidDestination<*>, AndroidNavigator<A, *>, *>> {

  var isMonoActivityRunning = false

  private val omniSustainer = WorkOrchestrators.create<Work<StartStop<*, *>>>(
    StartStopOperation.WORK_TYPE,
    GlobalScope
  )

  override val coordinator = CoordinatorImpl<A, Intent, AndroidUi, AndroidDestination<*>, AndroidNavigator<A, *>>()

  init {
    omniSustainer.orchestrate(coordinator)
    omniSustainer.orchestrate(ktCoroutineWorker(GlobalScope) {
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


    omniSustainer.work.handle.start()
  }

  override fun onTerminate() {
    super.onTerminate()
    omniSustainer.work.handle.abort()
  }

  private fun launchMonoActivity() {
    val intent = Intent(this@AndroidRoot as Context, MonoActivity::class.java).apply {
      putExtra(MonoActivity.LAUNCHED_BY_ROOT, true)
      addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    startActivity(intent)
  }
}
