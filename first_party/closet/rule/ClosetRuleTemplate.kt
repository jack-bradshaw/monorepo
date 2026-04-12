package com.jackbradshaw.closet.rule

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.runner.Description
import org.junit.runners.model.Statement

/** A thread-safe abstract base implementation of [ClosetRule]. */
abstract class ClosetRuleTemplate<T : AutoCloseable> : ClosetRule<T> {

  /** Guards [cache] and [isPostTest]. */
  private val mutex = Mutex()

  /** Stores the value from [initialiseResource] after first access. */
  private var cache: T? = null

  /** Whether the test has entered the tear-down stage. */
  private var isPostTest = false

  /** Produces the resource to be managed by this rule. */
  protected abstract fun initialiseResource(): T

  override fun get(): T = runBlocking {
    mutex.withLock {
      require(!isPostTest) { "Cannot use get() after test case has run." }

      val localCache = cache
      if (localCache == null) {
        val resource = initialiseResource()
        cache = resource
        resource
      } else {
        localCache
      }
    }
  }

  override fun apply(base: Statement, description: Description): Statement {
    return object : Statement() {
      override fun evaluate() {
        try {
          base.evaluate()
        } finally {
          runBlocking {
            mutex.withLock {
              isPostTest = true
              cache?.close()
              cache = null
            }
          }
        }
      }
    }
  }
}
