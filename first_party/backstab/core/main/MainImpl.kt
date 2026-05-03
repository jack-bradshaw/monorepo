package com.jackbradshaw.backstab.core.main

import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.generator.Generator
import com.jackbradshaw.obelisk.core.services.ObeliskControlService
import com.jackbradshaw.obelisk.core.services.ObeliskDataService
import com.jackbradshaw.obelisk.core.services.ObeliskErrorService
import com.jackbradshaw.backstab.core.model.BackstabTarget
import com.jackbradshaw.backstab.core.model.BackstabModule
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.CoroutineStart

/** Top-level coordinator for the Backstab annotation processor logic. */
@CoreScope
class MainImpl
@Inject
constructor(
    private val dataService: ObeliskDataService<BackstabTarget, BackstabModule>,
    private val controlService: ObeliskControlService,
    private val errorService: ObeliskErrorService<BackstabTarget>,
    private val generator: Generator,
) : Main {

  override suspend fun run() {
    coroutineScope {
      launch(start = CoroutineStart.UNDISPATCHED) {
        generateBackstabModules()
      }
      controlService.allowStart()
    }
  }

  /** Generates module for targets as they are emitted by [dataService] and suspends indefinitely. */
  private suspend fun generateBackstabModules() {
    val sluice = dataService.createSluice()
    sluice.flow
        .onEach { target ->
          val module =
              try {
                generator.generateModuleFor(target)
              } catch (error: Throwable) {
                errorService.fail(error, target)
                return@onEach
              }
          dataService.publish(module, setOf(target))
        }
        .collect()
  }
}
