package com.jackbradshaw.backstab.core.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration

interface Processor {
    suspend fun createMetaComponents(components: List<KSClassDeclaration>)
}
