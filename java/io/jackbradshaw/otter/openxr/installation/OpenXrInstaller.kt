package io.jackbradshaw.otter.openxr.installation

interface OpenXrInstaller {
  suspend fun deployActionManifestFiles()
}