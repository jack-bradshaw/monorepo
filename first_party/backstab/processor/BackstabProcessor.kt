package com.jackbradshaw.backstab.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import com.jackbradshaw.backstab.annotations.Backstab
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.asClassName
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

class BackstabProcessor(
    private val environment: SymbolProcessorEnvironment
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val (validSymbols, invalidSymbols) = resolver
          .getSymbolsWithAnnotation(Backstab::class.qualifiedName!!).partition { it.validate() }

        validSymbols.filterIsInstance<KSClassDeclaration>().forEach {
            processComponent(it)
        }

        return invalidSymbols
    }

    private fun processComponent(component: KSClassDeclaration) {
        val packageName = component.packageName.asString()
        val componentName = component.simpleName.asString()
        
        val builderBindings = findBuilderBindings(component)
        if (builderBindings == null) {
            environment.logger.warn("No @Component.Builder or @Component.Factory found in $componentName", component)
            return
        }

        val model = BackstabComponent(
            packageName = packageName,
            simpleName = componentName,
            builderBindings = builderBindings
        )

        val fileSpec = BackstabGenerator.generate(model)

        fileSpec.writeTo(environment.codeGenerator, Dependencies(true, component.containingFile!!))
    }

    private fun findBuilderBindings(component: KSClassDeclaration): List<BackstabComponent.ComponentBuilderMethod>? {
        val builder = component.declarations.filterIsInstance<KSClassDeclaration>()
            .firstOrNull { 
                it.annotations.any { ann -> 
                    ann.shortName.asString() == "Builder" || ann.shortName.asString() == "Factory"
                } 
            }
            
        if (builder == null) return emptyList()

        val bindings = mutableListOf<BackstabComponent.ComponentBuilderMethod>()
        
        builder.getAllFunctions().forEach { func ->
            if (!func.isAbstract) return@forEach
            val name = func.simpleName.asString()
            if (name == "build") return@forEach
            
            func.parameters.forEach { param ->
                val paramName = param.name?.asString() ?: return@forEach
                val type = param.type.resolve()
                val declaration = type.declaration as? KSClassDeclaration ?: return@forEach
                val paramType = declaration.toClassName()
                
                bindings.add(
                    BackstabComponent.ComponentBuilderMethod(
                        methodName = name,
                        paramName = paramName,
                        paramType = paramType
                    )
                )
            }
        }
        
        return bindings
    }

    private fun KSClassDeclaration.toClassName() = ClassName(packageName.asString(), simpleName.asString())
    
    private fun FileSpec.writeTo(codeGenerator: CodeGenerator, dependencies: Dependencies) {
        val file = codeGenerator.createNewFile(dependencies, packageName, name)
        val writer = OutputStreamWriter(file, StandardCharsets.UTF_8)
        writer.use {
            writeTo(it)
        }
    }
}
