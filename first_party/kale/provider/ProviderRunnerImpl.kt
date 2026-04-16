package com.jackbradshaw.kale.provider

import com.google.devtools.ksp.impl.KotlinSymbolProcessing
import com.google.devtools.ksp.processing.KSPJvmConfig
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSNode
import com.jackbradshaw.kale.model.Artifacts
import com.jackbradshaw.kale.model.Log
import com.jackbradshaw.kale.model.Resource
import com.jackbradshaw.kale.model.Result
import com.jackbradshaw.kale.model.Source
import com.jackbradshaw.kale.model.Versions
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files
import javax.inject.Inject

/** [ProviderRunner] that executes against the real KSP system. */
class ProviderRunnerImpl @Inject internal constructor() : ProviderRunner {

  override suspend fun runProviders(
      providers: Set<SymbolProcessorProvider>,
      sources: Set<Source>,
      versions: Versions,
      options: Map<String, String>
  ): Result {

    val logger = Logger()

    var inputFiles: InputFiles? = null
    var outputFiles: OutputFiles? = null
    var workingFiles: WorkingFiles? = null

    try {
      inputFiles = InputFiles(sources)
      outputFiles = OutputFiles()
      workingFiles = WorkingFiles()

      val classpaths = extractClasspaths()
      val config =
          createConfig(
              inputFiles = inputFiles,
              outputFiles = outputFiles,
              workingFiles = workingFiles,
              classpaths = classpaths,
              versions = versions,
              options = options)
      val exitCode = KotlinSymbolProcessing(config, providers.toList(), logger).execute()

      return if (exitCode == KotlinSymbolProcessing.ExitCode.OK) {
        Result.Success(outputFiles.collectKspArtifacts(), logger.logs)
      } else {
        Result.Failure(outputFiles.collectKspArtifacts(), logger.logs)
      }
    } catch (error: Exception) {
      return Result.Failure(Artifacts.createEmpty(), logger.logs, error)
    } finally {
      inputFiles?.root?.deleteRecursively()
      outputFiles?.root?.deleteRecursively()
      workingFiles?.root?.deleteRecursively()
    }
  }

  override suspend fun <S : SymbolProcessorProvider> runProvider(
      provider: S,
      sources: Set<Source>,
      versions: Versions,
      options: Map<String, String>
  ): Result = runProviders(setOf(provider), sources, versions, options)

  /**
   * Creates a [KSPJvmConfig] that reads from [inputFiles], writes to [outputFiles], uses
   * [workingFiles] as a temporary workspace, imports from [classpaths], and executes against
   * [versions] with [options].
   */
  private fun createConfig(
      inputFiles: InputFiles,
      outputFiles: OutputFiles,
      workingFiles: WorkingFiles,
      classpaths: Set<String>,
      versions: Versions,
      options: Map<String, String>
  ) =
      KSPJvmConfig.Builder()
          .apply {
            moduleName = "main"
            processorOptions = options.toMap()
            libraries = classpaths.map { File(it) }

            sourceRoots = listOf(inputFiles.root)
            javaSourceRoots = listOf(inputFiles.root)
            commonSourceRoots = emptyList()

            javaOutputDir = outputFiles.javaOutputs
            kotlinOutputDir = outputFiles.kotlinOutputs
            resourceOutputDir = outputFiles.resourceOutputs
            classOutputDir = outputFiles.resourceOutputs
            projectBaseDir = outputFiles.root
            outputBaseDir = outputFiles.root

            cachesDir = workingFiles.cache

            languageVersion = versions.languageVersion
            apiVersion = versions.apiVersion
            jvmTarget = versions.jvmTarget
          }
          .build()

  /** Extracts classpath paths from the current environment. */
  private fun extractClasspaths(): Set<String> {
    val loader =
        Thread.currentThread().contextClassLoader as? URLClassLoader
            ?: this::class.java.classLoader as? URLClassLoader

    val extractedClasspath =
        loader?.urLs?.map { File(it.toURI()).absolutePath }
            ?: System.getProperty("java.class.path").split(File.pathSeparator)

    return extractedClasspath.map { File(it).absolutePath }.toSet()
  }

