package io.jackbradshaw.otter.engine.config

/**
 * Engine configuration.
 *
 * - [vrEnabled] Enables and disables VR integration. Defaults to false.
 * - [headlessEnabled] Enables and disables headless mode (i.e. no GUI). Defaults to false.
 * - [debugEnabled] Enables and disables various debugging options.
 */
data class Config(
  val vrEnabled: Boolean = false,
  val headlessEnabled: Boolean = false,
  val debugEnabled: Boolean = false
)