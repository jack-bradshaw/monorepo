package io.matthewbradshaw.klu.concurrency

import java.util.concurrent.atomic.AtomicBoolean

/**
 * Wraps a block of code and ensures it runs at most one time (even if invoked asynchronously). Call [runIfNeverRun] to
 * try running the block.
 */
interface Once {
  /**
   * Runs the block if and only if it has never been run before. If the block has run before (or is currently running)
   * the function returns normally without error.
   */
  suspend fun runIfNeverRun()
}

/**
 * Defines a new [Once] that wraps the supplied [block].
 *
 * Example:
 * ```
 * var x = 0
 * val setup = once {
 *   x += 1
 * }
 *
 * println("$x") // Will print 0
 *
 * setup.runIfNeverRun()
 * setup.runIfNeverRun()
 * setup.runIfNeverRun()
 *
 * println("$x") // Will print 1
 * ```
 */
fun once(block: suspend () -> Unit) = object : Once {
  private val hasRun = AtomicBoolean(false)
  override suspend fun runIfNeverRun() {
    if (hasRun.compareAndSet(false, true)) block()
  }
}