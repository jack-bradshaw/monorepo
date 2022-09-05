package io.jackbradshaw.omnixr.installation

import io.jackbradshaw.omnixr.OmniXrScope
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Paths
import io.jackbradshaw.omnixr.manifest.ManifestGenerator
import kotlinx.coroutines.runBlocking
import io.jackbradshaw.omnixr.config.Config
import javax.inject.Inject

@OmniXrScope
class OpenXrInstallerImpl @Inject internal constructor(
    private val manifestGenerator: ManifestGenerator,
    private val config: Config
) : OpenXrInstaller {

  override fun deployActionManifestFiles() = runBlocking {
    val manifests = manifestGenerator.generateManifests()
    Paths.get(config.actionManifestDirectory, MAIN_MANIFEST_FILENAME).toFile().overwriteWith(manifests.primaryManifest)
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

