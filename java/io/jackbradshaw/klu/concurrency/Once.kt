package io.jackbradshaw.klu.concurrency

import java.util.concurrent.atomic.AtomicBoolean

/** An operation that runs at most once. */
interface Once {
  /** Runs the operation if not already run (or running). */
  suspend operator fun invoke()

  /** Whether the operation has run (or is running). */
  suspend fun hasRun(): Boolean
}

/**
 * Defines an operation that runs exactly once.
 *
 * Example:
 * ```
 * val setup = once {
 *   foo()
 *   println("completed setup")
 * }
 *
 * setup() // prints "completed setup"
 * setup() // doesn't print anything
 * ```
 */
fun once(operation: suspend () -> Unit) =
    object : Once {
      private val hasRun = AtomicBoolean(false)

      override suspend operator fun invoke() {
        if (hasRun.compareAndSet(false, true)) operation()
      }

      override suspend fun hasRun() = hasRun.get()
    }

/**
 * Defines a [Once] that throws the error returned by [errorGenerator] when invoked more than once.
 *
 * Example:
 * ```
 * val setup = once {
 *   foo()
 *   println("completed setup")
 * }.throwing { IllegalStateException("can't setup twice") }
 *
 * setup() // prints "completed setup"
 * setup() // throws exception
 * ```
 */
fun Once.throwing(errorGenerator: suspend () -> Throwable) =
    object : Once {
      private val hasRun = AtomicBoolean(false)

      override suspend operator fun invoke() {
        if (hasRun.compareAndSet(false, true)) this@throwing.invoke() else throw errorGenerator()
      }

      override suspend fun hasRun() = hasRun.get()
    }

/**
 * Defines a [Once] that throws [error] when invoked more than once.
 *
 * Example:
 * ```
 * val setup = once {
 *   foo()
 *   println("completed setup")
 * }.throwing(IllegalStateException("can't setup twice"))
 *
 * setup() // prints "completed setup"
 * setup() // throws exception
 * ```
 */
fun Once.throwing(error: Throwable) = throwing { error }

/**
 * Defines a [Once] that throws an IllegalStateException containing [message] when invoked more than
 * once.
 *
 * Example:
 * ```
 * val setup = once {
 *   foo()
 *   println("completed setup")
 * }.throwing("can't setup twice")
 *
 * setup() // prints "completed setup"
 * setup() // throws exception
 * ```
 */
fun Once.throwing(message: String) = throwing(IllegalStateException(message))

/**
 * Defines a [Once] that throws an IllegalStateException when invoked more than once.
 *
 * Example:
 * ```
 * val setup = once {
 *   foo()
 *   println("completed setup")
 * }.throwing { IllegalStateException("can't setup twice") }
 *
 * setup() // prints "completed setup"
 * setup() // throws exception
 * ```
 */
fun Once.throwing() = throwing("A once block was called multiple times.")
