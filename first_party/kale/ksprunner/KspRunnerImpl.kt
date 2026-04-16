package com.jackbradshaw.kale.ksprunner

import com.google.devtools.ksp.impl.KotlinSymbolProcessing
import com.google.devtools.ksp.processing.KSPJvmConfig
import com.google.devtools.ksp.processing.KspGradleLogger
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.jackbradshaw.kale.model.JvmClass
import com.jackbradshaw.kale.model.JvmSource
import com.jackbradshaw.kale.model.KspArtefacts
import com.jackbradshaw.kale.model.KspVersions
import java.io.File
import java.net.URLClassLoader
import java.nio.file.Files
import javax.inject.Inject

// Opt in required to access KotlinSymbolProcessing class.
@Suppress("OPT_IN_USAGE_ERROR")
class KspRunnerImpl @Inject internal constructor() : KspRunner {

  override fun runKsp(
      sources: Set<JvmSource>,
      providers: Set<SymbolProcessorProvider>,
      versions: KspVersions,
      options: Map<String, String>
  ): KspRunner.Result {
    val logger = KspGradleLogger(KspGradleLogger.LOGGING_LEVEL_WARN)

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
        KspRunner.Result.Success(artefacts = outputFiles.collectKspArtefacts())
      } else {

        KspRunner.Result.Failure(artefacts = outputFiles.collectKspArtefacts())
      }
    } catch (e: Exception) {
      return KspRunner.Result.Failure(artefacts = KspArtefacts.createEmpty(), error = e)
    } finally {
      inputFiles?.root?.deleteRecursively()
      outputFiles?.root?.deleteRecursively()
      workingFiles?.root?.deleteRecursively()
    }
  }

  override fun runKsp(
      sources: Set<JvmSource>,
      providers: SymbolProcessorProvider,
      versions: KspVersions,
      options: Map<String, String>
  ): KspRunner.Result = runKsp(sources, setOf(providers), versions, options)

  private fun createConfig(
      inputFiles: InputFiles,
      outputFiles: OutputFiles,
      workingFiles: WorkingFiles,
      classpaths: Set<String>,
      versions: KspVersions,
      options: Map<String, String>
  ) =
      KSPJvmConfig.Builder()
          .apply {
            // General
            moduleName = "main"
            processorOptions = options.toMap()
            libraries = classpaths.map { File(it) }

            // Inputs
            sourceRoots = listOf(inputFiles.root)
            javaSourceRoots = listOf(inputFiles.root)
            commonSourceRoots = emptyList()

            // Outputs
            javaOutputDir = outputFiles.javaOutputs
            kotlinOutputDir = outputFiles.kotlinOutputs
            resourceOutputDir = outputFiles.miscOutputs
            classOutputDir = outputFiles.classOutputs
            projectBaseDir = outputFiles.root
            outputBaseDir = outputFiles.root

            // Workspace
            cachesDir = workingFiles.cache

            // Versions
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
  private class InputFiles(sources: Set<JvmSource>) {
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

    /** The directory for misc outputs. */
    val miscOutputs: File = File(root, "misc").also { it.mkdirs() }

    /** The directory for class outputs. */
    val classOutputs: File = File(root, "classes").also { it.mkdirs() }

    /** Collects all outputs into a [KspArtefacts] object. */
    fun collectKspArtefacts(): KspArtefacts {
      val kotlinSources =
          kotlinOutputs
              .walkTopDown()
              .filter { it.isFile && it.extension == "kt" }
              .map { JvmSource("", it.nameWithoutExtension, it.extension, it.readText()) }
              .toList()

      val javaSources =
          javaOutputs
              .walkTopDown()
              .filter { it.isFile && it.extension == "java" }
              .map { JvmSource("", it.nameWithoutExtension, it.extension, it.readText()) }
              .toList()

      val misc =
          miscOutputs
              .walkTopDown()
              .filter {
                it.isFile &&
                    it.extension != "kt" &&
                    it.extension != "java" &&
                    it.extension != "class"
              }
              .map { JvmSource("", it.nameWithoutExtension, it.extension, it.readText()) }
              .toList()

      val classes =
          classOutputs
              .walkTopDown()
              .filter { it.isFile && it.extension == "class" }
              .map { JvmClass("", it.nameWithoutExtension, it.readBytes().toList()) }
              .toList()

      return KspArtefacts(
          kotlinSources = kotlinSources, javaSources = javaSources, classes = classes, misc = misc)
    }
  }

  /** The processing workspace. */
  private class WorkingFiles {
    /** The root directory for all working files. */
    val root: File = Files.createTempDirectory("working").toFile().also { it.mkdirs() }

    /** The directory for caches. */
    val cache: File = File(root, "caches").also { it.mkdirs() }
  }
}
