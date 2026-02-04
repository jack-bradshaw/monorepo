package com.jackbradshaw.backstab.processor.model

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.TypeName

/**
 * Represents the parsed data of a Dagger component annotated with @Backstab.
 *
 * This model serves as the intermediate representation between the KSP AST and the code generation
 * logic, decoupling the two.
 */
/**
 * @property packageName The package name of the component.
 * @property names The name path of the component (e.g., ["Outer", "Inner"]).
 * @property instantiator The mechanism used to instantiate the component.
 */
data class BackstabComponent(
    val packageName: String,
    val names: List<String>,
    val instantiator: ComponentInstantiator
) {

  /** The simple name of the component. */
  val name: String
    get() = names.last()

  /** Sealed interface representing the instantiation strategy. */
  sealed interface ComponentInstantiator

  /** Represents a component created via an implicit `create()` method. */
  data object Create : ComponentInstantiator

  /**
   * Represents a `@Component.Builder` interface.
   *
   * @property buildFunction The function that builds the component.
   * @property componentBindings Bindings that return the component type (e.g. `fun foo(foo: Foo):
   *   Builder`).
   * @property instanceBindings Bindings annotated with `@BindsInstance`.
   */
  data class Builder(
      val buildFunction: BuilderFunction,
      val componentBindings: List<BuilderFunction>,
      val instanceBindings: List<BuilderFunction>
  ) : ComponentInstantiator

  /**
   * Represents a `@Component.Factory` interface.
   *
   * @property functionName The name of the factory function.
   * @property parameters The parameters of the factory function.
   */
  data class Factory(val functionName: String, val parameters: List<FactoryParameter>) :
      ComponentInstantiator

  /** Represents a semantic qualification (Named or custom Qualifier). */
  sealed interface Qualification {
    /** The annotation spec for the qualification. */
    val annotation: AnnotationSpec
    /** A `@Named` qualification. */
    data class Named(override val annotation: AnnotationSpec) : Qualification
    /** A custom `@Qualifier` qualification. */
    data class Qualifier(override val annotation: AnnotationSpec) : Qualification
  }

  /**
   * Represents a single parameter on the component's `@Component.Factory` create function.
   *
   * @property paramType The type of the parameter.
   * @property qualification The qualification of the parameter, if any.
   */
  data class FactoryParameter(val paramType: TypeName, val qualification: Qualification? = null)

  /**
   * Represents a single function on the component's `@Component.Builder` interface.
   *
   * Each instance corresponds to a dependency that needs to be supplied to the component via a
   * builder function call.
   *
   * @property functionName The name of the builder function.
   * @property paramType The type of the parameter.
   * @property qualification The qualification of the parameter, if any.
   */
  data class BuilderFunction(
      val functionName: String,
      val paramType: TypeName,
      val qualification: Qualification? = null
  )
}
