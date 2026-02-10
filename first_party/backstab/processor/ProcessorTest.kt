package com.jackbradshaw.backstab.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.jackbradshaw.backstab.processor.generator.Generator
import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.jackbradshaw.backstab.processor.parser.Parser
import com.jackbradshaw.backstab.processor.writer.Writer
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Checks that the processor correctly coordinates the parsing, generating, and writing phases.
 */
abstract class ProcessorTest {

  abstract fun createSubject(
      generator: Generator,
      writer: Writer,
      parser: Parser
  ): Processor

  @Test
  fun createAggregateComponents_processesComponents() = runBlocking {
    val fakeGenerator =
        object : Generator {
          override suspend fun generate(component: BackstabComponent): FileSpec {
            return FileSpec.builder("com.test", "TestModule").build()
          }
        }
    val fakeWriter =
        object : Writer {
          override suspend fun write(spec: FileSpec, source: KSFile) {
            // No-op
          }
        }
    val fakeParser =
        object : Parser {
          override fun parseModel(component: KSClassDeclaration) =
              BackstabComponent(
                  ClassName("test", "TestComponent"),
                  BackstabComponent.ComponentInstantiator.CreateFunction)
        }
    val processor = createSubject(fakeGenerator, fakeWriter, fakeParser)

    processor.createAggregateComponents(emptyList())
  }
}
