package com.jackbradshaw.coroutines.testing.interceptor

import kotlinx.coroutines.CoroutineDispatcher
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext

class InterceptorDispatcherImpl(private val delegate: CoroutineDispatcher) : InterceptorDispatcher() {

  private val running = ConcurrentHashMap.newKeySet<Runnable>()

  override fun dispatch(context: CoroutineContext, block: Runnable) {
    val wrapper = object : Runnable {
      override fun run() {
        running.add(this)
        try {
          block.run()
        } finally {
          running.remove(this)
        }
      }
    }

    delegate.dispatch(context, wrapper)
  }

  override fun isDispatchNeeded(context: CoroutineContext) = delegate.isDispatchNeeded(context)

  override fun dispatchYield(context: CoroutineContext, block: Runnable) {
    val wrapper = object : Runnable {
      override fun run() {
        running.add(this)
        try {
          block.run()
        } finally {
          running.remove(this)
        }
      }
    }

    delegate.dispatchYield(context, wrapper)
  }

  override fun isIdle() = running.isEmpty()
}