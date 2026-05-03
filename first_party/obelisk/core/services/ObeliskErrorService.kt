package com.jackbradshaw.obelisk.core.services

/**
 * Provides fatal error reporting to the underlying code generation engine.
 */
interface ObeliskErrorService<A> {

  /**
   * Publishes a fatal [error] to the underlying logger.
   */
  suspend fun fail(error: Throwable, anchor: A? = null)

  /**
   * Publishes a fatal [error] message to the underlying logger, optionally anchored 
   * to a specific [anchor].
   */
  suspend fun fail(error: String, anchor: A? = null)
}
