package com.jackbradshaw.backstab.processor

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName

object BackstabGenerator {

    fun generate(component: BackstabComponent): FileSpec {
        val moduleName = "${component.simpleName}AutoModule"
        
        return FileSpec.builder(component.packageName, moduleName)
            .addType(
                TypeSpec.objectBuilder(moduleName)
                    .addAnnotation(dagger.Module::class.asClassName())
                    .addFunction(
                        generateProviderFunction(component)
                    )
                    .build()
            )
            .build()
    }

    private fun generateProviderFunction(component: BackstabComponent): FunSpec {
        val componentClassName = ClassName(component.packageName, component.simpleName)
        val providerName = "provide${component.simpleName}"
        
        val funBuilder = FunSpec.builder(providerName)
            .addAnnotation(dagger.Provides::class.asClassName())
            .addAnnotation(com.jackbradshaw.backstab.annotations.MetaScope::class.asClassName())
            .returns(componentClassName)
        
        component.builderBindings.forEach { binding ->
            funBuilder.addParameter(
                ParameterSpec.builder(binding.paramName, binding.paramType).build()
            )
        }

        val daggerName = "Dagger${component.simpleName}"
        val daggerClass = ClassName(component.packageName, daggerName)
        
        val codeBlock = StringBuilder("return %T.builder()")
        
        component.builderBindings.forEach { binding ->
            // Call .methodName(paramName)
            codeBlock.append("\n  .${binding.methodName}(${binding.paramName})") 
        }
        codeBlock.append("\n  .build()")
        
        funBuilder.addStatement(codeBlock.toString(), daggerClass)
        
        return funBuilder.build()
    }
}
