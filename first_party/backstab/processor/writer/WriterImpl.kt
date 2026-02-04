package com.jackbradshaw.backstab.processor.writer

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSFile
import com.jackbradshaw.backstab.processor.BackstabCoreScope
import com.squareup.kotlinpoet.FileSpec
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets
import javax.inject.Inject

/** Provides a concrete implementation of [Writer] using KSP's [CodeGenerator]. */
@BackstabCoreScope
class WriterImpl @Inject constructor(private val codeGenerator: CodeGenerator) : Writer {

  override suspend fun write(spec: FileSpec, source: KSFile) {
    val file = codeGenerator.createNewFile(Dependencies(true, source), spec.packageName, spec.name)
    val writer = OutputStreamWriter(file, StandardCharsets.UTF_8)
    writer.use { spec.writeTo(it) }
  }
}
