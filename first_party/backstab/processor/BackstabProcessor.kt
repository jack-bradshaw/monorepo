package com.jackbradshaw.backstab.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.validate
import com.jackbradshaw.backstab.annotations.Backstab
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.asClassName
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment

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
        val moduleName = "${componentName}AutoModule"
        
        val builderDependencies = findBuilderDependencies(component)
        if (builderDependencies == null) {
            environment.logger.warn("No @Component.Builder or @Component.Factory found in $componentName", component)
            return
        }

        val fileSpec = FileSpec.builder(packageName, moduleName)
            .addType(
                TypeSpec.objectBuilder(moduleName)
                    .addAnnotation(dagger.Module::class.asClassName())
                    .addFunction(
                        generateProviderFunction(component, builderDependencies)
                    )
                    .build()
            )
            .build()

        fileSpec.writeTo(environment.codeGenerator, Dependencies(true, component.containingFile!!))
    }

    private fun generateProviderFunction(
        component: KSClassDeclaration, 
        dependencies: List<Dependency>
    ): FunSpec {
        val componentClassName = component.toClassName()
        val providerName = "provide${component.simpleName.asString()}"
        
        val funBuilder = FunSpec.builder(providerName)
            .addAnnotation(dagger.Provides::class.asClassName())
            .addAnnotation(com.jackbradshaw.backstab.annotations.MetaScope::class.asClassName())
            .returns(componentClassName)
        
        dependencies.forEach { funBuilder.addParameter(it.spec) }

        val daggerName = "Dagger${component.simpleName.asString()}"
        val daggerClass = ClassName(component.packageName.asString(), daggerName)
        
        val codeBlock = StringBuilder("return %T.builder()")
        
        dependencies.forEach { dep ->
           // Call .builderMethodName(paramName)
           codeBlock.append("\n  .${dep.builderMethodName}(${dep.spec.name})") 
        }
        codeBlock.append("\n  .build()")
        
        funBuilder.addStatement(codeBlock.toString(), daggerClass)
        
        return funBuilder.build()
    }

    private fun findBuilderDependencies(component: KSClassDeclaration): List<Dependency>? {
        val builder = component.declarations.filterIsInstance<KSClassDeclaration>()
            .firstOrNull { 
                it.annotations.any { ann -> 
                    ann.shortName.asString() == "Builder" || ann.shortName.asString() == "Factory"
                } 
            }
            
        if (builder == null) return emptyList()

        val deps = mutableListOf<Dependency>()
        
        builder.getAllFunctions().forEach { func ->
            if (!func.isAbstract) return@forEach
            val name = func.simpleName.asString()
            if (name == "build") return@forEach
            
            func.parameters.forEach { param ->
                val paramName = param.name?.asString() ?: return@forEach
                val type = param.type.resolve()
                val declaration = type.declaration as? KSClassDeclaration ?: return@forEach
                val paramType = declaration.toClassName()
                
                deps.add(
                    Dependency(
                        builderMethodName = name,
                        spec = ParameterSpec.builder(paramName, paramType).build()
                    )
                )
            }
        }
        
        return deps
    }

    private fun KSClassDeclaration.toClassName() = ClassName(packageName.asString(), simpleName.asString())
    
    private fun FileSpec.writeTo(codeGenerator: CodeGenerator, dependencies: Dependencies) {
        val file = codeGenerator.createNewFile(dependencies, packageName, name)
        val writer = OutputStreamWriter(file, StandardCharsets.UTF_8)
        writer.use {
            writeTo(it)
        }
    }

    private data class Dependency(
        val builderMethodName: String,
        val spec: ParameterSpec
    )
}
