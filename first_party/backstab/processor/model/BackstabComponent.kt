package com.jackbradshaw.backstab.processor.model

import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.TypeName

/**
 * Model of a Dagger component annotated with @Backstab.
 * 
 * Note: This class models a Dagger component but is not itself a dagger component.
 *
 * @property className The class name of the modeled component.
 * @property instantiator The mechanism used to instantiate the modeled component.
 */
data class BackstabComponent(
    val className: ClassName,
    val instantiator: ComponentInstantiator
) {

  /** The name of the class Dagger generates from this component.
   * 
   * Example: Given `interface Foo { @Component interface Bar {} }` the name is `DaggerFoo_Bar`.
   */
  val generatedDaggerComponentName: ClassName
    get() {
      val daggerName = "Dagger" + className.simpleNames.joinToString("_")
      return ClassName(className.packageName, daggerName)
    }

  /** Sealed interface representing the instantiation strategy. */
  sealed interface ComponentInstantiator {

    /** Represents a component created via an implicit `create()` function. */
    data object CreateFunction : ComponentInstantiator

    /**
     * Represents a `@Component.Builder` interface.
     *
     * @property componentSetters Setters that return the component type (e.g. `fun foo(foo: Foo):
     *   Builder`).
     * @property boundInstanceSetters Setters annotated with `@BindsInstance`.
     * @property buildFunction The function that builds the component.
     */
    data class BuilderInterface(
        val componentSetters: List<SetterFunction>,
        val boundInstanceSetters: List<SetterFunction>,
        val buildFunction: BuildFunction
    ) : ComponentInstantiator

    /**
     * Represents a single function on the component's `@Component.Builder` interface.
     * 
     * Builder setters may only take a single paremeter.
     *
     * Each instance corresponds to a dependency that needs to be supplied to the component via a
     * builder function call.
     *
     * @property name The name of the builder function.
     * @property type The type of the parameter.
     * @property qualification The qualification of the parameter, if any.
     */
    data class SetterFunction(
        val name: MemberName,
        val type: TypeName,
        val qualification: Qualification? = null
    )

    /**
     * Represents the build function on the component's `@Component.Builder` interface.
     *
     * @property name The name of the build function.
     * @property returnType The type of the component that is built.
     */
    data class BuildFunction(
        val name: MemberName,
        val returnType: TypeName
    )

    /**
     * Represents a `@Component.Factory` interface.
     *
     * @property name The name of the factory function.
     * @property parameters The parameters of the factory function.
     */
    data class FactoryFunction(
        val name: MemberName,
        val parameters: List<Parameter>
    ) : ComponentInstantiator {

      /**
       * Represents a single parameter on the component's `@Component.Factory` create function.
       *
       * @property type The type of the parameter.
       * @property qualification The qualification of the parameter, if any.
       */
      data class Parameter(val type: TypeName, val qualification: Qualification? = null)
    }
  }

  /** Represents a semantic qualification (Named or custom Qualifier). */
  sealed interface Qualification {
    /** The annotation spec for the qualification. */
    val annotation: AnnotationSpec

    /** A `@Named` qualification. */
    data class Named(override val annotation: AnnotationSpec) : Qualification
    
    /** A custom `@Qualifier` qualification. */
    data class Qualifier(override val annotation: AnnotationSpec) : Qualification
  }
}
