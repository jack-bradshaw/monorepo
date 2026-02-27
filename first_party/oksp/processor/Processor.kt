package com.jackbradshaw.oksp.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.jackbradshaw.oksp.service.ProcessingService
import kotlinx.coroutines.flow.SharedFlow

/**
 * Base OKSP processor interface extending [SymbolProcessor].
 */
interface Processor : SymbolProcessor, ProcessingService {
  /** Emits when the last round has completed and KSP has signalled no further rounds are coming.
   * A mutable shared flow is used to ensure all subscribers have an opportunity to process the 
   * event before the upstream continues.
   */
  fun observeAllRoundsCompleteEvent(): SharedFlow<Unit>
}