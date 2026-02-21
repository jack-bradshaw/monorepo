package com.jackbradshaw.backstab.core.main

import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.generator.Generator
import com.jackbradshaw.backstab.core.ports.errorsink.ErrorSink
import com.jackbradshaw.backstab.core.ports.modulesink.ModuleSink
import com.jackbradshaw.backstab.core.ports.targetsource.TargetSource
import com.jackbradshaw.coroutines.io.Io
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/** Top-level coordinator for the Backstab annotation processor logic. */
@CoreScope
class MainImpl
@Inject
constructor(
    private val targetSource: TargetSource,
    private val moduleSink: ModuleSink,
    private val errorSink: ErrorSink,
    private val generator: Generator,
    @Io private val coroutineScope: CoroutineScope,
) : Main {

  private lateinit var job: Job

  override fun start() {
    check(!this::job.isInitialized) { "Main is already started, cannot start again." }

    job = coroutineScope.launch { generateModules() }
  }

  override fun stop() {
    if (this::job.isInitialized) job.cancel()
  }

  /**
   * Observes inbound sources, generates modules for them, and publishes the modules. Suspends
   * indefinitely.
   */
  private suspend fun generateModules() {
    targetSource
        .observeTargets()
        .onEach { target ->
          try {
            val module = generator.generateModuleFor(target)
            moduleSink.publishModules(target, listOf(module))
          } catch (t: Throwable) {
            errorSink.publishError(target, t)
          }
        }
        .collect()
  }
}
