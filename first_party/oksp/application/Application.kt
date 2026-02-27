package com.jackbradshaw.oksp.application

import com.jackbradshaw.oksp.application.ApplicationComponent

interface Application {
  suspend fun onCreate(component: ApplicationComponent)

  suspend fun onDestroy()
}