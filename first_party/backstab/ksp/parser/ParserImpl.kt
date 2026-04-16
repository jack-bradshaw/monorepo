package com.jackbradshaw.backstab.ksp.parser

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.typeregistry.BackstabTypeRegistry
import com.jackbradshaw.backstab.core.typeregistry.DaggerTypeRegistry
import com.jackbradshaw.backstab.ksp.adapters.isQualifier
import com.jackbradshaw.backstab.ksp.adapters.matches
import com.jackbradshaw.backstab.ksp.adapters.nameChain
import com.jackbradshaw.backstab.ksp.adapters.toQualifier
import com.jackbradshaw.backstab.ksp.adapters.toType
import com.jackbradshaw.oksp.model.SourceFile
import javax.inject.Inject

/** Provides a concrete implementation of [Parser]. */
@CoreScope
class ParserImpl @Inject constructor() : Parser {

  override fun toBackstabTarget(declaration: KSClassDeclaration): BackstabTarget {
    val annotations = declaration.annotations.toList()

    val hasBackstab = annotations.any { it.matches(BackstabTypeRegistry.BACKSTAB.qualifiedName!!) }
    val hasComponent = annotations.any { it.matches(DaggerTypeRegistry.COMPONENT.qualifiedName!!) }

    require(hasBackstab) { "Expected $declaration to be annotated with @Backstab." }
    require(hasComponent) { "Expected $declaration to be annotated with @Component." }

    val component =
        BackstabTarget.Component(
            packageName = declaration.packageName.asString(), nameChain = declaration.nameChain())
    val instantiator = parseInstantiator(declaration)

    val fileNameWithExtension = declaration.containingFile?.fileName ?: "Unknown.kt"
    val fileName = fileNameWithExtension.substringBeforeLast(".")
    val extension = fileNameWithExtension.substringAfterLast(".", "kt")

    val header =
        SourceFile(
            packageName = component.packageName,
            fileName = fileName,
            extension = extension,
        )

    return BackstabTarget(header, component, instantiator)
  }

  /** Parses the [BackstabTarget.ComponentInstantiator] for the component. */
  private fun parseInstantiator(
      component: KSClassDeclaration
  ): BackstabTarget.ComponentInstantiator {
    component.findFactory()?.let {
      return parseFactory(it)
    }

    component.findBuilder()?.let {
      return parseBuilder(it)
    }

    return BackstabTarget.ComponentInstantiator.CreateFunction
  }

  /** Finds the `@Component.Factory` interface defined within this component, if any. */
  private fun KSClassDeclaration.findFactory(): KSClassDeclaration? {
    return declarations.filterIsInstance<KSClassDeclaration>().firstOrNull { decl ->
      decl.annotations.any { it.matches(DaggerTypeRegistry.COMPONENT_FACTORY.qualifiedName!!) }
    }
  }

  /**
   * Parses a `@Component.Factory` interface into a
   * [BackstabTarget.ComponentInstantiator.FactoryFunction] model.
   */
  private fun parseFactory(
      factoryInterface: KSClassDeclaration
  ): BackstabTarget.ComponentInstantiator.FactoryFunction {
    val function =
        checkNotNull(factoryInterface.getAllFunctions().firstOrNull { it.isAbstract }) {
          "Factory interface must have exactly one abstract function."
        }

    val name = function.simpleName.asString()

    val params =
        function.parameters.map { param ->
          val type = param.type.resolve().toType()
          val qualifier = param.annotations.firstOrNull { it.isQualifier() }?.toQualifier()
          BackstabTarget.ComponentInstantiator.FactoryFunction.Parameter(type, qualifier)
        }

    return BackstabTarget.ComponentInstantiator.FactoryFunction(name, params)
  }

  /** Finds the `@Component.Builder` interface defined within this component, if any. */
  private fun KSClassDeclaration.findBuilder(): KSClassDeclaration? {
    return declarations.filterIsInstance<KSClassDeclaration>().firstOrNull { decl ->
      decl.annotations.any { it.matches(DaggerTypeRegistry.COMPONENT_BUILDER.qualifiedName!!) }
    }
  }

  /**
   * Parses a `@Component.Builder` interface into a
   * [BackstabTarget.ComponentInstantiator.BuilderInterface] model.
   */
  private fun parseBuilder(
      builderInterface: KSClassDeclaration
  ): BackstabTarget.ComponentInstantiator.BuilderInterface {
    val functions = builderInterface.getAllFunctions().filter { it.isAbstract }.toList()

    val buildFunctionSymbol =
        checkNotNull(functions.firstOrNull { it.parameters.isEmpty() }) {
          "Builder interface must have a build function with no parameters."
        }

    val buildFunctionName = buildFunctionSymbol.simpleName.asString()
    val buildFunctionReturnType =
        checkNotNull(buildFunctionSymbol.returnType?.resolve()?.toType()) {
          "Could not resolve return type for build function"
        }

    val buildFunction =
        BackstabTarget.ComponentInstantiator.BuilderInterface.BuildFunction(
            buildFunctionName, buildFunctionReturnType)

    val setters =
        functions
            .filter { it.parameters.isNotEmpty() }
            .map { func ->
              val name = func.simpleName.asString()
              val param = func.parameters.first()
              val type = param.type.resolve().toType()
              val qualifier = param.annotations.firstOrNull { it.isQualifier() }?.toQualifier()
              BackstabTarget.ComponentInstantiator.BuilderInterface.SetterFunction(
                  name, type, qualifier)
            }
            .toList()

    return BackstabTarget.ComponentInstantiator.BuilderInterface(setters, buildFunction)
  }
}
