package com.jackbradshaw.backstab.oksp.adapters

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSNode
import com.google.devtools.ksp.validate
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.typeregistry.BackstabTypeRegistry
import com.jackbradshaw.backstab.core.typeregistry.DaggerTypeRegistry
import com.jackbradshaw.obelisk.core.adapters.InflowAdapter
import com.jackbradshaw.obelisk.core.adapters.Ingestion
import com.jackbradshaw.obelisk.core.model.Source
import javax.inject.Inject

class BackstabInflowAdapter @Inject constructor() : InflowAdapter<KSNode, BackstabTarget> {
  
  override fun ingest(abstractSyntaxTreeRootElements: Set<KSNode>): Ingestion<KSNode, BackstabTarget> {
    val translated = mutableMapOf<KSNode, Set<BackstabTarget>>()
    val unused = mutableSetOf<KSNode>()
    val declarations = abstractSyntaxTreeRootElements.asSequence().flatMap { collectClasses(it) }

    for (node in declarations) {
      if (node.annotations.any { it.shortName.asString() == "Backstab" }) {
        if (node.validate()) {
          val target = parseTarget(node)
          translated[node] = setOf(target)
        } else {
          unused.add(node)
        }
      }
    }
    return Ingestion(translated, unused)
  }

  private fun collectClasses(node: KSNode): Sequence<KSClassDeclaration> = sequence {
    if (node is KSClassDeclaration) {
      yield(node)
      for (child in node.declarations) {
        yieldAll(collectClasses(child))
      }
    } else if (node is KSFile) {
      for (child in node.declarations) {
        yieldAll(collectClasses(child))
      }
    }
  }

  private fun parseTarget(declaration: KSClassDeclaration): BackstabTarget {
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
        Source(
            packageName = component.packageName,
            fileName = fileName,
            extension = extension,
        )

    return BackstabTarget(header, component, instantiator)
  }

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

  private fun KSClassDeclaration.findFactory(): KSClassDeclaration? {
    return declarations.filterIsInstance<KSClassDeclaration>().firstOrNull { decl ->
      decl.annotations.any { it.shortName.asString() == "Factory" }
    }
  }

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

  private fun KSClassDeclaration.findBuilder(): KSClassDeclaration? {
    return declarations.filterIsInstance<KSClassDeclaration>().firstOrNull { decl ->
      decl.annotations.any { it.shortName.asString() == "Builder" }
    }
  }

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
