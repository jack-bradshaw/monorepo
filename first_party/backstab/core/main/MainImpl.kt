package com.jackbradshaw.backstab.core.main

import com.jackbradshaw.backstab.core.CoreScope
import com.jackbradshaw.backstab.core.generator.Generator
import com.jackbradshaw.backstab.core.repository.Repository
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

/** Top-level coordinator for the Backstab annotation processor logic. */
@CoreScope
class MainImpl
@Inject
constructor(
    private val repository: Repository,
    private val generator: Generator,
) : Main {

  override suspend fun run() {
    generateBackstabModules()
  }

  /** Generates module for targets as they are emitted by [repository] and suspends indefinitely. */
  private suspend fun generateBackstabModules() {
    repository
        .observeTargets()
        .onEach { target ->
          val module =
              try {
                generator.generateModuleFor(target)
              } catch (error: Throwable) {
                repository.publishError(target, error)
                return@onEach
              }
          repository.publishModule(target, module)
        }
        .collect()
  }
}
