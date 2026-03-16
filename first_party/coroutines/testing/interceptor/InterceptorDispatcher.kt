package com.jackbradshaw.coroutines.testing.interceptor

import kotlinx.coroutines.CoroutineDispatcher

abstract class InterceptorDispatcher : CoroutineDispatcher() {
  /** Whether the dispatcher is presently unoccupied by work. */
  abstract fun isIdle(): Boolean

  interface Factory {
    fun create(delegate: CoroutineDispatcher): InerceptorDispatcher
  }
}