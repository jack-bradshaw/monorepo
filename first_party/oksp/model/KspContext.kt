package com.jackbradshaw.oksp.model

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

/** Context containing KSP native components required for a single round of processing. */
data class KspContext(val environment: SymbolProcessorEnvironment, val resolver: Resolver)
