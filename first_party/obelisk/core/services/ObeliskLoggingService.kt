package com.jackbradshaw.obelisk.core.services

import com.jackbradshaw.obelisk.core.model.LogLevel

/** Provides informational logging to the underlying code generation engine. */
interface ObeliskLoggingService<R> {

  /**
   * Publishes a [message] with a specific [level] to the underlying logger, optionally anchored to
   * the [anchor].
   */
  suspend fun log(message: String, level: LogLevel?, anchor: R? = null)
}
