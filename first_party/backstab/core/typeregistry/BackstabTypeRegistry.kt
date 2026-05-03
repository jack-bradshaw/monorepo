package com.jackbradshaw.backstab.core.typeregistry

import com.jackbradshaw.backstab.core.annotations.AggregateScope
import com.jackbradshaw.backstab.core.annotations.Backstab

/** Registry for types originating from the Backstab library. */
object BackstabTypeRegistry {
  /** The fully qualified name of the Backstab `Backstab` annotation. */
  val BACKSTAB = Backstab::class

  /** The fully qualified name of the Backstab `AggregateScope` annotation. */
  val AGGREGATE_SCOPE = AggregateScope::class
}
