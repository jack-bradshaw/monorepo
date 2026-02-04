package com.jackbradshaw.backstab.processor.core

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.processor.BackstabCoreScope
import com.jackbradshaw.backstab.processor.generator.AggregateComponentGenerator
import com.jackbradshaw.backstab.processor.parser.Parser
import com.jackbradshaw.backstab.processor.writer.Writer
import javax.inject.Inject
import kotlinx.coroutines.coroutineScope

/** Provides a concrete implementation of [Processor]. */
@BackstabCoreScope
class ProcessorImpl
@Inject
constructor(
    private val parser: Parser,
    private val generator: AggregateComponentGenerator,
    private val writer: Writer
) : Processor {

  /**
   * Iterates through the provided components, parses them into models, generates the
   * implementation, and writes the file to disk.
   */
  override suspend fun createAggregateComponents(components: List<KSClassDeclaration>) =
      coroutineScope {
        components.forEach { component ->
          val model = parser.parseModel(component)
          val generated = generator.generate(model)
          writer.write(generated, source = component.containingFile!!)
        }
      }
}
