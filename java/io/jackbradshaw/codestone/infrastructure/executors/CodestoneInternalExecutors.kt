package io.jackbradshaw.codestone.infrastructure.executors

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO (jackbradshaw): Delete this class, it shouldn't exist. Move definitions to each platforms.

/** Executors for codestone internals. */
object CodestoneInternalExecutors {
  /** Executor for use in sustainment internals. */
  val forsustainment: ExecutorService by lazy { Executors.newCachedThreadPool() }
}
