package io.matthewbradshaw.kotty.concurrency

import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.sync.Mutex

/**
 * A block of code that should be run exactly once.
 */
interface Once {
  /**
   * Runs the block if and only if it has never been run before. If the block has run before (or is currently running)
   * the function returns normally without error.
   */
  suspend fun runOnce()
}

/**
 * Creates a block of code that should be run exactly once, but does not run the block yet. Call [runOnce] on the
 * returned object to invoke the code. This function guarantees that the wrapped code will never run more than once
 * even when [runOnce] is called in parallel.
 *
 * Example:
 * ```
 * val setup = once {
 *   // set some variables
 * }
 *
 * setup.runOnce() // runs
 * setup.runOnce() // does not run
 * ```
 */
fun once(block: suspend () -> Unit) = object : Once {
  private val hasRun = AtomicBoolean(false)
  override suspend fun runOnce() {
    if (hasRun.compareAndSet(false, true)) block()
  }
}