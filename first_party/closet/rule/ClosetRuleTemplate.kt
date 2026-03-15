package com.jackbradshaw.closet.rule

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.runner.Description
import org.junit.runners.model.Statement

abstract class ClosetRuleTemplate<T : AutoCloseable> : ClosetRule<T> {

  private val mutex = Mutex()
  private var cache: T? = null
  private var isClosed = false

  protected abstract fun initialiseResource(): T

  override fun get(): T = runBlocking {
    mutex.withLock {
      if (cache == null) {
        cache = initialiseResource()
        if (isClosed) cache?.close()
      }
      cache!!
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
              isClosed = true
              cache?.close()
            }
          }
        }
      }
    }
  }
}
