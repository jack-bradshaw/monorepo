package io.jackbradshaw.klu.collections

import java.util.LinkedList
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class SimpleDoubleListBuffer<T> : DoubleListBuffer<T> {

  private val switchMutex = Mutex()

  private val list1 = LinkedList<T>()
  private val list2 = LinkedList<T>()
  private var activeList = list1

  override suspend fun getActive() = activeList
  override suspend fun getInactive() = if (activeList === list1) list2 else list1
  override suspend fun switch() {
    switchMutex.withLock { activeList = getInactive() }
  }
}