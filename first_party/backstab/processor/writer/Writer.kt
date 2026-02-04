package com.jackbradshaw.backstab.processor.writer

import com.google.devtools.ksp.symbol.KSFile
import com.squareup.kotlinpoet.FileSpec

/** Writes generated [FileSpec]s to the filesystem. */
interface Writer {
  /**
   * Writes the [spec] to disk.
   *
   * @param source The originating [KSFile]. This is required for KSP's incremental processing to
   *   correctly track dependencies and invalidate outputs when the source changes.
   */
  suspend fun write(spec: FileSpec, source: KSFile)
}
