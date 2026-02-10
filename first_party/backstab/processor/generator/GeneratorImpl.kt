package com.jackbradshaw.backstab.processor.generator

import com.jackbradshaw.backstab.annotations.AggregateScope
import com.jackbradshaw.backstab.external.DaggerTypeRegistry
import com.jackbradshaw.backstab.external.JavaxTypeRegistry
import com.jackbradshaw.backstab.processor.ProcessorScope
import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import javax.inject.Inject

/** Provides a concrete implementation of [Generator]. */
@ProcessorScope
class GeneratorImpl @Inject constructor() : Generator {
  
  override suspend fun generate(component: BackstabComponent): FileSpec {
    return FileSpec.builder(getGeneratedAggregateModuleName(component))
        .addType(generateModule(component))
        .build()
  }

  private fun generateModule(component: BackstabComponent): TypeSpec {
    return TypeSpec.objectBuilder(getGeneratedAggregateModuleName(component))
        .addAnnotation(DaggerTypeRegistry.MODULE)
        .addFunction(generateProviderFunction(component))
        .build()
  }

  /** Generates the provider function that exposes the component instance. */
  private fun generateProviderFunction(component: BackstabComponent): FunSpec {
    val componentClassName = component.className
    val providerName = "provide${component.className.simpleName}"

    val spec =
        FunSpec.builder(providerName)
            .addAnnotation(DaggerTypeRegistry.PROVIDES)
            .addAnnotation(AggregateScope::class.asClassName())
            .returns(componentClassName)

    val funSpec =
        when (val instantiator = component.instantiator) {
          is BackstabComponent.ComponentInstantiator.CreateFunction -> spec.populateForCreateInstantiator(component)
          is BackstabComponent.ComponentInstantiator.BuilderInterface ->
              spec.populateForBuilderInstantiator(component, instantiator)
          is BackstabComponent.ComponentInstantiator.FactoryFunction ->
              spec.populateForFactoryInstantiator(component, instantiator)
        }

    return spec.build()
  }

  /**
   *  Populates the builder with code that calls the `create()` method to instantiate [component]
   * and returns the mutated builder.
   * 
   * The generated code will only work if the [component] a `create` function for instantiation.
   */
  private fun FunSpec.Builder.populateForCreateInstantiator(
      component: BackstabComponent
  ): FunSpec.Builder = addCode("return %T.create()", component.generatedDaggerComponentName)

  /**
   * Populates the builder with code that calls a `@Component.Builder` to instantiate [component]
   * and returns the mutated builder.
   *
   * The generated code will only work if the [component] uses a builder interface for instantiation.
   */
  private fun FunSpec.Builder.populateForBuilderInstantiator(
      component: BackstabComponent,
      instantiator: BackstabComponent.ComponentInstantiator.BuilderInterface
  ): FunSpec.Builder {
    val allSetters = instantiator.componentSetters + instantiator.boundInstanceSetters

    allSetters.forEachIndexed { index, setter ->
      val paramSpec = ParameterSpec.builder("arg$index", setter.type)
      setter.qualification?.let { paramSpec.addAnnotation(it.annotation) }
      addParameter(paramSpec.build())
    }

    addCode(
        CodeBlock.builder()
            .apply {
              add("return %T.builder()", component.generatedDaggerComponentName)
              allSetters.forEachIndexed { index, setter -> add("\n  .%N(%L)", setter.name, "arg$index") }
              add("\n  .%N()", instantiator.buildFunction.name)
            }
            .build())
    return this
  }

  /**
   * Populates the builder with code that calls a `@Component.Factory` to instantiate [component]
   * and returns the mutated builder.
   *
   * The generated code will only work if the [component] uses a factory function for instantiation
   */
  private fun FunSpec.Builder.populateForFactoryInstantiator(
      component: BackstabComponent,
      instantiator: BackstabComponent.ComponentInstantiator.FactoryFunction
  ): FunSpec.Builder {
    instantiator.parameters.forEachIndexed { index, parameter ->
      val paramName = "arg$index"
      val paramSpec = ParameterSpec.builder(paramName, parameter.type)

      val qualification = parameter.qualification
      if (qualification != null) {
        paramSpec.addAnnotation(qualification.annotation)
      }

      addParameter(paramSpec.build())
    }

    val args = instantiator.parameters.indices.joinToString(", ") { "arg$it" }
    addCode(
        CodeBlock.builder()
            .add(
                "return %T.factory().%N(%L)",
                component.generatedDaggerComponentName,
                instantiator.name,
                args)
            .build())
    return this
  }

  private fun getGeneratedAggregateModuleName(component: BackstabComponent): ClassName {
    val moduleName = "${component.className.simpleNames.joinToString("_")}_AggregateModule"
    return ClassName(component.className.packageName, moduleName)
  }
}
