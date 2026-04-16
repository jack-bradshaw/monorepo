package com.jackbradshaw.kale.testing

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated

/**
 * A [SymbolProcessor] that tracks whether [process] was invoked and generates a source file using
 * [codeGenerator].
 *
 * The generated files are:
 * - `GeneratedFile.kt` containing `GeneratedClass`
 * - `GeneratedJavaFile.java` containing `GeneratedJavaClass`
 * - `GeneratedResource.txt` containing a raw string.
 */
class CodeGeneratingProcessor(private val codeGenerator: CodeGenerator) : SymbolProcessor {

  /** Whether [process] was invoked. */
  var didRunProcess = false
    private set

  override fun process(resolver: Resolver): List<KSAnnotated> {

    if (!didRunProcess) {
      codeGenerator
          .createNewFile(
              dependencies = Dependencies(false),
              packageName = "test",
              fileName = "GeneratedFile",
              extensionName = "kt")
          .use { it.write("package test\nclass GeneratedClass\n".encodeToByteArray()) }

      codeGenerator
          .createNewFile(
              dependencies = Dependencies(false),
              packageName = "test",
              fileName = "GeneratedJavaFile",
              extensionName = "java")
          .use { it.write("package test;\nclass GeneratedJavaClass {}\n".encodeToByteArray()) }

      codeGenerator
          .createNewFile(
              dependencies = Dependencies(false),
              packageName = "test",
              fileName = "GeneratedResource",
              extensionName = "txt")
          .use { it.write("Generated text".encodeToByteArray()) }

      didRunProcess = true
    }
    return emptyList()
  }
}
