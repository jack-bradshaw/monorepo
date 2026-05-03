package com.jackbradshaw.oksp.application

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.jackbradshaw.oksp.service.KspService

interface Application {
  suspend fun onCreate(component: KspComponent)

  suspend fun onDestroy()

  interface KspComponent {
    fun kspService(): KspService

    fun environment(): SymbolProcessorEnvironment
  }
}
