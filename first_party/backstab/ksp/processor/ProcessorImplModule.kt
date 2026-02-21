package com.jackbradshaw.backstab.ksp.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.jackbradshaw.backstab.core.ports.errorsink.ErrorSink
import com.jackbradshaw.backstab.core.ports.modulesink.ModuleSink
import com.jackbradshaw.backstab.core.ports.targetsource.TargetSource
import dagger.Binds
import dagger.Module

/** Dagger module that binds the KSP platform components. */
@Module
interface KspProcessorModule {
  /** Binds [KspBackend] as a [Processor]. */
  @Binds fun bindProcessor(impl: KspBackend): Processor

  /** Binds [KspBackend] as a [SymbolProcessor]. */
  @Binds fun bindSymbolProcessor(impl: KspBackend): SymbolProcessor

  /** Binds [KspBackend] as a [TargetSource]. */
  @Binds fun bindTargetSource(impl: KspBackend): TargetSource

  /** Binds [KspBackend] as a [ModuleSink]. */
  @Binds fun bindModuleSink(impl: KspBackend): ModuleSink

  /** Binds [KspBackend] as an [ErrorSink]. */
  @Binds fun bindErrorSink(impl: KspBackend): ErrorSink
}
