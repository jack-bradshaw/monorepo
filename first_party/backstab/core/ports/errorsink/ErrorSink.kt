package com.jackbradshaw.backstab.core.ports.errorsink

import com.jackbradshaw.backstab.core.model.BackstabTarget

/** Provides a way to report error associated with a [BackstabTarget]. */
interface ErrorSink {
  /** Publishes an [error] associated with the given [target]. */
  suspend fun publishError(target: BackstabTarget, error: Throwable)
}
