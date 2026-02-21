package com.jackbradshaw.backstab.ksp.testing

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated

/** A base class for KSP tests that execute a set of test cases. */
abstract class SymbolProcessorTest(private val env: SymbolProcessorEnvironment) : SymbolProcessor {
  private var finished = false

  override fun process(resolver: Resolver): List<KSAnnotated> {
    if (finished) return emptyList()

    for ((name, executable) in supplyCases()) {
      try {
        executable(resolver)
      } catch (e: Throwable) {
        env.logger.error("Test case $name failed. Error: ${e.message ?: e.toString()}")
      }
    }

    finished = true
    return emptyList()
  }

  protected abstract fun supplyCases(): Map<String, (Resolver) -> Unit>
}
