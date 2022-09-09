package io.jackbradshaw.omnixr.manifest.installer

import io.jackbradshaw.omnixr.OmniXrScope
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Paths
import io.jackbradshaw.omnixr.manifest.generator.ManifestGenerator
import kotlinx.coroutines.runBlocking
import io.jackbradshaw.omnixr.config.Config
import javax.inject.Inject

@OmniXrScope
class ManifestInstallerImpl @Inject internal constructor(
    private val manifestGenerator: ManifestGenerator,
    private val config: Config
) : ManifestInstaller {

  override fun deployActionManifestFiles() = runBlocking {
    val manifests = manifestGenerator.generateManifests()
    Paths
        .get(config.actionManifestDirectory, config.actionManifestFilename)
        .toFile()
        .overwriteWith(manifests.primaryManifest)

    manifests.secondaryManifests.forEach {
      Paths
          .get(config.actionManifestDirectory.toString(), it.url)
          .toFile()
          .overwriteWith(it.content)
    }
  }

  private fun File.overwriteWith(contents: String) {
    try {
      if (exists()) delete()
      createNewFile()
      BufferedWriter(FileWriter(this)).use {
        it.write(contents)
      }
    } catch (e: IOException) {
      throw IllegalStateException("Failed to overwrite file $this.", e)
    }
  }
}

