package com.jackbradshaw.closet.rule

import org.junit.rules.TestRule

/**
 * A JUnit test rule that manages the lifecycle of an [AutoCloseable] resource.
 *
 * The underlying resource (provided by [get]) is automatically closed upon completion of the test,
 * and the test will not exit until the resource has completed closure (by returning from `close`).
 */
interface ClosetRule<out T : AutoCloseable> : TestRule {
  /** Retrieves the resource managed by this rule. */
  fun get(): T
}
