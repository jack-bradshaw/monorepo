package io.matthewbradshaw.merovingian.coroutines

import com.jme3.app.Application
import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

/**
 * Dispatcher for posting to the JMonkey Engine 3 application thread.
 */
class JMonkeyDispatcher(private val app: Application) : CoroutineDispatcher() {
  override fun dispatch(context: CoroutineContext, block: Runnable) {
    app.enqueue(block)
  }
}

/**
 * Map from application to dispatcher. Ensures safe concurrency and exactly one dispatcher per application.
 */
private val DISPATCHERS = ConcurrentHashMap<Application, JMonkeyDispatcher>()

/**
 * Gets a CoroutineDispatcher for this application. Every call for a given application returns the same instance, and
 * calls are thread safe.
 */
fun Application.dispatcher(): CoroutineDispatcher = DISPATCHERS.getOrPut(this) { JMonkeyDispatcher(this) }

