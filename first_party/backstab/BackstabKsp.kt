package com.jackbradshaw.backstab

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate
import com.jackbradshaw.backstab.annotations.Backstab
import com.jackbradshaw.backstab.processor.processorComponent
import com.jackbradshaw.backstab.processor.Processor

/** The KSP entrypoint for the Backstab annotation processor. */
class BackstabKsp(private val environment: SymbolProcessorEnvironment) : SymbolProcessor {

  /** The underlying processor which actually does the code generation. */
  private val processor: Processor by lazy {
    processorComponent(environment.codeGenerator).processor()
  }

  override fun process(resolver: Resolver): List<KSAnnotated> {
    val (validSymbols, invalidSymbols) =
        resolver.getSymbolsWithAnnotation(Backstab::class.qualifiedName!!).partition {
          it.validate()
        }

    val componentSymbols = validSymbols.filterIsInstance<KSClassDeclaration>()

    if (componentSymbols.isNotEmpty()) {
      kotlinx.coroutines.runBlocking { processor.createAggregateComponents(componentSymbols) }
    }

    return invalidSymbols
  }
}

/** Provides the processor. Required for KSP integration. */
class BackstabProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment) = BackstabKsp(environment)
}
