package com.jackbradshaw.closet.resourcemanager

/** Provides a [ResourceManager.Factory]. */
interface ResourceManagerComponent {
  /** Provides a [ResourceManager.Factory]. Calls are idempotent and return the same instance. */
  fun resourceManagerFactory(): ResourceManager.Factory
}
