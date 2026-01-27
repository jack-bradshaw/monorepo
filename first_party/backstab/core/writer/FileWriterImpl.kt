package com.jackbradshaw.backstab.core.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSFile
import com.squareup.kotlinpoet.FileSpec
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

class FileWriterImpl(
    private val codeGenerator: CodeGenerator
) : Writer {

    override suspend fun write(spec: FileSpec, source: KSFile) {
        val file = codeGenerator.createNewFile(
            Dependencies(true, source), 
            spec.packageName, 
            spec.name
        )
        val writer = OutputStreamWriter(file, StandardCharsets.UTF_8)
        writer.use {
            spec.writeTo(it)
        }
    }
}
