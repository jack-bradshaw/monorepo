package com.jackbradshaw.backstab.processor.parser

import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.external.DaggerTypeRegistry
import com.jackbradshaw.backstab.external.JavaxTypeRegistry
import com.jackbradshaw.backstab.processor.ProcessorScope
import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.squareup.kotlinpoet.ksp.toAnnotationSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import java.util.LinkedList
import javax.inject.Inject

/** Provides a concrete implementation of [Parser]. */
@ProcessorScope
class ParserImpl @Inject constructor() : Parser {

  /**
   * Converts [component] to a [BackstabComponent] model.
   *
   * @throws IllegalArgumentException if [component] is not annotated with `@dagger.Component`.
   */
  override fun parseModel(component: KSClassDeclaration): BackstabComponent {
    val annotations = component.annotations.toList()

    if (!annotations.any { it.matches(DaggerTypeRegistry.COMPONENT) }) {
      throw IllegalArgumentException(
          "Error: ${component.qualifiedName?.asString() ?: component.simpleName.asString()} is not a Dagger @Component.")
    }

    val packageName = component.packageName.asString()
    val names = parseNames(component)
    val className = ClassName(packageName, names)
    val instantiator = parseInstantiator(component)

    return BackstabComponent(className, instantiator)
  }

  /**
   * Parses the hierarchy of names for the component by traversing up the declaration hierarchy.
   *
   * For example: `Outer.Inner.FooComponent` becomes `['Outer', 'Inner', 'FooComponent']`.
   */
  private fun parseNames(declaration: KSClassDeclaration): List<String> {
    val names = LinkedList<String>()
    var current: KSClassDeclaration? = declaration
    while (current != null) {
      names.add(0, current.simpleName.asString())
      current = current.parentDeclaration as? KSClassDeclaration
    }
    return names
  }

  /** Parses the [BackstabComponent.ComponentInstantiator] for the component. */
  private fun parseInstantiator(
      component: KSClassDeclaration
  ): BackstabComponent.ComponentInstantiator {
    component.findFactory()?.let {
      return parseFactory(it)
    }

    component.findBuilder()?.let {
      return parseBuilder(it)
    }

    return BackstabComponent.ComponentInstantiator.CreateFunction
  }

  /** Finds the `@Component.Factory` interface defined within this component, if any. */
  private fun KSClassDeclaration.findFactory(): KSClassDeclaration? {
    return declarations
        .filterIsInstance<KSClassDeclaration>()
        .firstOrNull { decl ->
          decl.annotations.any { it.matches(DaggerTypeRegistry.COMPONENT_FACTORY) }
        }
  }

  /** Finds the `@Component.Builder` interface defined within this component, if any. */
  private fun KSClassDeclaration.findBuilder(): KSClassDeclaration? {
    return declarations
        .filterIsInstance<KSClassDeclaration>()
        .firstOrNull { decl ->
          decl.annotations.any { it.matches(DaggerTypeRegistry.COMPONENT_BUILDER) }
        }
  }

  /** Parses a `@Component.Factory` interface into a [BackstabComponent.ComponentInstantiator.FactoryFunction] model. */
  private fun parseFactory(
      factoryInterface: KSClassDeclaration
  ): BackstabComponent.ComponentInstantiator.FactoryFunction {
    val function =
        checkNotNull(factoryInterface.getAllFunctions().firstOrNull { it.isAbstract }) {
          "Factory interface must have exactly one abstract function."
        }

    val owner = factoryInterface.asType(emptyList()).toTypeName() as ClassName
    val name = MemberName(owner, function.simpleName.asString())

    val params =
        function.parameters.map { param ->
          val type = param.type.resolve().toTypeName()
          val qualification = parseQualification(param.annotations)
          BackstabComponent.ComponentInstantiator.FactoryFunction.Parameter(type, qualification)
        }

    return BackstabComponent.ComponentInstantiator.FactoryFunction(name, params)
  }

  /** Parses a `@Component.Builder` interface into a [BackstabComponent.ComponentInstantiator.BuilderInterface] model. */
  private fun parseBuilder(
      builderInterface: KSClassDeclaration
  ): BackstabComponent.ComponentInstantiator.BuilderInterface {
    val functions = builderInterface.getAllFunctions().filter { it.isAbstract }.toList()

    val buildFunctionSymbol =
        checkNotNull(functions.firstOrNull { it.parameters.isEmpty() }) {
          "Builder interface must have a build function with no parameters."
        }
        
    val owner = builderInterface.asType(emptyList()).toTypeName() as ClassName
    val buildFunctionName = MemberName(owner, buildFunctionSymbol.simpleName.asString())
    val buildFunctionReturnType =
        buildFunctionSymbol.returnType?.resolve()?.toTypeName()
            ?: throw IllegalStateException("Could not resolve return type for build function.")

    val buildFunction =
        BackstabComponent.ComponentInstantiator.BuildFunction(buildFunctionName, buildFunctionReturnType)

    val bindingFunctions = functions.filter { it.parameters.isNotEmpty() }

    val componentSetters =
        bindingFunctions
            .filter { func ->
              !func.parameters.first().annotations.any {
                it.matches(DaggerTypeRegistry.BINDS_INSTANCE)
              }
            }
            .map { func ->
              val name = MemberName(owner, func.simpleName.asString())
              val param = func.parameters.first()
              val type = param.type.resolve().toTypeName()
              val qualification = parseQualification(param.annotations)
              BackstabComponent.ComponentInstantiator.SetterFunction(name, type, qualification)
            }

    val boundInstanceSetters =
        bindingFunctions
            .filter { func ->
              func.parameters.first().annotations.any { it.matches(DaggerTypeRegistry.BINDS_INSTANCE) }
            }
            .map { func ->
              val name = MemberName(owner, func.simpleName.asString())
              val param = func.parameters.first()
              val type = param.type.resolve().toTypeName()
              val qualification = parseQualification(param.annotations)
              BackstabComponent.ComponentInstantiator.SetterFunction(name, type, qualification)
            }

    return BackstabComponent.ComponentInstantiator.BuilderInterface(
        componentSetters, boundInstanceSetters, buildFunction)
  }

  /** Parses Dagger qualification (Named or custom Qualifier) from [annotations]. */
  private fun parseQualification(
      annotations: Sequence<KSAnnotation>
  ): BackstabComponent.Qualification? {
    val annotation =
        annotations.firstOrNull { it.matches(JavaxTypeRegistry.NAMED) || it.isDaggerQualifier() }
            ?: return null

    return if (annotation.matches(JavaxTypeRegistry.NAMED)) {
      BackstabComponent.Qualification.Named(annotation.toAnnotationSpec())
    } else {
      BackstabComponent.Qualification.Qualifier(annotation.toAnnotationSpec())
    }
  }

  /** Returns true if this annotation is a Dagger qualifier. */
  private fun KSAnnotation.isDaggerQualifier(): Boolean {
    val declaration = annotationType.resolve().declaration as? KSClassDeclaration ?: return false
    return declaration.annotations.any { it.matches(JavaxTypeRegistry.QUALIFIER) }
  }

  /** Returns true if this annotation matches the [qualifiedName]. */
  private fun KSAnnotation.matches(qualifiedName: String): Boolean {
    return annotationType.resolve().declaration.qualifiedName?.asString() == qualifiedName
  }
}
