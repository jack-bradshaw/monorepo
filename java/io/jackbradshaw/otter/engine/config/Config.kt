package io.jackbradshaw.otter.engine.config

/**
 * Engine configuration.
 *
 * - [xrEnabled] Enables and disables XR integration. Defaults to false.
 * - [headlessEnabled] Enables and disables headless mode (i.e. no GUI). Defaults to false.
 * - [debugEnabled] Enables and disables various debugging options.
 */
data class Config(
  val xrEnabled: Boolean = false,
  val headlessEnabled: Boolean = false,
  val debugEnabled: Boolean = false
)