package io.jackbradshaw.codestone.sustainment.omnisustainer.factoring

/** simple implementation of [Platforms]. */
class PlatformsSimplex : Platforms {
  private val allPlatforms: Set<Platform<*>> by lazy {
    COMPONENTS_CLASSES.map {
          try {
            val clazz = Class.forName(it)
            clazz.getMethod("create").invoke(null) as Platform<*>
          } catch (e: ClassNotFoundException) {
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
            "io.jackbradshaw.codestone.sustainment.omnisustainer.factoring.KtCoroutinePlatform",
            "io.jackbradshaw.codestone.sustainment.omnisustainer.facoring.ListenableFuturePlatform",
            "io.jackbradshaw.codestone.sustainment.omnisustainer.factoring.StartStopPlatform",
        )
    /* ktlint-enable max-line-length */
  }
}
