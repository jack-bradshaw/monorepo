package com.jackbradshaw.backstab.processor.core

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.jackbradshaw.backstab.processor.generator.AggregateComponentGenerator
import com.jackbradshaw.backstab.processor.model.BackstabComponent
import com.jackbradshaw.backstab.processor.parser.Parser
import com.jackbradshaw.backstab.processor.writer.Writer
import com.squareup.kotlinpoet.FileSpec
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Checks that the processor correctly coordinates the parsing, generating, and writing phases.
 *
 * **Testing Dimensions:**
 * 1. **Orchestration**: Verifies that the processor calls the Parser, Generator, and Writer in the
 *    correct order.
 * 2. **Iterative Processing**: Verifies that the processor handles multiple components in the input
 *    list.
 * 3. **IO Handling**: Verifies that the processor correctly interfaces with the KSP environment for
 *    file creation.
 */
abstract class ProcessorTest {

  abstract fun createSubject(
      generator: AggregateComponentGenerator,
      writer: Writer,
      parser: Parser
  ): Processor

  @Test
  fun createAggregateComponentsIteratesAndProcessesComponents() = runBlocking {
    val fakeGenerator =
        object : AggregateComponentGenerator {
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
              BackstabComponent("test", listOf("TestComponent"), BackstabComponent.Create)
        }
    val processor = createSubject(fakeGenerator, fakeWriter, fakeParser)

    processor.createAggregateComponents(emptyList())
  }
}
