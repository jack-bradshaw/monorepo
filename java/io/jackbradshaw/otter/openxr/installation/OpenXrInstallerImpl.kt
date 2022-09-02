package io.jackbradshaw.otter.openxr.installation

import io.jackbradshaw.otter.openxr.constants.OpenXrConstants

import io.jackbradshaw.otter.openxr.model.input.InputIdentifier
import io.jackbradshaw.otter.openxr.model.input.InputLocation
import io.jackbradshaw.otter.openxr.model.output.OutputIdentifier
import io.jackbradshaw.otter.openxr.model.output.OutputLocation
import io.jackbradshaw.otter.openxr.model.input.Component
import io.jackbradshaw.otter.openxr.model.User
import io.jackbradshaw.otter.openxr.model.InteractionProfile
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths

class OpenXrInstallerImpl : OpenXrInstaller {

  override suspend fun deployActionManifestFiles() {
    OpenXrConstants.ACTION_MANIFEST_FILE.overwriteWith(createPrimaryManifest())
    profileSpecs.forEach { profile, spec ->
      Paths.get(
          OpenXrConstants.ACTION_MANIFEST_DIRECTORY.toString(),
          spec.filename()
      ).toFile().overwriteWith(profile.createManifest())
    }
  }

  private fun createPrimaryManifest(): String {
    TODO()
    
    // for each interaction profile write a link
    // for each interaction profile declare actions for all inputs and outputs
  }

  private fun InteractionProfile.createManifest(): String {
    TODO()
    // for each input declare components
    // for each output declare components
  }

  private fun ProfileSpec.filename() = "${vendor}_${name}.json"

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