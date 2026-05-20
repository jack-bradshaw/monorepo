package com.jackbradshaw.sealant

import com.jackbradshaw.sealant.hub.SealedHub
import com.jackbradshaw.sealant.source.SealedSource

/** Infrastructure for leak-free Kotlin flows. */
interface SealantComponent {
  /** Provides a [SealedHub.Factory]. The same instance must be returned each time. */
  fun sealedHubFactory(): SealedHub.Factory

  /** Provides a [SealedSource.Factory]. The same instance must be returned each time. */
  fun sealedSourceFactory(): SealedSource.Factory
}
