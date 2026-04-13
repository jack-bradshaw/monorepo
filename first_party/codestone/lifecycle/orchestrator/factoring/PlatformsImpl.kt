package com.jackbradshaw.codestone.lifecycle.orchestrator.factoring

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
            "com.jackbradshaw.codestone.lifecycle.orchestrator.factoring.CoroutinePlatform",
            "com.jackbradshaw.codestone.lifecycle.orchestrator.factoring.ListenableFuturePlatform",
            "com.jackbradshaw.codestone.lifecycle.orchestrator.factoring.StartStopPlatform",
        )
    /* ktlint-enable max-line-length */
  }
}
