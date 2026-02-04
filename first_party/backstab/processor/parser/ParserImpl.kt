package com.jackbradshaw.backstab.processor.parser

/**
 * Parses Dagger [KSClassDeclaration] symbols into the Backstab domain model.
 *
 * This implementation identifies Dagger qualifiers by matching the fully qualified class names
 * (FQCN) of annotations. `@Named` is identified by checking if the qualified name equals
 * `javax.inject.Named`. Custom qualifiers are identified by resolving the annotation type and
 * checking for the `javax.inject.Qualifier` base annotation.
 *
 * This implementation has several known limitations:
 * 1. Qualifiers defined in libraries without source attachment or KSP information may fail to
 *    resolve if the type hierarchy cannot be traversed.
 * 2. Relying strictly on `javax.inject.Qualifier` prevents usage of Jakarta Inject
 *    (`jakarta.inject.Qualifier`).
 * 3. Type resolution is computationally expensive. Deeply nested annotations may impact build
 *    performance.
 */
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.external.ExternalTypes
import com.jackbradshaw.backstab.processor.BackstabCoreScope
import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.squareup.kotlinpoet.ksp.toAnnotationSpec
import com.squareup.kotlinpoet.ksp.toTypeName
import javax.inject.Inject

/** Provides a concrete implementation of [Parser]. */
@BackstabCoreScope
class ParserImpl @Inject constructor() : Parser {

  /**
   * Converts [component] to a [BackstabComponent] model.
   *
   * @throws IllegalArgumentException if [component] is not annotated with `@dagger.Component`.
   */
  override fun parseModel(component: KSClassDeclaration): BackstabComponent {
    val annotations = component.annotations.toList()
    if (!annotations.any { it.matches(ExternalTypes.DAGGER_COMPONENT) }) {
      throw IllegalArgumentException(
          "Error: ${component.qualifiedName?.asString() ?: component.simpleName.asString()} is not a Dagger @Component.")
    }

    val packageName = component.packageName.asString()
    val names = collectNames(component)
    val instantiator = findInstantiator(component)

    return BackstabComponent(packageName, names, instantiator)
  }

  /** Collects the hierarchy of names for the component (e.g. `Outer.Inner`). */
  private fun collectNames(declaration: KSClassDeclaration): List<String> {
    val names = mutableListOf<String>()
    var current: KSClassDeclaration? = declaration
    while (current != null) {
      names.add(0, current.simpleName.asString())
      current = current.parentDeclaration as? KSClassDeclaration
    }
    return names
  }

  /** Finds the [BackstabComponent.ComponentInstantiator] for the component. */
  private fun findInstantiator(
      component: KSClassDeclaration
  ): BackstabComponent.ComponentInstantiator {
    val declarations = component.declarations.filterIsInstance<KSClassDeclaration>().toList()

    val factoryInterface =
        declarations.firstOrNull { decl ->
          decl.annotations.any { it.matches(ExternalTypes.DAGGER_COMPONENT_FACTORY) }
        }
    if (factoryInterface != null) {
      return parseFactoryInstantiator(factoryInterface) ?: BackstabComponent.Create
    }

    val builderInterface =
        declarations.firstOrNull { decl ->
          decl.annotations.any { it.matches(ExternalTypes.DAGGER_COMPONENT_BUILDER) }
        }
    if (builderInterface != null) {
      return parseBuilderInstantiator(builderInterface) ?: BackstabComponent.Create
    }

    return BackstabComponent.Create
  }

  /** Parses a `@Component.Factory` interface into a [BackstabComponent.Factory] model. */
  private fun parseFactoryInstantiator(
      factoryInterface: KSClassDeclaration
  ): BackstabComponent.Factory? {
    val function = factoryInterface.getAllFunctions().firstOrNull { it.isAbstract } ?: return null

    val functionName = function.simpleName.asString()
    val params =
        function.parameters.map { param ->
          val type = param.type.resolve().toTypeName()
          val qualification = extractQualification(param.annotations)
          BackstabComponent.FactoryParameter(type, qualification)
        }

    return BackstabComponent.Factory(functionName, params)
  }

  /** Parses a `@Component.Builder` interface into a [BackstabComponent.Builder] model. */
  private fun parseBuilderInstantiator(
      builderInterface: KSClassDeclaration
  ): BackstabComponent.Builder? {
    val functions = builderInterface.getAllFunctions().filter { it.isAbstract }.toList()

    val buildFunctionSymbol = functions.firstOrNull { it.parameters.isEmpty() } ?: return null
    val buildFunctionName = buildFunctionSymbol.simpleName.asString()
    val buildFunctionReturnType =
        buildFunctionSymbol.returnType?.resolve()?.toTypeName()
            ?: throw IllegalStateException("Could not resolve return type for build function.")

    val buildFunction =
        BackstabComponent.BuilderFunction(buildFunctionName, buildFunctionReturnType)

    val bindingFunctions = functions.filter { it.parameters.isNotEmpty() }

    val componentBindings =
        bindingFunctions
            .filter { func ->
              !func.parameters.first().annotations.any {
                it.matches(ExternalTypes.DAGGER_BINDS_INSTANCE)
              }
            }
            .map { func ->
              val name = func.simpleName.asString()
              val param = func.parameters.first()
              val type = param.type.resolve().toTypeName()
              val qualification = extractQualification(param.annotations)
              BackstabComponent.BuilderFunction(name, type, qualification)
            }
            .toList()

    val instanceBindings =
        bindingFunctions
            .filter { func ->
              func.parameters.first().annotations.any {
                it.matches(ExternalTypes.DAGGER_BINDS_INSTANCE)
              }
            }
            .map { func ->
              val name = func.simpleName.asString()
              val param = func.parameters.first()
              val type = param.type.resolve().toTypeName()
              val qualification = extractQualification(param.annotations)
              BackstabComponent.BuilderFunction(name, type, qualification)
            }
            .toList()

    return BackstabComponent.Builder(
        buildFunction = buildFunction,
        componentBindings = componentBindings,
        instanceBindings = instanceBindings)
  }

  /** Extracts the Dagger qualification (Named or Qualifier) from the annotation list. */
  private fun extractQualification(
      annotations: Sequence<KSAnnotation>
  ): BackstabComponent.Qualification? {
    for (annotation in annotations) {
      if (annotation.matches(ExternalTypes.JAVAX_INJECT_NAMED)) {
        return BackstabComponent.Qualification.Named(annotation.toAnnotationSpec())
      }
      if (isDaggerQualifier(annotation)) {
        return BackstabComponent.Qualification.Qualifier(annotation.toAnnotationSpec())
      }
    }
    return null
  }

  /** Returns true if the annotation is annotated with `@javax.inject.Qualifier`. */
  private fun isDaggerQualifier(annotation: KSAnnotation): Boolean {
    val type = annotation.annotationType.resolve()
    val declaration = type.declaration as? KSClassDeclaration ?: return false
    return declaration.annotations.any { it.matches(ExternalTypes.JAVAX_INJECT_QUALIFIER) }
  }

  /** Returns true if the annotation matches the given fully qualified class name. */
  private fun KSAnnotation.matches(fqcn: String): Boolean {
    val type = this.annotationType.resolve()
    if (type.isError) return false
    val declaration = type.declaration as? KSClassDeclaration ?: return false
    return declaration.qualifiedName?.asString() == fqcn
  }
}
