package io.jackbradshaw.klu.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.withLock

class NiceFlower<T>(initialValue: T, private val onChange: suspend (T) -> Unit = {}) : MutableFlower<T> {

  private val coreGuard = Mutex()
  private val core = MutableStateFlow(initialValue)

  init {
    runBlocking {
      onChange(initialValue)
    }
  }

  override suspend fun set(t: T) = coreGuard.withLock {
    if (core.value == t) return
    core.value = t
    onChange(t)
  }

  override suspend fun get(): T = core.value

  override suspend fun transform(transform: suspend (T) -> T): T {
    coreGuard.withLock {
      set(transform(core.value))
      return core.value
    }
  }

  override fun asFlow(): Flow<T> = core
}