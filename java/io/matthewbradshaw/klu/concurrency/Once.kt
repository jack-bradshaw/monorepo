package io.matthewbradshaw.klu.concurrency

import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.sync.Mutex

/**
 * Contains a block of code that should be run exactly once.
 */
interface Once {
  /**
   * Runs the block if and only if it has never been run before. If the block has run before (or is currently running)
   * the function returns normally without error.
   */
  suspend fun runOnce()
}

/**
 * Defines a [block] of code that should be run exactly once, but does not invoke the code yet. Call [runOnce] on the
 * returned object to invoke the block. This function guarantees the wrapped code will never run more than once (in a
 * given process) even if [runOnce] is called multiple times asynchronously.
 *
 * Example:
 * ```
 * var x = 0
 * val setup = once {
 *   x +=1
 * }
 *
 * fun main() {
 *   setup.runOnce()
 *   setup.runOnce()
 *   println("$x") // prints 1
 * }
 * ```
 */
fun once(block: suspend () -> Unit) = object : Once {
  private val hasRun = AtomicBoolean(false)
  override suspend fun runOnce() {
    if (hasRun.compareAndSet(false, true)) block()
  }
}