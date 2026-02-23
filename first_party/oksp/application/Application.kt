package com.jackbradshaw.oksp.application

import com.jackbradshaw.oksp.component.OkspComponent

interface Application {
  suspend fun onCreate(component: OkspComponent)

  suspend fun onDestroy()
}