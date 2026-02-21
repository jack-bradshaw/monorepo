package com.jackbradshaw.backstab.ksp.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.jackbradshaw.backstab.core.ports.errorsink.ErrorSink
import com.jackbradshaw.backstab.core.ports.modulesink.ModuleSink
import com.jackbradshaw.backstab.core.ports.targetsource.TargetSource
import kotlinx.coroutines.flow.SharedFlow

interface Processor : SymbolProcessor, TargetSource, ModuleSink, ErrorSink {
  /**
   * Emits Unit when there are no more symbols to process.
   *
   * Implementations must trigger the emission when one of two events occurs:
   * 1. KSP notifies the symbol processor via the `finish` callback.
   * 2. The custom logic of the processor deems there are no more symbols to process.
   *
   * In both cases, collectors may assume the signal means the process is nearing temrination and
   * should close resources if necessary.
   */
  val onProcessingComplete: SharedFlow<Unit>
}
