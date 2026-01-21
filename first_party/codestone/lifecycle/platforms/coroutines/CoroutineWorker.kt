package com.jackbradshaw.codestone.lifecycle.platforms.coroutines

import kotlinx.coroutines.Job
import kotlin.reflect.typeOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work

fun ktCoroutineWork(handle: Job) = object : Work<Job> {
  override val handle = handle
  override val workType = typeOf<Job>()
}

fun ktCoroutineWorker(
  scope: CoroutineScope, 
  block: suspend CoroutineScope.() -> Unit
): Worker<Work<Job>> {
  val job = scope.launch(start = CoroutineStart.LAZY, block = block)
  return object : Worker<Work<Job>> {
    override val work = object : Work<Job> {
      override val handle = job
      override val workType = typeOf<Job>()
    }
  }
}
