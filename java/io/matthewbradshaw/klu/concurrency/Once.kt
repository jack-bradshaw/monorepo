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
  operator suspend fun invoke()

  suspend fun hasRun(): Boolean
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
 * for (i in 0..10) launch { setup.runIfNeverRun() }
 *
 * println("$x") // Will print 1
 * ```
 */
fun once(block: suspend () -> Unit) = object : Once {
  private val hasRun = AtomicBoolean(false)

  override operator suspend fun invoke() {
    if (hasRun.compareAndSet(false, true)) block()
  }

  override suspend fun hasRun() = hasRun.get()
}

fun Once.onSubsequentRuns(block: suspend () -> Throwable) = object : Once {
  private val hasRun = AtomicBoolean(false)

  override operator suspend fun invoke() {
    if (hasRun.compareAndSet(false, true)) this@onSubsequentRuns.invoke() else block()
  }

  override suspend fun hasRun() = hasRun.get()
}

fun Once.errorOnSubsequentRuns(throwable: Throwable) = onSubsequentRuns { throw throwable }

fun Once.errorOnSubsequentRuns(message: String) = errorOnSubsequentRuns(IllegalStateException(message))

fun Once.errorOnSubsequentRuns() = errorOnSubsequentRuns("once() blocks must only be called once.")