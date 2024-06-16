package io.jackbradshaw.queen.infrastructure.executors

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO (jackbradshaw): Delete this class, it shouldn't exist. Move definitions to each platforms.

/** Executors for queen internals. */
object QueenInternalExecutors {
  /** Executor for use in sustainment internals. */
  val forsustainment: ExecutorService by lazy { Executors.newCachedThreadPool() }
}
