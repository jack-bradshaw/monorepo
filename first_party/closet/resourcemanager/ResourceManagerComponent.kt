package com.jackbradshaw.closet.resourcemanager

/** Provides a [ResourceManagerFactory]. */
interface ResourceManagerComponent {
  /** Provides a [ResourceManagerFactory]. Calls are idempotent and return the same instance. */
  fun resourceManagerFactory(): ResourceManagerFactory
}
