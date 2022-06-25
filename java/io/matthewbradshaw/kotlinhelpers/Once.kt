package io.matthewbradshaw.kotlinhelpers

import java.util.concurrent.atomic.AtomicBoolean
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

interface Once {
  suspend fun runOnce()
}

fun once(block: suspend () -> Unit) = object : Once {
  private val mutex = Mutex()
  private val hasRun = AtomicBoolean(false)
  override suspend fun runOnce() {
    if (hasRun.compareAndSet(false, true)) block()
  }
}