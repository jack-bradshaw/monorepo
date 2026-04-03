package com.jackbradshaw.coroutines

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher

/** Provides infrastructure for CPU and IO bound coroutines. */
interface CoroutinesComponent {
  /** Provides a [CoroutineContext] that is well suited to IO-bound work. */
  @Io fun ioContext(): CoroutineContext

  /** Provides a [CoroutineDispatcher] that is well suited to IO-bound work. */
  @Io fun ioDispatcher(): CoroutineDispatcher

  /** Provides a [CoroutineContext] that is well suited to CPU-bound work. */
  @Cpu fun cpuContext(): CoroutineContext

  /** Provides a [CoroutineDispatcher] that is well suited to CPU-bound work. */
  @Cpu fun cpuDispatcher(): CoroutineDispatcher
}
