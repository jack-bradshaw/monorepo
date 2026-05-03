package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated

/**
 * A [SymbolProcessor] that tracks whether [process] was invoked, generates a file during the first
 * round, and throws a [RuntimeException] with the message "Exception after generation" during the
 * second round.
 */
class ExceptionThrowingAndCodeGeneratingProcessor(
    private val environment: SymbolProcessorEnvironment
) : SymbolProcessor {

  /** Number of times [process] was invoked. */
  var roundCount = 0
    private set
    
  /** Whether [process] was invoked at least once. */
  var didRunProcess = false
    private set

  override fun process(resolver: Resolver): List<KSAnnotated> {
    roundCount++
    didRunProcess = true
    if (roundCount == 1) {
      // Generate a file in the first round
      environment.codeGenerator.createNewFile(
          dependencies = Dependencies(aggregating = false),
          packageName = "test",
          fileName = "GeneratedBeforeException",
          extensionName = "kt"
      ).use { 
        it.write("package test\nclass GeneratedBeforeException\n".toByteArray()) 
      }
      return emptyList()
    } else {
      // Throw an exception in the second round
      throw RuntimeException("Exception after generation")
    }
  }
}
