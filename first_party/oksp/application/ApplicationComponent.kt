package com.jackbradshaw.oksp.application

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.jackbradshaw.oksp.service.ProcessingService

interface ApplicationComponent {
  fun processingService(): ProcessingService
  fun environment(): SymbolProcessorEnvironment
}
