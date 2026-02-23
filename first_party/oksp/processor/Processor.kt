package com.jackbradshaw.oksp.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.jackbradshaw.oksp.services.LifecycleService
import com.jackbradshaw.oksp.services.ProcessingService

/**
 * Base OKSP processor interface extending [SymbolProcessor].
 */
interface Processor : SymbolProcessor , ProcessingService, LifecycleService