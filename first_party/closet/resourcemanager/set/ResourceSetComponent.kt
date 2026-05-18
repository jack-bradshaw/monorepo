package com.jackbradshaw.closet.resourcemanager.set

/** Provides a [ResourceSet.Factory]. */
interface ResourceSetComponent {
  /** Provides a [ResourceSet.Factory]. Calls are idempotent and return the same instance. */
  fun resourceSetFactory(): ResourceSet.Factory
}
