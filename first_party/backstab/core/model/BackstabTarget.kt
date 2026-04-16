package com.jackbradshaw.backstab.core.model

import com.jackbradshaw.backstab.core.annotations.Backstab
import com.jackbradshaw.oksp.model.SourceFile
import dagger.Component
import javax.inject.Named
import javax.inject.Qualifier

/**
 * A Dagger component annotated with [Backstab].
 *
 * Note: This class models a Dagger component but is not itself a dagger component.
 *
 * @property component The identity of the component declaration.
 * @property instantiator The mechanism used to instantiate the modeled component.
 */
data class BackstabTarget(
    val header: SourceFile,
    val component: Component,
    val instantiator: ComponentInstantiator
) {

  /** The identity of a component declaration. */
  data class Component(val packageName: String, val nameChain: List<String>)

  /** Sealed interface representing the instantiation strategy. */
  sealed interface ComponentInstantiator {

    /** Represents a component created via an implicit `create()` function. */
    data object CreateFunction : ComponentInstantiator

    /**
     * Represents a [Component.Builder] interface.
     *
     * @property setters Setters that supply dependencies to the component (usually `setFoo`).
     * @property buildFunction The function that builds the component (usually `build`)
     */
    data class BuilderInterface(
        val setters: List<SetterFunction>,
        val buildFunction: BuildFunction
    ) : ComponentInstantiator {

      /**
       * Represents a single function on the component's [Component.Builder] interface.
       *
       * Builder setters may only take a single parameter.
       *
       * Each instance corresponds to a dependency that needs to be supplied to the component via a
       * builder function call.
       *
       * @property name The name of the builder function.
       * @property type The type of the parameter.
       * @property qualification The qualification of the parameter, if any.
       */
      data class SetterFunction(val name: String, val type: Type, val qualifier: Qualifier? = null)

      /**
       * Represents the build function on the component's [Component.Builder] interface.
       *
       * @property name The name of the build function.
       * @property returnType The type of the component that is built.
       */
      data class BuildFunction(val name: String, val returnType: Type)
    }

    /**
     * Represents a [Component.Factory] interface.
     *
     * @property name The name of the factory function.
     * @property parameters The parameters of the factory function.
     */
    data class FactoryFunction(val name: String, val parameters: List<Parameter>) :
        ComponentInstantiator {

      /**
       * Represents a single parameter on the component's [Component.Factory] create function.
       *
       * @property type The type of the parameter.
       * @property qualification The qualification of the parameter, if any.
       */
      data class Parameter(val type: Type, val qualifier: Qualifier? = null)
    }
  }

  /** Represents a semantic qualifier (Named or custom Qualifier). */
  sealed interface Qualifier {
    /** A [Named] qualifier. */
    data class Named(val value: String) : Qualifier

    /** A custom [Qualifier] qualifier. */
    data class Custom(val packageName: String, val nameChain: List<String>) : Qualifier
  }
}
