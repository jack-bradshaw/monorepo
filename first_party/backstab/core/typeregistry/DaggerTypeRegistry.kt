package com.jackbradshaw.backstab.core.typeregistry

import dagger.Component
import dagger.Module
import dagger.Provides

/** Registry for types originating from the Dagger library. */
object DaggerTypeRegistry {
  /**
   * The fully qualified name of the Dagger `Component` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Component.html)
   */
  val COMPONENT = Component::class

  /**
   * The fully qualified name of the Dagger `Component.Builder` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Component.Builder.html)
   */
  val COMPONENT_BUILDER = Component.Builder::class

  /**
   * The fully qualified name of the Dagger `Component.Factory` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Component.Factory.html)
   */
  val COMPONENT_FACTORY = Component.Factory::class

  /**
   * The fully qualified name of the Dagger `Module` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Module.html)
   */
  val MODULE = Module::class

  /**
   * The fully qualified name of the Dagger `Provides` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Provides.html)
   */
  val PROVIDES = Provides::class
}
