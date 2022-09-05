package io.jackbradshaw.otter.openxr.installation

import io.jackbradshaw.otter.openxr.constants.OpenXrConstants

import io.jackbradshaw.otter.openxr.encoding.Encoding
import io.jackbradshaw.otter.openxr.model.InputIdentifier
import io.jackbradshaw.otter.openxr.model.InputLocation
import io.jackbradshaw.otter.openxr.model.OutputIdentifier
import io.jackbradshaw.otter.openxr.model.OutputLocation
import io.jackbradshaw.otter.openxr.model.InputComponent
import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.Input
import io.jackbradshaw.otter.openxr.standard.StandardInputComponent
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import io.jackbradshaw.otter.openxr.standard.StandardInteractionProfile
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onEach
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.Gson
import io.jackbradshaw.otter.openxr.manifest.ManifestGenerator
import kotlinx.coroutines.runBlocking

class OpenXrInstallerImpl(
    private val manifestGenerator: ManifestGenerator
) : OpenXrInstaller {

  override fun deployActionManifestFiles() = runBlocking {
    val manifests = manifestGenerator.generateManifests()
    OpenXrConstants.ACTION_MANIFEST_FILE.overwriteWith(manifests.primaryManifest)
    manifests.secondaryManifests.forEach {
      val file = Paths.get(
          OpenXrConstants.ACTION_MANIFEST_DIRECTORY.toString(),
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
}

