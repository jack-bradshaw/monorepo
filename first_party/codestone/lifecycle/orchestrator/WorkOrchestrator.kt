package com.jackbradshaw.codestone.lifecycle.orchestrator

import com.jackbradshaw.codestone.lifecycle.worker.Worker
import com.jackbradshaw.codestone.lifecycle.startstop.StartStop
import com.jackbradshaw.codestone.lifecycle.work.Work

/**
 * A [Worker] which is the aggregation of multiple other workers.
 *
 * The scope of aggregation is defined by the implementation.
 */
interface WorkOrchestrator<O : Work<*>> : Worker<O> {
  fun orchestrate(lifecycle: Worker<*>)
  fun orchestrateAll(lifecycles: Collection<Worker<*>>)
  fun release(lifecycle: Worker<*>)
  fun releaseAll()
}
