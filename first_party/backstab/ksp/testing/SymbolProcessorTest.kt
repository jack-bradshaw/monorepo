package com.jackbradshaw.backstab.ksp.testing

import com.google.devtools.ksp.symbol.KSAnnotated
import com.jackbradshaw.oksp.application.Application
import com.jackbradshaw.oksp.application.ApplicationComponent
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/** A base class for KSP tests that execute a set of test cases. */
abstract class SymbolProcessorTest : Application {

  override suspend fun onCreate(component: ApplicationComponent) {
    GlobalScope.launch {
      component.processingService().observeRoundStartEvents().first()
      val resolver = component.processingService().getRoundResolver()

      for ((name, executable) in supplyCases()) {
        try {
          executable(resolver)
        } catch (e: Throwable) {
          component.environment().logger.error("Test case $name failed. Error: ${e.message ?: e.toString()}")
        }
      }
      
      component.processingService().completeRound()
    }
  }

  override suspend fun onDestroy() {}



  protected abstract fun supplyCases(): Map<String, (com.google.devtools.ksp.processing.Resolver) -> Unit>
}
