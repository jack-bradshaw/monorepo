package com.jackbradshaw.closet.rule

import com.jackbradshaw.closet.ClosetScope
import com.jackbradshaw.closet.suspending.SuspendableClosable
import java.lang.AutoCloseable
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.runner.Description
import org.junit.runners.model.Statement

/** Default implementation of [AutoCloseRule]. */
@ClosetScope
class AutoCloseRuleImpl @Inject constructor() : AutoCloseRule {

  private val autoCloseables = ConcurrentHashMap.newKeySet<AutoCloseable>()

  private val suspendableClosables = ConcurrentHashMap.newKeySet<SuspendableClosable>()

  override fun <T : AutoCloseable> register(resource: T): T {
    autoCloseables.add(resource)
    return resource
  }

  override fun <T : SuspendableClosable> register(resource: T): T {
    suspendableClosables.add(resource)
    return resource
  }

  override fun apply(base: Statement, description: Description): Statement {
    return object : Statement() {
      override fun evaluate() {
        try {
          base.evaluate()
        } finally {
          closeAllRegisteredResources()
        }
      }
    }
  }

  private fun closeAllRegisteredResources() {
    val exceptions = mutableSetOf<Throwable>()

    runBlocking {
      suspendableClosables.forEach {
        try {
          it.close()
        } catch (e: Throwable) {
          exceptions.add(e)
        }
      }
    }
    autoCloseables.forEach {
      try {
        it.close()
      } catch (e: Throwable) {
        exceptions.add(e)
      }
    }

    if (exceptions.isNotEmpty())
        throw CompositeException("Some closables failed to close", exceptions)
  }
}
