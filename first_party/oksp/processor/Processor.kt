package com.jackbradshaw.oksp.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.jackbradshaw.oksp.service.ProcessingService
import kotlinx.coroutines.flow.SharedFlow

/**
 * Base OKSP processor interface extending [SymbolProcessor].
 */
interface Processor : SymbolProcessor, ProcessingService {
  fun observeTermination(): SharedFlow<Unit>
}