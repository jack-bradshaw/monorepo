package com.jackbradshaw.backstab.core.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.jackbradshaw.backstab.core.generator.MetaComponentGenerator
import com.jackbradshaw.backstab.core.model.BackstabComponent
import com.jackbradshaw.backstab.core.parser.Parser
import com.jackbradshaw.backstab.core.writer.Writer
import com.squareup.kotlinpoet.FileSpec
import kotlinx.coroutines.runBlocking
import org.junit.Test

abstract class ProcessorTest {

  abstract fun createSubject(
      generator: MetaComponentGenerator,
      writer: Writer,
      parser: Parser
  ): Processor

  @Test
  fun createMetaComponentsIteratesAndProcessesComponents() = runBlocking {
    val fakeGenerator =
        object : MetaComponentGenerator {
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
          override fun parseModel(component: KSClassDeclaration) = null
        }
    val processor = createSubject(fakeGenerator, fakeWriter, fakeParser)

    processor.createMetaComponents(emptyList())
  }
}
