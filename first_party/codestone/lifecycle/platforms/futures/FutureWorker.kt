package com.jackbradshaw.codestone.lifecycle.platforms.futures

import com.google.common.util.concurrent.ListenableFuture
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import kotlin.reflect.typeOf

fun listenableFutureWork(handle: ListenableFuture<Unit>) = object : Work<ListenableFuture<Unit>> {
  override val handle = handle
  override val workType = typeOf<ListenableFuture<Unit>>()
}