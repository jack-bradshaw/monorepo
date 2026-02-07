package com.jackbradshaw.backstab.processor.generator

import com.jackbradshaw.backstab.annotations.aggregate.AggregateScope
import com.jackbradshaw.backstab.processor.BackstabCoreScope
import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import dagger.Provides
import javax.inject.Inject

/** Provides a concrete implementation of [AggregateComponentGenerator]. */
@BackstabCoreScope
class AggregateComponentGeneratorImpl @Inject constructor() : AggregateComponentGenerator {
  override suspend fun generate(component: BackstabComponent): FileSpec {
    val moduleName = "${component.names.joinToString("_")}_AggregateModule"

    return FileSpec.builder(component.packageName, moduleName)
        .addType(
            TypeSpec.objectBuilder(moduleName)
                .addAnnotation(dagger.Module::class.asClassName())
                .addFunction(generateProviderFunction(component))
                .build())
        .build()
  }

  /** Generates the provider function that exposes the component instance. */
  private fun generateProviderFunction(component: BackstabComponent): FunSpec {
    val componentClassName = ClassName(component.packageName, component.names)
    val providerName = "provide${component.name}"

    val spec =
        FunSpec.builder(providerName)
            .addAnnotation(Provides::class.asClassName())
            .addAnnotation(AggregateScope::class.asClassName())
            .returns(componentClassName)

    val funSpec =
        when (val instantiator = component.instantiator) {
          is BackstabComponent.Create -> spec.populateForCreateInstantiator(component)
          is BackstabComponent.Builder ->
              spec.populateForBuilderInstantiator(component, instantiator)
          is BackstabComponent.Factory ->
              spec.populateForFactoryInstantiator(component, instantiator)
        }

    return spec.build()
  }

  /** Populates the function body for components using the implicit `create()` method. */
  private fun FunSpec.Builder.populateForCreateInstantiator(
      component: BackstabComponent
  ): FunSpec.Builder = addCode("return %T.create()", component.getDaggerComponentClassName())

  /** Populates the function body for components using a `@Component.Builder`. */
  private fun FunSpec.Builder.populateForBuilderInstantiator(
      component: BackstabComponent,
      instantiator: BackstabComponent.Builder
  ): FunSpec.Builder {
    val allBindings = instantiator.componentBindings + instantiator.instanceBindings

    allBindings.forEachIndexed { index, binding ->
      val paramSpec = ParameterSpec.builder("arg$index", binding.paramType)
      binding.qualification?.let { paramSpec.addAnnotation(it.annotation) }
      addParameter(paramSpec.build())
    }

    val implementationBuilder = CodeBlock.builder()
    implementationBuilder.add("return %T.builder()", component.getDaggerComponentClassName())

    allBindings.forEachIndexed { index, binding ->
      implementationBuilder.add("\n  .%N(%L)", binding.functionName, "arg$index")
    }

    implementationBuilder.add("\n  .%N()", instantiator.buildFunction.functionName)
    addCode(implementationBuilder.build())
    return this
  }

  /** Populates the function body for components using a `@Component.Factory`. */
  private fun FunSpec.Builder.populateForFactoryInstantiator(
      component: BackstabComponent,
      instantiator: BackstabComponent.Factory
  ): FunSpec.Builder {
    instantiator.parameters.forEachIndexed { index, parameter ->
      val paramName = "arg$index"
      val paramSpec = ParameterSpec.builder(paramName, parameter.paramType)

      val qualification = parameter.qualification
      if (qualification != null) {
        paramSpec.addAnnotation(qualification.annotation)
      }

      addParameter(paramSpec.build())
    }

    val implementationBuilder = CodeBlock.builder()
    implementationBuilder.add(
        "return %T.factory().%N(",
        component.getDaggerComponentClassName(),
        instantiator.functionName)

    val args = instantiator.parameters.indices.joinToString(", ") { "arg$it" }
    implementationBuilder.add(args)
    implementationBuilder.add(")")

    addCode(implementationBuilder.build())
    return this
  }

  /** Returns the ClassName for the Dagger generated implementation. */
  private fun BackstabComponent.getDaggerComponentClassName(): ClassName {
    val daggerName = "Dagger" + names.joinToString("_")
    return ClassName(packageName, daggerName)
  }
}
