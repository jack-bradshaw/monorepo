package com.jackbradshaw.closet.resourcemanager.map

/** Provides a [ResourceMap.Factory]. */
interface ResourceMapComponent {
  /** Provides a [ResourceMap.Factory]. Calls are idempotent and return the same instance. */
  fun resourceMapFactory(): ResourceMap.Factory
}
