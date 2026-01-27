package com.jackbradshaw.backstab.core.processor

import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.jackbradshaw.backstab.core.generator.MetaComponentGenerator
import com.jackbradshaw.backstab.core.parser.Parser
import com.jackbradshaw.backstab.core.writer.Writer
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ProcessorImpl(
    private val generator: MetaComponentGenerator,
    private val writer: Writer,
    private val parser: Parser
) : Processor {

    override suspend fun createMetaComponents(components: List<KSClassDeclaration>) = coroutineScope {
        components.forEach { component ->
            val model = parser.parseModel(component) ?: return@forEach
            val spec = generator.generate(model)
            writer.write(spec, component.containingFile!!)
        }
    }
}
