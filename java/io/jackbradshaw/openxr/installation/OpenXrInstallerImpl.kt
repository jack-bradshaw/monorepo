package io.jackbradshaw.otter.openxr.installation

import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Paths
import io.jackbradshaw.otter.openxr.manifest.ManifestGenerator
import kotlinx.coroutines.runBlocking
import io.jackbradshaw.otter.openxr.model.Config

class OpenXrInstallerImpl(
    private val manifestGenerator: ManifestGenerator,
    private val config: Config
) : OpenXrInstaller {

  override fun deployActionManifestFiles() = runBlocking {
    val manifests = manifestGenerator.generateManifests()
    MAIN_MANIFEST_FILENAME.overwriteWith(manifests.primaryManifest)
    manifests.secondaryManifests.forEach {
      val file = Paths.get(
          config.actionManifestDirectory.toString(),
          it.url
      ).toFile()
      file.overwriteWith(it.content)
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

  companion object {
    private const val MAIN_MANIFEST_FILENAME = "action_manifest.json"
  }
}

