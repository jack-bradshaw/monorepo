package com.jackbradshaw.codestone.lifecycle.orchestrator

import com.jackbradshaw.codestone.lifecycle.orchestrator.WorkOrchestrator
import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.work.Work
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.conversion.multiconverter.MultiConverter
import com.jackbradshaw.codestone.lifecycle.startstop.StartStopImpl
import com.jackbradshaw.codestone.lifecycle.platforms.startstop.startStopWork
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.CoroutineScope

// TODO (jackbradshaw): Refactor this class to avoid flows
/** A simple implementation of [WorkOrchestrator]. */
class WorkOrchestratorImpl<O : Work<*>>
@Inject
constructor(
    private val inputConverter: MultiConverter<Work<StartStop<*, *>>>,
    private val outputConverter: MultiConverter<O>,
    private val integrationScope: CoroutineScope
) : WorkOrchestrator<O> {

  private val toSustain = MutableSharedFlow<Worker<*>>(replay = Int.MAX_VALUE)
  private val toRelease = MutableSharedFlow<Worker<*>>(replay = Int.MAX_VALUE)
  private val toReleaseAll = MutableSharedFlow<Unit>(replay = Int.MAX_VALUE)

  private val activeOperations = ConcurrentHashMap<Worker<*>, StartStop<*, *>>()

  override fun orchestrate(lifecycle: Worker<*>) {
    toSustain.tryEmit(lifecycle)
  }

  override fun orchestrateAll(lifecycles: Collection<Worker<*>>) {
    lifecycles.forEach { orchestrate(it) }
  }

  override fun release(lifecycle: Worker<*>) {
    toRelease.tryEmit(lifecycle)
  }

  override fun releaseAll() {
    toReleaseAll.tryEmit(Unit)
  }

  override val work: O =
      outputConverter.convert(
          startStopWork(
              object : StartStopImpl<Unit, Throwable>() {
            init {
              onStart {
                 integrationScope.launch {
                   launch {
                     toSustain.collect {
                       val converted = inputConverter.convert(it.work)
                       activeOperations[it] = converted.handle
                       converted.handle.start()
                     }
                   }

                   launch {
                     toRelease.collect {
                       activeOperations[it]?.abort()
                       activeOperations.remove(it)
                     }
                   }

                   launch { toReleaseAll.collect { cancelAllOperations() } }

                   suspendCancellableCoroutine<Unit> { it.invokeOnCancellation { cancelAllOperations() } }
                 }
              }
              onStop {
                  cancelAllOperations()
              }
            }
          }))

  private fun cancelAllOperations() {
    for (task in activeOperations.values) task.abort()
    activeOperations.clear()
  }
}
