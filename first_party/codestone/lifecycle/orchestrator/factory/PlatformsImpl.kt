package com.jackbradshaw.codestone.lifecycle.orchestrator.factory

/** simple implementation of [Platforms]. */
class PlatformsImpl : Platforms {
  private val allPlatforms: Set<Platform<*>> by lazy {
    COMPONENTS_CLASSES.map {
          try {
             Class.forName(it).getMethod("create").invoke(null) as Platform<*>
          } catch (e: Exception) {
            // Expected when the platform is not in the classpath.
            null
          }
        }
        .filterNotNull()
        .toSet()
  }

  override fun getAll() = allPlatforms

  companion object {
    /* ktlint-disable max-line-length */
    private val COMPONENTS_CLASSES =
        setOf(
            "com.jackbradshaw.codestone.lifecycle.platforms.coroutines.CoroutinePlatform",
            "com.jackbradshaw.codestone.lifecycle.platforms.futures.FuturePlatform",
            "com.jackbradshaw.codestone.lifecycle.platforms.startstop.StartStopPlatform",
        )
    /* ktlint-enable max-line-length */
  }
}
