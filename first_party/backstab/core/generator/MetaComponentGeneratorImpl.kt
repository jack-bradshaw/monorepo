package com.jackbradshaw.backstab.core.generator

import com.jackbradshaw.backstab.core.model.BackstabComponent
import com.jackbradshaw.backstab.core.model.BackstabComponent.BuilderMethod
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.jackbradshaw.backstab.annotations.meta.MetaScope
import com.squareup.kotlinpoet.asClassName
import dagger.Provides

class MetaComponentGeneratorImpl : MetaComponentGenerator {

    override suspend fun generate(component: BackstabComponent): FileSpec {
        val moduleName = "${component.name}AutoModule"
        
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
        val componentClassName = ClassName(component.packageName, component.name)
        val providerName = "provide${component.name}"
        
        val funBuilder = FunSpec.builder(providerName)
            .addAnnotation(Provides::class.asClassName())
            .addAnnotation(MetaScope::class.asClassName())
            .returns(componentClassName)
        
        val bindings = component.builder?.bindings ?: emptyList()
        
        bindings.forEachIndexed { index, binding ->
            val paramBuilder = ParameterSpec.builder("arg$index", binding.paramType)
            
            binding.named?.let { paramBuilder.addAnnotation(it) }
            paramBuilder.addAnnotations(binding.qualifiers)
            
            funBuilder.addParameter(paramBuilder.build())
        }

        val daggerName = "Dagger${component.name}"
        val daggerClass = ClassName(component.packageName, daggerName)
        
        val codeBlock = if (component.builder == null) {
            "return %T.create()"
        } else {
            val sb = StringBuilder("return %T.builder()")
            bindings.forEachIndexed { index, binding ->
                // Call .methodName(arg{i})
                sb.append("\n  .${binding.methodName}(arg$index)") 
            }
            sb.append("\n  .build()")
            sb.toString()
        }
        
        funBuilder.addStatement(codeBlock, daggerClass)
        
        return funBuilder.build()
    }
}
