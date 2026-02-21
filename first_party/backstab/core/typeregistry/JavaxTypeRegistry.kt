package com.jackbradshaw.backstab.core.typeregistry

import javax.inject.Named
import javax.inject.Qualifier

/** Registry for types originating from the Javax library. */
object JavaxTypeRegistry {
  /**
   * The fully qualified name of the [Named] annotation.
   *
   * [Documentation](https://docs.oracle.com/javaee/7/api/javax/inject/Named.html)
   */
  val NAMED = Named::class

  /**
   * The fully qualified name of the [Qualifier] annotation.
   *
   * [Documentation](https://docs.oracle.com/javaee/7/api/javax/inject/Qualifier.html)
   */
  val QUALIFIER = Qualifier::class
}
