package com.jackbradshaw.obelisk.core.services

/**
 * Provides control over the underlying compiler plugin's execution lifecycle.
 */
interface ObeliskControlService {

  /** 
   * Starts the internal processing and observation loops. 
   * Should only be called once.
   */
  suspend fun allowStart()

  /**
   * Allows the compiler to terminate the process once all work is complete.
   */
  suspend fun allowEnd()

  /** Allows the compiler to move on to new data if multi-round, batched, or otherwise segmented
   * procesing is supported. 
   */
  suspend fun allowAdvance()

  /**
   * Aborts processing and allows the compiler to terminate immediately.
   */
  suspend fun forceAbort()
}
