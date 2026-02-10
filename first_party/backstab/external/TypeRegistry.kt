package com.jackbradshaw.backstab.external

import com.squareup.kotlinpoet.asClassName
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Qualifier

/**
 * Registry for types originating from the Dagger library.
 */
object DaggerTypeRegistry {
  /**
   * The fully qualified name of the Dagger `Component` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Component.html)
   */
  val COMPONENT = Component::class.qualifiedName!!

  /**
   * The fully qualified name of the Dagger `Component.Builder` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Component.Builder.html)
   */
  val COMPONENT_BUILDER = Component.Builder::class.qualifiedName!!

  /**
   * The fully qualified name of the Dagger `Component.Factory` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Component.Factory.html)
   */
  val COMPONENT_FACTORY = Component.Factory::class.qualifiedName!!

  /**
   * The fully qualified name of the Dagger `BindsInstance` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/BindsInstance.html)
   */
  val BINDS_INSTANCE = BindsInstance::class.qualifiedName!!

  /**
   * The [ClassName] of the Dagger `Module` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Module.html)
   */
  val MODULE = Module::class.asClassName()

  /**
   * The [ClassName] of the Dagger `Provides` annotation.
   *
   * [Documentation](https://dagger.dev/api/latest/dagger/Provides.html)
   */
  val PROVIDES = Provides::class.asClassName()
}

/**
 * Registry for types originating from the Javax library.
 */
object JavaxTypeRegistry {
  /**
   * The fully qualified name of the javax `Named` annotation.
   *
   * [Documentation](https://docs.oracle.com/javaee/7/api/javax/inject/Named.html)
   */
  val NAMED = Named::class.qualifiedName!!

  /**
   * The fully qualified name of the javax `Qualifier` annotation.
   *
   * [Documentation](https://docs.oracle.com/javaee/7/api/javax/inject/Qualifier.html)
   */
  val QUALIFIER = Qualifier::class.qualifiedName!!

  /**
   * The [ClassName] of the javax `Inject` annotation.
   *
   * [Documentation](https://docs.oracle.com/javaee/7/api/javax/inject/Inject.html)
   */
  val INJECT = Inject::class.asClassName()
}
