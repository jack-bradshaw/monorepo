package com.jackbradshaw.backstab.core.parser

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.core.model.BackstabComponent
import com.jackbradshaw.backstab.core.model.BackstabComponent.BuilderMethod
import com.squareup.kotlinpoet.ClassName

class ParserImpl : Parser {

    override fun parseModel(component: KSClassDeclaration): BackstabComponent? {
        val packageName = component.packageName.asString()
        val componentName = component.simpleName.asString()
        
        val builder = findBuilder(component)

        return BackstabComponent(
            packageName = packageName,
            name = componentName,
            builder = builder
        )
    }

    private fun findBuilder(component: KSClassDeclaration): BackstabComponent.Builder? {
        val builder = component.declarations.filterIsInstance<KSClassDeclaration>()
            .firstOrNull { 
                it.annotations.any { ann -> 
                    ann.shortName.asString() == "Builder" || ann.shortName.asString() == "Factory"
                } 
            }
            
        if (builder == null) return null

        val bindings = mutableListOf<BuilderMethod>()
        
        builder.getAllFunctions().forEach { func ->
            if (!func.isAbstract) return@forEach
            val name = func.simpleName.asString()
            if (name == "build") return@forEach
            
            func.parameters.forEach { param ->
                val type = param.type.resolve()
                val declaration = type.declaration as? KSClassDeclaration ?: return@forEach
                val paramType = ClassName(declaration.packageName.asString(), declaration.simpleName.asString())
                
                // Extract qualifiers: strictly annotations annotated with @javax.inject.Qualifier
                val extractedQualifiers = param.annotations
                    .filter { ksAnnot ->
                        val type = ksAnnot.annotationType.resolve()
                        val declaration = type.declaration
                        declaration.annotations.any { metaAnnot ->
                            metaAnnot.shortName.asString() == "Qualifier" && 
                            metaAnnot.annotationType.resolve().declaration.packageName.asString() == "javax.inject"
                        }
                    }
                    .map { ksAnnotation ->
                        val builder = com.squareup.kotlinpoet.AnnotationSpec.builder(
                            ClassName(
                                ksAnnotation.annotationType.resolve().declaration.packageName.asString(),
                                ksAnnotation.shortName.asString()
                            )
                        )
                        ksAnnotation.arguments.forEach { arg ->
                            val value = arg.value
                            if (value is String) {
                                builder.addMember("%S", value)
                            } else if (value != null) {
                                builder.addMember("%L", value)
                            }
                        }
                        builder.build()
                    }
                    .toList()
                
                val namedAnnotation = extractedQualifiers.firstOrNull { 
                    it.typeName.toString() == "javax.inject.Named" 
                }
                
                val otherQualifiers = extractedQualifiers.filter { 
                    it.typeName.toString() != "javax.inject.Named" 
                }

                bindings.add(
                    BuilderMethod(
                        methodName = name,
                        paramType = paramType,
                        named = namedAnnotation,
                        qualifiers = otherQualifiers
                    )
                )
            }
        }
        
        return BackstabComponent.Builder(bindings)
    }
}
