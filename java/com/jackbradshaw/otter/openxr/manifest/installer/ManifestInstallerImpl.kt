package com.jackbradshaw.otter.openxr.manifest.installer

import com.jackbradshaw.otter.OtterScope
import com.jackbradshaw.otter.config.Config
import com.jackbradshaw.otter.openxr.manifest.generator.ManifestGenerator
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Paths
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

@OtterScope
class ManifestInstallerImpl
@Inject
internal constructor(private val config: Config, private val manifestGenerator: ManifestGenerator) :
    ManifestInstaller {

  override fun deployActionManifestFiles() = runBlocking {
    val manifests = manifestGenerator.generateManifests()
    Paths.get(
            config.openXrConfig.actionManifestDirectory, config.openXrConfig.actionManifestFilename)
        .toFile()
        .overwriteWith(manifests.primaryManifest)

    manifests.secondaryManifests.forEach {
      Paths.get(config.openXrConfig.actionManifestDirectory, it.url)
          .toFile()
          .overwriteWith(it.content)
    }
  }

  private fun File.overwriteWith(contents: String) {
    try {
      if (exists()) delete()
      createNewFile()
      BufferedWriter(FileWriter(this)).use { it.write(contents) }
    } catch (e: IOException) {
      throw IllegalStateException("Failed to overwrite file $this.", e)
    }
  }
}
