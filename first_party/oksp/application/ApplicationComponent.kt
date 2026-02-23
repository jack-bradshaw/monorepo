package com.jackbradshaw.oksp.component

import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.jackbradshaw.oksp.service.ProcessingService

interface OkspComponent {
  fun processingService(): ProcessingService
  fun environment(): SymbolProcessorEnvironment
}
