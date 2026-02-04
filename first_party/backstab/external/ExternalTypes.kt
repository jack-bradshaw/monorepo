package com.jackbradshaw.backstab.external

import dagger.BindsInstance
import dagger.Component
import javax.inject.Named
import javax.inject.Qualifier

/**
 * Registry for the various types which originate from external libraries and are referenced by the
 * processor.
 */
object ExternalTypes {
  /** The fully qualified name of the Dagger Component annotation. */
  val DAGGER_COMPONENT = Component::class.qualifiedName!!

  /** The fully qualified name of the Dagger Component Builder annotation. */
  val DAGGER_COMPONENT_BUILDER = Component.Builder::class.qualifiedName!!

  /** The fully qualified name of the Dagger Component Factory annotation. */
  val DAGGER_COMPONENT_FACTORY = Component.Factory::class.qualifiedName!!

  /** The fully qualified name of the Dagger BindsInstance annotation. */
  val DAGGER_BINDS_INSTANCE = BindsInstance::class.qualifiedName!!

  /** The fully qualified name of the javax.inject.Named annotation. */
  val JAVAX_INJECT_NAMED = Named::class.qualifiedName!!

  /** The fully qualified name of the javax.inject.Qualifier annotation. */
  val JAVAX_INJECT_QUALIFIER = Qualifier::class.qualifiedName!!
}
