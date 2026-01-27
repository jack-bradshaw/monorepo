package com.jackbradshaw.backstab.entrypoint

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import com.jackbradshaw.backstab.annotations.backstab.Backstab
import com.jackbradshaw.backstab.core.backstabCoreComponent
import com.jackbradshaw.backstab.core.processor.Processor

class BackstabEntrypoint(
    private val environment: SymbolProcessorEnvironment
) : SymbolProcessor {

    private val processor: Processor by lazy {
        backstabCoreComponent(environment.codeGenerator).processor()
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val (validSymbols, invalidSymbols) = resolver
          .getSymbolsWithAnnotation(Backstab::class.qualifiedName!!).partition { it.validate() }

        val componentSymbols = validSymbols.filterIsInstance<KSClassDeclaration>()

        kotlinx.coroutines.runBlocking {
            processor.createMetaComponents(componentSymbols)
        }

        return invalidSymbols
    }
}

class BackstabProcessorFactory : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment)= BackstabEntrypoint(environment)
}