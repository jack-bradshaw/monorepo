package com.jackbradshaw.oksp.application

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.jackbradshaw.oksp.service.ProcessingService

interface Application {
  suspend fun onCreate(component: ContextComponent)

  suspend fun onDestroy()

  interface ContextComponent {
    fun processingService(): ProcessingService

    fun environment(): SymbolProcessorEnvironment
  }
}
