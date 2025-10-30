package com.jackbradshaw.coroutines.testing.launcher

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job

interface Launcher {

  fun launchEagerly(block: suspend CoroutineScope.() -> Unit): Job

  fun launchDeferred(block: suspend CoroutineScope.() -> Unit): Job

  fun <T> asyncEagerly(block: suspend CoroutineScope.() -> T): Deferred<T>

  fun <T> asyncDeferred(block: suspend CoroutineScope.() -> T): Deferred<T>
}
