package com.jackbradshaw.coroutines

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineDispatcher

/** Provides infrastructure for CPU and IO bound coroutines. */
interface CoroutinesComponent {
  /** Provides a [CoroutineContext] optimised for IO-bound work. */
  @Io fun ioContext(): CoroutineContext

  /** Provides a [CoroutineDispatcher] optimised for IO-bound work. */
  @Io fun ioDispatcher(): CoroutineDispatcher

  /** Provides a [CoroutineContext] optimised for CPU-bound work. */
  @Cpu fun cpuContext(): CoroutineContext

  /** Provides a [CoroutineDispatcher] optimised for CPU-bound work. */
  @Cpu fun cpuDispatcher(): CoroutineDispatcher
}