  /**
   * The processing inputs.
   *
   * Writes [sources] to [root] during instantiation.
   */
  private class InputFiles(sources: Set<Source>) {
    /** The root directory for all inputs. */
    val root: File = Files.createTempDirectory("sources").toFile().also { it.mkdirs() }

    init {
      sources.forEach { source ->
        val file = File(root, "${source.fileName}.${source.extension}")
        file.parentFile.mkdirs()
        file.writeText(source.contents)
      }
    }
  }

  /** The processing outputs. */
  private class OutputFiles {
    /** The root directory for all outputs. */
    val root: File = Files.createTempDirectory("outs").toFile().also { it.mkdirs() }

    /** The directory for Kotlin outputs. */
    val kotlinOutputs: File = File(root, "kotlin").also { it.mkdirs() }

    /** The directory for Java outputs. */
    val javaOutputs: File = File(root, "java").also { it.mkdirs() }

    /** The directory for all other outputs. */
    val resourceOutputs: File = File(root, "resources").also { it.mkdirs() }

    /** Collects all outputs into a [Artifacts] object. */
    fun collectKspArtifacts(): Artifacts {
      val kotlinSources =
          kotlinOutputs
              .walkTopDown()
              .filter { it.isFile && it.extension == "kt" }
              .map {
                Source(
                    packageName = it.getPackageName(kotlinOutputs),
                    fileName = it.nameWithoutExtension,
                    extension = it.extension,
                    contents = it.readText())
              }
              .toList()

      val javaSources =
          javaOutputs
              .walkTopDown()
              .filter { it.isFile && it.extension == "java" }
              .map {
                Source(
                    packageName = it.getPackageName(javaOutputs),
                    fileName = it.nameWithoutExtension,
                    extension = it.extension,
                    contents = it.readText())
              }
              .toList()

      val resourcesList =
          resourceOutputs
              .walkTopDown()
              .filter { it.isFile && it.extension != "kt" && it.extension != "java" }
              .map {
                Resource(
                    directoryPath = it.getDirectoryPath(resourceOutputs),
                    fileName = it.nameWithoutExtension,
                    extension = it.extension,
                    contents = it.readBytes().toList())
              }
              .toList()

      return Artifacts(
          kotlinSources = kotlinSources, javaSources = javaSources, resources = resourcesList)
    }

    /** Gets the JVM package of this file using the standard dot-separated string format. */
    private fun File.getPackageName(root: File): String {
      val relative = this.parentFile.relativeTo(root).path
      return if (relative.isEmpty()) "" else relative.replace(File.separatorChar, '.')
    }

    /** Gets the path of this file relative to the output root. */
    private fun File.getDirectoryPath(root: File): String {
      return this.parentFile.relativeTo(root).path
    }
  }

  /** The processing workspace. */
  private class WorkingFiles {
    /** The root directory for all working files. */
    val root: File = Files.createTempDirectory("working").toFile().also { it.mkdirs() }

    /** The directory for caches. */
    val cache: File = File(root, "caches").also { it.mkdirs() }
  }

  /**
   * Stores received logging events in [logs].
   *
   * Not thread-safe since KSP is single-threaded.
   */
  private class Logger : KSPLogger {

    /** All logging events received by this logger, stored in the order they were received. */
    val logs = mutableListOf<Log>()

    override fun logging(message: String, symbol: KSNode?) {
      logs.add(Log.Unspecified(message, symbol))
    }

    override fun info(message: String, symbol: KSNode?) {
      logs.add(Log.Info(message, symbol))
    }

    override fun warn(message: String, symbol: KSNode?) {
      logs.add(Log.Warning(message, symbol))
    }

    override fun error(message: String, symbol: KSNode?) {
      logs.add(Log.Error(message, symbol))
    }

    override fun exception(e: Throwable) {
      logs.add(Log.Exception(e))
    }
  }
}
