package com.jackbradshaw.backstab.core.generator

import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.model.BackstabModule
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.model.BackstabTarget.ComponentInstantiator.BuilderInterface
import com.jackbradshaw.backstab.core.model.BackstabTarget.ComponentInstantiator.CreateFunction
import com.jackbradshaw.backstab.core.model.BackstabTarget.ComponentInstantiator.FactoryFunction
import com.jackbradshaw.oksp.model.SourceFile
import com.jackbradshaw.backstab.core.model.kotlinpoet.toAnnotationSpec
import com.jackbradshaw.backstab.core.model.kotlinpoet.toTypeName
import com.jackbradshaw.backstab.core.typeregistry.BackstabTypeRegistry
import com.jackbradshaw.backstab.core.typeregistry.DaggerTypeRegistry
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import javax.inject.Inject

/** Provides a concrete implementation of [Generator]. */
@CoreScope
class GeneratorImpl @Inject constructor() : Generator {

  override suspend fun generateModuleFor(target: BackstabTarget): BackstabModule {
    val metadata = generateBackstabModuleMetadata(target)
    val source = generateModuleSource(target, metadata)
    val file = FileSpec.builder(metadata.packageName, metadata.simpleName).addType(source).build()

    return BackstabModule(
        SourceFile(metadata.packageName, metadata.simpleNames.last(), "kt", file.toString()))
  }

  /**
   * Generates metadata for a backstab module that provides [target].
   *
   * The metadata is wrapped in a [ClassName], with a package matching the [target], and a name
   * matching the class name chain (with underscores separating nested classes) and _BackstabModule
   * appended.
   *
   * For example the following class would yield `package=com.foo` and
   * `name=Inner_Outer_BackstabModule`:
   * ```
   * package com.foo
   *
   * interface Outer
   *   @Backstab
   *   @Component
   *   interface Inner {
   *     fun provideFoo(): Foo
   *     fun create(): Inner
   *   }
   * }
   * ```
   */
  private fun generateBackstabModuleMetadata(target: BackstabTarget): ClassName {
    val moduleName = target.component.nameChain.joinToString("_") + "_BackstabModule"
    return ClassName(target.component.packageName, moduleName)
  }

  /**
   * Genearates the souce code for a backstab module that provides [target] and uses [metadata] for
   * paackage/name information. The generated module accurately targets the factory, builder or
   * implicit create function of the [target].
   */
  private fun generateModuleSource(target: BackstabTarget, metadata: ClassName): TypeSpec {
    return TypeSpec.objectBuilder(metadata)
        .addAnnotation(DaggerTypeRegistry.MODULE.asClassName())
        .addFunction(generateProviderFunction(target))
        .build()
  }

  /**
   * Generates a function that provides [target] by invoking the factory, builder, or implicit
   * create-function of the Dagger-generated component associatd with [target].
   */
  private fun generateProviderFunction(target: BackstabTarget): FunSpec {
    // No risk of collisions because each module contains exactly one provisioning call.
    val name = "provideComponent"
    val spec =
        FunSpec.builder(name)
            .addAnnotation(DaggerTypeRegistry.PROVIDES.asClassName())
            .addAnnotation(BackstabTypeRegistry.AGGREGATE_SCOPE.asClassName())
            .returns(target.asClassName())

    when (val instantiator = target.instantiator) {
      is CreateFunction -> spec.configureForCreateInstantiator(target)
      is BuilderInterface -> spec.configureForBuilderInstantiator(target, instantiator)
      is FactoryFunction -> spec.configureForFactoryInstantiator(target, instantiator)
    }

    return spec.build()
  }

  private fun BackstabTarget.asClassName(): ClassName =
      ClassName(component.packageName, component.nameChain)

  /**
   * Configures this spec with code that instantiates [target] via the `create` function of the
   * Dagger-generated component associated with [target]. No check is performeed to verify the
   * `create` function actually exists, so the caller must verify this is the correct instantiation
   * approach for [target]. This function mutates the builder directly so nothing is returned.
   */
  private fun FunSpec.Builder.configureForCreateInstantiator(target: BackstabTarget) {
    addCode("return %T.create()", target.generatedComponentClassName())
  }

  /**
   * Configures this spec with code that instantiates [target] via the component builder of the
   * Dagger-generated component associated with [target]. No check is performeed to verify the
   * builder actually exists, so the caller must verify this is the correct instantiation approach
   * for [target]. This function mutates the builder directly so nothing is returned.
   */
  private fun FunSpec.Builder.configureForBuilderInstantiator(
      target: BackstabTarget,
      instantiator: BuilderInterface
  ) {

    val codeBlockBuilder =
        CodeBlock.builder().add("return %T.builder()", target.generatedComponentClassName())

    for ((index, setter) in instantiator.setters.withIndex()) {
      val paramName = "arg$index"

      val paramSpec = ParameterSpec.builder(paramName, setter.type.toTypeName())
      setter.qualifier?.let { paramSpec.addAnnotation(it.toAnnotationSpec()) }
      addParameter(paramSpec.build())

      codeBlockBuilder.add("\n.%N(%L)", setter.name, paramName)
    }

    addCode(codeBlockBuilder.add("\n.%N()", instantiator.buildFunction.name).build())
  }

  /**
   * Configures this spec with code that instantiates [target] via the component factory of the
   * Dagger-generated component associated with [target]. No check is performeed to verify the
   * factory actually exists, so the caller must verify this is the correct instantiation approach
   * for [target]. This function mutates the builder directly so nothing is returned.
   */
  private fun FunSpec.Builder.configureForFactoryInstantiator(
      target: BackstabTarget,
      instantiator: FactoryFunction
  ) {

    val args = mutableListOf<String>()

    for ((index, parameter) in instantiator.parameters.withIndex()) {
      val paramName = "arg$index"

      val paramSpec = ParameterSpec.builder(paramName, parameter.type.toTypeName())
      parameter.qualifier?.let { paramSpec.addAnnotation(it.toAnnotationSpec()) }
      addParameter(paramSpec.build())

      args += paramName
    }

    addCode(
        CodeBlock.builder()
            .add(
                "return %T.factory().%N(%L)",
                target.generatedComponentClassName(),
                instantiator.name,
                args.joinToString(", "))
            .build())
  }

  /**
   * Returns the name of the Dagger-generated component associated with this target.
   *
   * This function assumes dagger generates a class matching the name of the target, with "Dagger"
   * prepended, and nested classes separated with underscores.
   *
   * For example, the following code produces "DaggerFoo_Bar" when the target is Bar:
   * ```kotlin
   * interface Foo {
   *   @Component
   *   interface Bar { ... }
   * }
   * ```
   */
  private fun BackstabTarget.generatedComponentClassName(): ClassName {
    val daggerName = "Dagger" + component.nameChain.joinToString("_")
    return ClassName(component.packageName, daggerName)
  }
}
