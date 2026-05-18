package com.jackbradshaw.closet.rule

import com.jackbradshaw.closet.suspending.SuspendableClosable
import java.lang.AutoCloseable
import org.junit.rules.TestRule

/**
 * Closes all registered resources during test tear down.
 *
 * Closure order is not guaranteed, but closure is guaranteed to occur sequentally (i.e. one
 * resource a time). If exceptions occur during closure, they are collated into a
 * [CompositeException] that is thrown after all other resources have been processed.
 * [SuspendableClosable]s are closed on the test thread.
 *
 * This rule is thread-safe and supports concurrent registration of resources.
 */
interface AutoCloseRule : TestRule {
  /** Registers a regular [AutoCloseable] resource to be closed. Returns the resource. */
  fun <T : AutoCloseable> register(resource: T): T

  /** Registers a [SuspendableClosable] resource to be closed. Returns the resource. */
  fun <T : SuspendableClosable> register(resource: T): T
}

/** An exception composed of multiple others. */
class CompositeException(message: String, val exceptions: Set<Throwable>) :
    RuntimeException(message)
